package org.slive.tr069.servlet.expample;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slive.tr069.control.ACSProcessControl;
import org.slive.tr069.control.ACSProcessControlManager;
import org.slive.tr069.control.InformParse;
import org.slive.tr069.control.example.InformHandleExample;
import org.slive.tr069.model.AbstractMethod;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.SoapMessageModel;
import org.w3c.dom.Document;


public class ACSServlet extends HttpServlet
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ACSServlet.class.getName());
    private static final long serialVersionUID = 7701552490168024356L;
    
    private static final String LastInform = "lastInform";
    private String methodClassPath;					// 创建构造方法类的基路径
    private InformParse informParse;				// 针对cpe的Inform方法继续解析

    public ACSServlet()
    {
        this.informParse = new InformParse(new InformHandleExample());
        methodClassPath = AbstractMethod.class.getName();
        methodClassPath = methodClassPath.substring(0, methodClassPath.lastIndexOf(".") + 1);
    }

    /**
     * 解析ServletRequest至具体的 Tr069的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/xml;charset=UTF-8");
        ServletOutputStream outStream = response.getOutputStream();
        HttpSession session = request.getSession();
        Object sessionAttr = session.getAttribute(LastInform);
        Inform lastInform = null;
        if (sessionAttr != null)
        {
            lastInform = (Inform) sessionAttr;
        }

        if (request.getContentLength() == 0) // 获取CPE的一个空http时，代表一个事务执行的开始
        {
            if (lastInform != null)
            {
                doACSMethods(response, outStream, lastInform);
            }
        }
        else
        {
            // 执行解析，并将解析后需要调用的下一步方法压入队列中；
			try
			{
				// 解析多条信息
				ServletInputStream inputStream = request.getInputStream();
				SoapMessageModel[] soapMsgs = parseInputStream2SoapMsgs(inputStream);
				int length = soapMsgs.length;
				for (int index = 0; index < length; index++)	
				{
					lastInform = doParseCPEMethod(session, lastInform, soapMsgs[index]);
				}
			} catch (Exception e)
			{
				return;
			}

			doACSMethods(response, outStream, lastInform); // 执行缓存中的方法
        }
    }

	private Inform doParseCPEMethod(HttpSession session, Inform lastInform,
			SoapMessageModel soapMsg) throws Exception
	{
		String reqname = AbstractMethod.getRequestName(soapMsg);
		ACSProcessControl acsPcl = null;
		AbstractMethod requestMethod = null;
		try
		{
		    requestMethod = (AbstractMethod) Class.forName(methodClassPath + reqname).newInstance();
		}
		catch (Exception ex)
		{
		    LOGGER.error("Create " + reqname + " error!", ex);
		    throw ex;
		}

		try
		{
			requestMethod.parse(soapMsg);
			if (requestMethod instanceof Inform) // 接收到Inform，需要进行特殊处理，回复InformResponse，并记录当前会话
			{
				lastInform = (Inform) requestMethod;
				session.setAttribute(LastInform, lastInform);
				acsPcl = ACSProcessControlManager.getInstance()
						.getProcessControl(lastInform.getDeviceId().getCpeId());

				// Inform处理
				informParse.getInformHandle().setAcsProcessControl(acsPcl); // 无论acsPcl是否为空都调用设置
				informParse.parseInform(lastInform);

				// Inform的回复
				if (acsPcl != null)
				{
					acsPcl.setDeviceInfor(lastInform); 			// 缓存Inform，以备使用
					acsPcl.revAndConfirmMethod(requestMethod); 	// 具体方法解析
				}
			} else
			{
				if (lastInform != null)
				{
					// 具体方法解析
					acsPcl = ACSProcessControlManager.getInstance().getProcessControl(
							lastInform.getDeviceId().getCpeId());
					if (acsPcl != null)
					{
						acsPcl.revAndConfirmMethod(requestMethod);
					}
				}
			}
		} catch (Exception e)
		{
			LOGGER.error("Deal " + reqname + " method error! method={}",requestMethod, e);
			throw e;
		}
		return lastInform;
	}
    
    private SoapMessageModel[] parseInputStream2SoapMsgs(InputStream inputStream) throws Exception
    {
    	// 读取http中的（xml）数据流
    	int available = inputStream.available();
    	byte[] bs = new byte[available];
    	inputStream.read(bs);
    	
    	// 获取分解为多个xml
    	String messageXml = new String(bs);
    	String[] xmls = messageXml.split("\\<\\?xml");		// 通过“<?xml”	进行分割，TODO 如果不存在<?xml> 如何处理
    	
    	// 解析为SoapMessageModel，xmls[0]要避免，因为它为空
    	int length = xmls.length - 1;
    	SoapMessageModel[] smms = new SoapMessageModel[length];
		for (int index = 0; index < length; index++)
		{
			String xml = "<?xml" + xmls[index + 1];		// 补全xml
			
			// TODO 处理效率过低
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(xml.trim().getBytes()));
			smms[index] = new SoapMessageModel(doc);
		}
		return smms;
    }
    

    /**
     * 根据具体的设备，执行ACS方法
     */
    private void doACSMethods(HttpServletResponse response,
                              ServletOutputStream outStream, Inform lastInform) throws IOException
    {
        ACSProcessControl acsPcl = ACSProcessControlManager.getInstance().
            getProcessControl(lastInform.getDeviceId().getCpeId());
        if (acsPcl != null)
        {
            response.setContentLength(acsPcl.doACSEMethods(outStream));
            outStream.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the ACSServlet.
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
               IOException
    {
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void destroy()
    {
    }
}
