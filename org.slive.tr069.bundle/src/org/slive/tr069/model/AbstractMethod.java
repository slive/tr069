package org.slive.tr069.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;

import javax.xml.soap.SOAPException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slive.tr069.model.struct.FaultStruct;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 模型抽象类，所有CPE,ACS方法（模型）都是基于该类的<br>
 * <b>主要功能是：</b>将模型转换为SoapMessage进行发送；或者接收SoapMessage并解析到模型中。<br>
 * <b>SoapMessage</b>即SOAPXml,包括了SoapEnvelope,SoapHeader,SoapBody三个部分组成。
 * 其中SoapPart和 SoapHeader在该类中默认生成或者解析，SoapBody由继承该类的模型进行生成或者解析 (见
 * {@link #addField2Body(SOAPBodyElement, SOAPFactory)}和{@link #parse(SOAPMessage)}
 * )。<br>
 *
 * SOAP是基于HTTP传输的xml信息，<b>SoapMessage</b>封装了SOAPXml信息，并通{@code
 * #writeTo(OutputStream)} 或者{@code #writeTo(URL)}转换为HTTP或者底层数据流，进行网络传输。<br>
 *
 * <pre>
 * <b>附：</b>
 *  SOAP 是基于 XML 的简易协议，可使应用程序在 HTTP 之上进行信息交换。 或者更简单地说：SOAP 是用于访问网络服务的协议。
 *  一条 SOAP 消息就是一个普通的 XML 文档，包含下列元素： 必需的 Envelope 元素，可把此 XML 文档标识为一条 SOAP 消息
 *  可选的 Header元素，包含头部信息 必需的 Body 元素，包含所有的调用和响应信息 可选的 Fault 元素，提供有关在处理此消
 *  息所发生错误的信息。
 *
 *  <b>语法规则</b>
 * 	这里是一些重要的语法规则：
 * 	SOAP 消息必须用 XML 来编码
 * 	SOAP 消息必须使用 SOAP Envelope 命名空间
 * 	SOAP 消息必须使用 SOAP Encoding 命名空间
 * 	SOAP 消息不能包含 DTD 引用
 * 	SOAP 消息不能包含 XML 处理指令
 *
 * 	<b>SOAP 消息的基本结构（实例）</b>
 * 		<?xml version="1.0"?>
 * 		<soap:Envelope
 * 		xmlns:soap="http://www.w3.org/2001/12/soap-envelope"
 * 		soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
 * 		<soap:Header>
 * 		  ...
 * 		  ...
 * 		</soap:Header>
 * 		<soap:Body>
 * 		  ...
 * 		  ...
 * 		  <soap:Fault>
 * 		    ...
 * 		    ...
 * 		  </soap:Fault>
 * 		</soap:Body>
 * 		</soap:Envelope>
 *
 * @author Slive
 * @date 2013-05-06
 *
 */
public abstract class AbstractMethod implements Serializable
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethod.class);
    private static final long serialVersionUID = -5424823002848936758L;
    //所有soap-xml命名声明全局变量，TODO cwmp-1-1?
    public static final String URN_CWMP1_0 = "urn:dslforum-org:cwmp-1-0";
    public static final String URN_CWMP1_1 = "urn:dslforum-org:cwmp-1-1";
    public static final String URL_XSD = "http://www.w3.org/2001/XMLSchema";
    public static final String URL_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String URL_ENCODE = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final String URL_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String CWMP = "cwmp";
    public static final String XSD = "xsd";
    public static final String XSI = "xsi";
    public static final String SOAP_ENC = "SOAP-ENC";
    public static final String SOAP_ENV = "SOAP-ENV";
    public static final String XMLNS = "xmlns";				// xml命名空间
    public static final String ARRAY_TYPE = "arrayType";
    public static final String FAULT = "Fault";
    private static final String FaultCode = "FaultCode";
    private static final String FaultString = "FaultString";
    // 所有soap-xml头声明全局变量
    private static final String ID = "ID";									// 必填项
    private static final String HoldRequests = "HoldRequests";				// 从cpe回复到acs中不需要,默认0 为false,1为true
    private static final String NoMoreRequests = "NoMoreRequests";			// 默认0 为false,1为true
    private static final String MustUnderstand = "SOAP-ENV:mustUnderstand";	// 标识是否存在
    // 通用属性（主要是头信息）
    protected String requestId;					// 每一次请求的id，由请求方决定
    protected String methodName;				// 方法名称
    protected boolean acs2CpeEnv;				// 是否是acs端到cpe端的信包
    private boolean noMoreReqs;					// 是否有更多请求		
    private boolean holdReqs;					// 如果ACS需要更新控制流，可以设置，并且mustunderstand为1，只能使用在acs中
    private boolean requeired;					// 是否该方法是必选的
    private FaultStruct faultStruct;
    
    private String urnCwmpVersion;					// 选用CWMP版本

    /**
     * 默认代表从ACS到CPE的信包
     */
    public AbstractMethod()
    {
        iniModel(true);
    }

    /**
     * @param acs2CpeEnv 为true时代表从ACS到CPE的信包，否则反过来
     */
    public AbstractMethod(boolean acs2CpeEnv)
    {
        iniModel(acs2CpeEnv);
    }

    private void iniModel(boolean acs2CpeEnv)
    {
        this.acs2CpeEnv = acs2CpeEnv;
        this.noMoreReqs = false;
        this.holdReqs = false;
        this.requeired = true;
        this.methodName = "";
        this.requestId = "";
        urnCwmpVersion = URN_CWMP1_1;		// 默认版本
    }

    /**
     * 构造各自的SoapBody对应的xml文件
     */
    protected abstract void addField2Body(Element body, SoapMessageModel soapMessageModel);

    /**
     * 解析各自的SoapBody对应的xml文件至模型字段中
     */
    protected abstract void parseBody2Filed(Element body, SoapMessageModel soapMessageModel);

    /**
     * 错误处理的封装
     * @param detailEntry
     * @param soapFactory
     * @throws SOAPException
     */
    protected void addFaultDetail2Body(Element detailEntry, SoapMessageModel soapMessageModel)
    {
        //TODO  添加特定模型的错误信息
    }

    /**
     * 详细的错误解析，实例格式如下：<br/>
     * 	//	<FaultString>Invalid arguments</FaultString>
     * //	<SetParameterValuesFault>
     * //	<ParameterName>
     * //	InternetGatewayDevice.Time.LocalTimeZone
     * //	</ParameterName>
     * //	<FaultCode>9012</FaultCode>
     * //	<FaultString>Not a valid time zone value</FaultString>
     * //	</SetParameterValuesFault>
     * //	<SetParameterValuesFault>
     * //	<ParameterName>
     * //	InternetGatewayDevice.Time.LocalTimeZoneName
     * //	</ParameterName>
     * //	< FaultCode>9012</FaultCode>
     * //	<FaultString>String too long</FaultString>
     * //	</SetParameterValuesFault>
     * @param details
     */
    protected void parseFaultDetail(NodeList details)
    {
        //TODO 转为抽象方法，各个具体错误信息
    }

    /**
     * 解析各自的fault对应的xml文件至模型字段中
     */
    protected void parseFault(Element detailEntry)
    {
        // 如下格式：
        //    	<detail>
        //    	<cwmp:Fault>
        //    	<FaultCode>9003</FaultCode>
        //    	<FaultString>Invalid arguments</FaultString>
        faultStruct = new FaultStruct(Integer.parseInt(getRequestElement(detailEntry, FaultCode)),
                                      getRequestElement(detailEntry, FaultString));
        System.out.println("MethodName:" + methodName + " FaultStruct:" + faultStruct);
        parseFaultDetail(detailEntry.getElementsByTagName(methodName + FAULT));
    }

    /**
     * 将SOAPMessage 解析值相应的数据模型值中
     */
    public void parse(SoapMessageModel soapMessageModel) throws NoSuchElementException
    {
        if (soapMessageModel.getSoapHeader() != null && soapMessageModel.getSoapHeader().hasChildNodes())
        {
            NodeList headList = soapMessageModel.getSoapHeader().getChildNodes();
            for (int index = 0; index < headList.getLength(); index++)
            {
                Node headItem = headList.item(index);
                if (headItem.getNodeName().startsWith(CWMP + ":" + ID))
                {
                    requestId = headItem.getTextContent();
                }
                if (headItem.getNodeName().startsWith(CWMP + ":" + HoldRequests))
                {
                    String value = headItem.getTextContent();
                    holdReqs = Boolean.parseBoolean(value);
                }
                if (headItem.getNodeName().startsWith(CWMP + ":" + NoMoreRequests))
                {
                    String value = headItem.getTextContent();
                    noMoreReqs = Boolean.parseBoolean(value);
                }
            }
        }

        try
        {
			Element body = getRequestChildElement(soapMessageModel.getSoapBody(), CWMP + ":" + methodName);
            if (!isFault())
            {
                parseBody2Filed(body, soapMessageModel);
            }
            else
            {
                Element fElement = getFaultElement(soapMessageModel);

                // 为空时的处理方式
                parseFault(fElement);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("pars request error ", e);
        }

    }

    public int writeTo(OutputStream out) throws IOException
    {
        int length = 0;
        try
        {
            SoapMessageModel msg = new SoapMessageModel();
            addAllSoap(msg);
            length = msg.writeTo(out);
        }
        catch (Exception e)
        {
            LOGGER.error("reply info write error ", e);
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 发送并读取
     * @param url 目的地址
     * @return
     * @throws SOAPException
     */
    public int writeTo(URL url) throws IOException
    {
        if (url != null)
        {
            URLConnection openConnection = url.openConnection();
            return writeTo(openConnection.getOutputStream());
        }
        else
        {
            return 0;
        }
    }

    private void addAllSoap(SoapMessageModel soapMessageModel)
    {
        // 写到Cpe端，需要添加<?xml version=1.0 encoding='utf-8'>
        soapMessageModel.getDocument().setXmlStandalone(true);
        addNameSpaceDeclaretion(soapMessageModel);
        addIdElement2Header(soapMessageModel, requestId);
        if (acs2CpeEnv)
        {
            if (holdReqs)
            {
                addHoldReqsElement2Header(soapMessageModel, holdReqs);
            }
        }
        if (noMoreReqs)
        {
            addNoMoreReqsElement2Header(soapMessageModel, noMoreReqs);
        }

        Element bodyElement = soapMessageModel.createElementNS(methodName, CWMP, urnCwmpVersion);
        soapMessageModel.getSoapBody().appendChild(bodyElement);
        try
        {
            addField2Body(bodyElement, soapMessageModel);
        }
        catch (Exception e)
        {
            LOGGER.error("Add Field2Body error!", e);
        }

    }

    public String getMethodName()
    {
        return methodName;
    }

    /**
     * 请求标识
     */
    public String getRequestId()
    {
        return requestId;
    }

    /**
     * 请求标识，由请求方决定，应答方保持一致。
     * @param _requestId
     */
    public void setRequestId(String _requestId)
    {
        this.requestId = _requestId;
    }

    /**
     * @return 为true时代表从ACS到CPE的信包，否则反过来
     */
    public boolean isAcs2CpeEnv()
    {
        return acs2CpeEnv;
    }

    /**
     * @param acs2CpeEnv 为true时代表从ACS到CPE的信包，否则反过来
     */
    public void setAcs2CpeEnv(boolean acs2CpeEnv)
    {
        this.acs2CpeEnv = acs2CpeEnv;
    }

    public boolean isNoMoreReqs()
    {
        return noMoreReqs;
    }

	/**
	 * 该标记取值布尔型“0”（false）或“1”（true）。如果该标记不存在，则其值被解释为“0”（false）。
	 * 可以在最后请求的信包或其后的信包中将该标记值设置为
	 * “1”。在会话中一旦设置为true，该值应当在随后发送的所有信包中也设置为true，并且在该会话中发送方不得再发送其它的请求报文。
	 * 
	 * @param noMoreReqs
	 */
    public void setNoMoreReqs(boolean noMoreReqs)
    {
        this.noMoreReqs = noMoreReqs;
    }

    public boolean isHoldReqs()
    {
        return holdReqs;
    }

    public void setHoldReqs(boolean holdReqs)
    {
        this.holdReqs = holdReqs;
    }

    public boolean isRequeired()
    {
        return requeired;
    }

    public void setRequeired(boolean requeired)
    {
        this.requeired = requeired;
    }

    /**
     * 默认{@linkplain #URN_CWMP1_1}
     * @see #URN_CWMP1_0
     * @see #URN_CWMP1_1
     * @return
     */
    public String getUrnCwmpVersion()
	{
		return urnCwmpVersion;
	}

    /**
     * @see #URN_CWMP1_0
     * @see #URN_CWMP1_1
     * @param urnCwmpVersion
     */
	public void setUrnCwmpVersion(String urnCwmpVersion)
	{
		if(URN_CWMP1_0.equalsIgnoreCase(urnCwmpVersion))
		{
			this.urnCwmpVersion = URN_CWMP1_0;
		}
		else
		{
			this.urnCwmpVersion = URN_CWMP1_1;
		}
	}

	public boolean isFault()
    {
        return methodName.equals(FAULT);
    }

    protected String b2s(boolean b)
    {
        return (b) ? "1" : "0";
    }

    /**
     * 获取最上层错误<br/>
     * <p>如下格式：<detail><cwmp:Fault><FaultCode>9003</FaultCode><FaultString>Invalid arguments</FaultString></p>
     * @return	返回空时，代表物错误
     */
    public FaultStruct getFaultStruct()
    {
        return faultStruct;
    }

    @Override
    public String toString()
    {
        StringBuilder sbd = new StringBuilder();
        sbd.append("MethodName:");
        sbd.append(methodName);
        sbd.append(" RequestId:");
        sbd.append(requestId);
        return sbd.toString();
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        if (requestId != null)
        {
            byte[] bytes = requestId.getBytes();
            for (byte b : bytes)
            {
                result += b;
            }
        }

        if (methodName != null)
        {
            byte[] bytes = methodName.getBytes();
            for (byte b : bytes)
            {
                result += b;
            }
        }

        return result * 37;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof AbstractMethod)
        {
            // 方法名称和请求ID相等时，才可以认为是同一个对象
            AbstractMethod method = (AbstractMethod) obj;
            if (method.getMethodName().equals(this.getMethodName()) && (method.getRequestId().equals(this.getRequestId())))
            {
                return true;
            }
        }
        return false;
    }

    protected static Element getFaultElement(SoapMessageModel soapMessageModel)
    {
        Element soapFaultElement = getRequestChildElement(soapMessageModel.getSoapBody(), SOAP_ENV + ":" + FAULT);
        Element detailElement = getRequestChildElement(soapFaultElement, "detail");
        Element fElement = getRequestChildElement(detailElement, CWMP + ":" + FAULT);
        return fElement;
    }

    /**
     * 添加所有的命名空间
     * @param msg
     * @throws SOAPException
     */
    private void addNameSpaceDeclaretion(SoapMessageModel soapMessageModel)
    {
        // 需添加前缀xmlns
        soapMessageModel.getRootNode().setAttribute(XMLNS + ":" + SOAP_ENV, URL_ENVELOPE);
        soapMessageModel.getRootNode().setAttribute(XMLNS + ":" + SOAP_ENC, URL_ENCODE);
        soapMessageModel.getRootNode().setAttribute(XMLNS + ":" + XSD, URL_XSD);
        soapMessageModel.getRootNode().setAttribute(XMLNS + ":" + XSI, URL_XSI);
        soapMessageModel.getRootNode().setAttribute(XMLNS + ":" + CWMP, urnCwmpVersion);
    }

    private void addIdElement2Header(SoapMessageModel soapMessageModel, String idValue)
    {
        Element cwmpIdE = soapMessageModel.createElementNS(ID, CWMP, urnCwmpVersion);
        cwmpIdE.setTextContent(idValue);						// 设置element值;setTextContent此属性返回此节点及其后代的文本内容;setNodeValue()此节点的值，取决于其类型
        cwmpIdE.setAttribute(MustUnderstand, "1");				// 必须标识为1
        soapMessageModel.getSoapHeader().appendChild(cwmpIdE);
    }

    private void addHoldReqsElement2Header(SoapMessageModel soapMessageModel, boolean request)
    {
        Element cwmpIdE = soapMessageModel.createElementNS(HoldRequests, CWMP, urnCwmpVersion);
        cwmpIdE.setTextContent(request == true ? "1" : "0");			// 设置element值;setTextContent此属性返回此节点及其后代的文本内容;setNodeValue()此节点的值，取决于其类型
        cwmpIdE.setAttribute(MustUnderstand, "1");						// 必须标识为1
        soapMessageModel.getSoapHeader().appendChild(cwmpIdE);
    }

    private void addNoMoreReqsElement2Header(SoapMessageModel soapMessageModel, boolean noMoreReqs)
    {
        Element cwmpIdE = soapMessageModel.createElementNS(NoMoreRequests, CWMP, urnCwmpVersion);
        cwmpIdE.setTextContent(noMoreReqs == true ? "1" : "0");						// 设置element值;setTextContent此属性返回此节点及其后代的文本内容;setNodeValue()此节点的值，取决于其类型
        cwmpIdE.setAttribute(MustUnderstand, noMoreReqs == true ? "1" : "0");		// 必须标识为1
        soapMessageModel.getSoapHeader().appendChild(cwmpIdE);
    }

    /**
     * 打印记录发送请求的结构
     * @param msg
     */
    public static int getMsgLength(SoapMessageModel soapMessageModel)
    {
        int result = 0;
        try
        {
            ByteArrayOutputStream myOutStr = getMsg2OutStream(soapMessageModel);
            result = myOutStr.size();
        }
        catch (Exception e)
        {
            LOGGER.error("get the message length error:", e);
        }

        return result;
    }

    private static ByteArrayOutputStream getMsg2OutStream(SoapMessageModel soapMessageModel)
        throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException
    {

        ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
        StreamResult strResult = new StreamResult();
        strResult.setOutputStream(myOutStr);

        TransformerFactory tfac = TransformerFactory.newInstance();
        try
        {
            Transformer t = tfac.newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            t.transform(new DOMSource(soapMessageModel.getRootNode()), strResult);
        }
        catch (Exception e)
        {
            LOGGER.error("get byte error", e);

        }
        return myOutStr;
    }

    /**
     * 打印记录发送请求的结构
     * @param msg
     */
    public static String getMsgToString(SoapMessageModel soapMessageModel)
    {
        try
        {
            ByteArrayOutputStream myOutStr = getMsg2OutStream(soapMessageModel);
            return myOutStr.toString();
        }
        catch (Exception e)
        {
            LOGGER.error("getMsgToString", e);
        }

        return "";
    }

    protected static Element getRequestChildElement(Element req, String name)
    {
        Element result = null;
        NodeList nodeList = req.getChildNodes();
        for (int index = 0; index < nodeList.getLength(); index++)
        {
            Node item = nodeList.item(index);
            if (item.getNodeName().equalsIgnoreCase(name) && item instanceof Element)
            {
                result = (Element) item;
                break;
            }
        }
        return result;
    }

    protected Element getRequestChildElement2(Element req, String name)
    {
        Element result = null;
        NodeList nodeList = req.getElementsByTagNameNS(urnCwmpVersion, CWMP + ":" + name);
        for (int index = 0; index < nodeList.getLength(); index++)
        {
            Node item = nodeList.item(index);
            if (item instanceof Element)
            {
                result = (Element) item;
                break;
            }
        }
        return result;
    }

    protected static String getRequestElement(Element req, String name)
    {
        return getRequestChildElement(req, name).getTextContent();
    }

    public static void getArrayTypeAttribute(Element paramAttrElement, String arrayType, int arrayLen)
    {
        paramAttrElement.setAttribute((XSI + ":type"), (SOAP_ENC + ":Array"));
        paramAttrElement.setAttribute((SOAP_ENC + ":arrayType"), (CWMP + ":" + arrayType + "["
            + String.valueOf(arrayLen) + "]"));
    }

    protected String getHeaderElement(Element soapHeader, String name)
    {
        return soapHeader.getElementsByTagNameNS(urnCwmpVersion, CWMP + ":" + name).item(0).getNodeValue();
    }

    /**
     * @Deprecated
     */
    protected static int getArrayCount(SoapMessageModel soapMessageModel, Element e)
    {
        //TODO
//        Name nameArray = soapFactory.createName("arrayType", "soap-enc",
//                "http://schemas.xmlsoap.org/soap/encoding/");
//        String attr = e.getAttributeValue(nameArray);
//        String c = attr.substring(attr.indexOf('[') + 1, attr.length() - 1);
//        return Integer.parseInt(c);
        return 0;
    }

    public static Element getRequest(SoapMessageModel soapMessageModel)
    {
        Element request = null;
        NodeList iter = soapMessageModel.getSoapBody().getChildNodes();
        for (int index = 0; index < iter.getLength(); index++)
        {
            Node n = iter.item(index);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                request = (Element) n;
            }
        }
        return request;
    }

    public static String getRequestName(SoapMessageModel soapMessageModel)
    {
        String name = getRequest(soapMessageModel).getNodeName();
        if (name.startsWith("cwmp:"))
        {
            name = name.substring(5);
        }
        else
        {
            try
            {
                Element faultElement = getFaultElement(soapMessageModel);
                if (faultElement == null)
                {
                    name = FAULT;
                }
                else
                {
                    NodeList childNodes = faultElement.getChildNodes();
                    int length = childNodes.getLength();
                    for (int index = 0; index < length; index++)
                    {
                        Node item = childNodes.item(index);
                        if (item instanceof Element)
                        {
                            String nodeName = item.getNodeName();
                            if (!nodeName.equals(FaultCode) && !nodeName.equals(FaultString) && nodeName.contains(FAULT))
                            {
                                return nodeName.substring(0, (nodeName.length() - 5));
                            }
                        }
                    }
                    name = FAULT;
                }
            }
            catch (Exception e)
            {
                name = FAULT;
                LOGGER.error("getRequestName Error!");
            }
        }
        return name;
    }
}
