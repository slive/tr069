package org.slive.tr069.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.slive.tr069.control.ACSProcessControl;
import org.slive.tr069.control.ACSProcessControlManager;
import org.slive.tr069.control.ResponseParse;
import org.slive.tr069.control.example.ResponseHandleExample;
import org.slive.tr069.model.AbstractMethod;
import org.slive.tr069.model.Download;
import org.slive.tr069.servlet.expample.ACSServlet;
import org.slive.tr069.servlet.expample.UpdateServlet;


public class Activator implements BundleActivator,ServiceListener
{
    private ACSServlet acsServlet;
    private BundleContext context;
    private ServiceReference serviceReference;		// osgi中的已经注册的服务的引用
    private UpdateServlet updateServlet;

    @Override
    public void start(BundleContext context) throws Exception
    {

    	System.out.println("Start acs testing.");
		this.context = context;
		acsServlet = new ACSServlet();
		updateServlet = new UpdateServlet();
		
		try
		{
			registerServlet();	// 注册服务
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// 注册服务监听，filter:HttpService服务
		context.addServiceListener(this, ("(objectclass=" + HttpService.class.getName() + ")"));
		System.out.println("Start acs testing sucessful!");
    }
    
	/**
	 * 获取httpservice服务，然后注册servlet映射的地址，资源目录
	 */
    
	private void registerServlet()
	{
		if (serviceReference == null)
		{
			serviceReference = context.getServiceReference(HttpService.class.getName());
		}

		if (serviceReference != null)
		{
			HttpService service = (HttpService) context.getService(serviceReference);
			try
			{
				service.registerServlet("/acs", acsServlet, null,null);
				System.out.println("Load validator acs testing modual,please through '/acs' to access!");
				service.registerServlet("/upload", updateServlet, null,null);
				System.out.println("Load validator acs testing modual,please through '/upload' to access!");
				
				
//				String _cpeId = "012345E168-TEL012345001d2bf83633";
				String _cpeId = "012345_012345001d2bf83633";
//				String _cpeId = "001FA4GENV4.4L45A-B291A37001FA49239A4";
				ACSProcessControl pcl = new ACSProcessControl(_cpeId, AbstractMethod.URN_CWMP1_0, 1);
				ACSProcessControlManager.getInstance().addProcessControl(pcl);
				pcl.setResponseParse(new ResponseParse(new ResponseHandleExample()));
				
				
//				GetParameterNames gpn0 = new GetParameterNames();
//				gpn0.setParameterPathAndNextLevel("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.SIP.", true);
//				pcl.addACSRequestMethod(gpn0);
////				
//				GetParameterNames gpn1 = new GetParameterNames();
//				gpn1.setParameterPathAndNextLevel("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.", true);
//				pcl.addACSRequestMethod(gpn1);
////				
//				GetParameterNames gpn2 = new GetParameterNames();
//				gpn2.setParameterPathAndNextLevel("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.", true);
//				pcl.addACSRequestMethod(gpn2);
////				
//				GetParameterNames gpn3 = new GetParameterNames();
//				gpn3.setParameterPathAndNextLevel("InternetGatewayDevice.Services.VoiceService.", true);
//				pcl.addACSRequestMethod(gpn3);
				
//				SetParameterValues spv = new SetParameterValues();
//				ParameterValueStructStr pvs1 = new ParameterValueStructStr(
//						"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.ProxyServer", "10.8.3.215");
//				spv.getParameterList().addParamValues(pvs1);
//				pcl.addACSRequestMethod(spv);
//				ParameterValueStructUnsignedInt pvs2 = new ParameterValueStructUnsignedInt(
//						"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.ProxyServerPort", 5060l);
//				spv.getParameterList().addParamValues(pvs2);
//				ParameterValueStructStr pvs3 = new ParameterValueStructStr(
//						"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.X_CU_SecondaryProxyServer", "10.8.3.207");
//				spv.getParameterList().addParamValues(pvs3);
//				ParameterValueStructUnsignedInt pvs4 = new ParameterValueStructUnsignedInt(
//						"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.X_CU_SecondaryProxyServerPort", 5060l);
//				spv.getParameterList().addParamValues(pvs4);
//				pcl.addACSRequestMethod(spv);
				
//				GetParameterValues gpv = new GetParameterValues();
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.SIP.AuthUserName");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.SIP.AuthPassword");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.Enable");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.DirectoryNumber");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.ProxyServer");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.ProxyServerPort");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.X_CU_SecondaryProxyServer");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.X_CU_SecondaryProxyServerPort");
//				gpv.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfileNumberOfEntries");
//				pcl.addACSRequestMethod(gpv);
				
//				GetParameterValues gpv1 = new GetParameterValues();
//				gpv1.getParameterNames().add("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.SIP.ProxyServer");
//				pcl.addACSRequestMethod(gpv1);
//				
//				GetParameterNames gpn =new GetParameterNames();
//				gpn.setParameterPathAndNextLevel("InternetGatewayDevice.Services.VoiceService.1.", true);
//				pcl.addACSRequestMethod(gpn);
////				
//				GetRPCMethods gpm = new GetRPCMethods();
//				pcl.addACSRequestMethod(gpm);
				
//				Reboot rb = new Reboot("MyRoot");
//				pcl.addACSRequestMethod(rb);
				
				Download dld = new Download();
				dld.setCommandKey("Download_dl");
				dld.setFileType(Download.FT_FIRMWARE);
				//TODO TEST
				dld.setUrl("http://10.8.9.116:8080/upload");
				String fileName = "04L_app.img";
				dld.setTargetFileName(fileName);
				String path = System.getProperty("user.dir") + File.separator + "upload" + File.separator + fileName;
				File file = new File(path);
				FileInputStream fileOut = new FileInputStream(file);
				updateServlet.setUpdateFile(file);
				BufferedInputStream bis = new BufferedInputStream(fileOut);
				int available = bis.available();
				dld.setFileSize(available);
				pcl.addACSRequestMethod(dld);
				
//				SetParameterValues spv = new SetParameterValues();
//				spv.getParameterList().addParamValues(new ParameterValueStructStr("InternetGatewayDevice.ManagementServer.ParameterKey", "12345"));
//				spv.setRequestId("309686");
//				pcl.addACSRequestMethod(spv);
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

    @Override
    public void stop(BundleContext context) throws Exception
    {
    }

	@Override
	public void serviceChanged(ServiceEvent event)
	{
		// TODO Auto-generated method stub
		
	}
}
