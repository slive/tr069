package org.slive.tr069.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slive.tr069.model.AbstractMethod;
import org.slive.tr069.model.AddObjectResponse;
import org.slive.tr069.model.DeleteObjectResponse;
import org.slive.tr069.model.DownloadResponse;
import org.slive.tr069.model.FactoryResetResponse;
import org.slive.tr069.model.GetParameterAttributesResponse;
import org.slive.tr069.model.GetParameterNamesResponse;
import org.slive.tr069.model.GetParameterValuesResponse;
import org.slive.tr069.model.GetRPCMethodsResponse;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.InformResponse;
import org.slive.tr069.model.RebootResponse;
import org.slive.tr069.model.SetParameterAttributesResponse;
import org.slive.tr069.model.SetParameterValuesResponse;
import org.slive.tr069.model.TransferComplete;
import org.slive.tr069.model.TransferCompleteResponse;
import org.slive.tr069.model.UploadResponse;


/**
 * 分类解析器
 * @author Slive
 * @date 2013-5-16
 */
public final class ResponseParse
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseParse.class);
    private IResponseHandle responseHandle;

    public ResponseParse(IResponseHandle responseHandle)
    {
        if (responseHandle == null)
        {
            throw new NullPointerException();
        }
        this.responseHandle = responseHandle;
    }
    
    public IResponseHandle getResponseHandle()
	{
		return responseHandle;
	}
    
	/**
	 * 解析CPEResquest，并做相应的自动回复
	 * @param method
	 */
	public void parseCPEResquestMethod(AbstractMethod method)
	{
		try
		{
			ACSProcessControl acsPcl = responseHandle.getAcsProcessControl();
			AbstractMethod resMethod = null;
			responseHandle.beforeParse(method, null);           // 解析前的操作
			if (method instanceof Inform) // 接收到Inform，进行解析
			{
				responseHandle.parseInformMethod((Inform) method);
				resMethod = new InformResponse();
				if (acsPcl != null) // 回复
				{
					acsPcl.setMaxEnvelopes(((Inform) method).getMaxEnvelopes()); // TODO 理论上由ACS自身确定的，此次根据CPE来决定
				}
			}
            else if (method instanceof TransferComplete)
            {
                responseHandle.parseTranCmpMethod((TransferComplete) method);
                resMethod = new TransferCompleteResponse();
            }
			
			
			if(acsPcl != null && resMethod != null)		// 回复
    		{
				resMethod.setRequestId(method.getRequestId());
				acsPcl.addACSResponseMethod(resMethod);
    		}

		} catch (Exception e)
		{
			LOGGER.error("Parsed CPE request method error:" + method, e);
		}
	}

    /**
     * 解析ACS请求,CPE回复方法
     * @param confirmMethod
     */
	public void parseCPEResponseMethod(ACSConfirmMethod confirmMethod)
    {
        AbstractMethod msgModel = confirmMethod.getResponseMethod();
        Object attObj = confirmMethod.getAttachObj();
        responseHandle.beforeParse(msgModel, attObj);           // 解析前的操作
        System.err.println("Receive msg:");
        System.out.println("\t" + msgModel + "\r\n-------------------------------------------------------------------------------\r\n");
        try
        {
            if (msgModel instanceof GetParameterValuesResponse) // 接收到GetParameterValuesResponse，进行解析
            {
                responseHandle.parseGetParamValuesResMethod((GetParameterValuesResponse) msgModel, attObj);
            }
            else if (msgModel instanceof SetParameterValuesResponse) // 接收到SetParameterValuesResponse，进行解析
            {
                responseHandle.parseSetParamValuesResMethod((SetParameterValuesResponse) msgModel, attObj);
            }
            else if (msgModel instanceof GetParameterNamesResponse) // 接收到GetParameterNamesResponse，进行解析
            {
                responseHandle.parseGetParamNamesResMethod((GetParameterNamesResponse) msgModel, attObj);
            }
            else if (msgModel instanceof SetParameterAttributesResponse) // 接收到SetParameterAttributesResponse，进行解析
            {
                responseHandle.parseSetParamAttrResMethod((SetParameterAttributesResponse) msgModel, attObj);
            }
            else if (msgModel instanceof GetParameterAttributesResponse)
            {
                responseHandle.parseGetParamAttrResMethod((GetParameterAttributesResponse) msgModel, attObj);
            }
            else if (msgModel instanceof AddObjectResponse)
            {
                responseHandle.parseAddObjectResMethod((AddObjectResponse) msgModel, attObj);
            }
            else if (msgModel instanceof DeleteObjectResponse)
            {
                responseHandle.parseDelObjectResMethod((DeleteObjectResponse) msgModel, attObj);
            }
            else if (msgModel instanceof RebootResponse)
            {
                responseHandle.parseRebootResMethod((RebootResponse) msgModel, attObj);
            }
            else if (msgModel instanceof DownloadResponse)
            {
                responseHandle.parseDownloadResMethod((DownloadResponse) msgModel, attObj);
            }
            else if (msgModel instanceof UploadResponse)
            {
                responseHandle.parseUploadResMethod((UploadResponse) msgModel, attObj);
            }
            else if (msgModel instanceof FactoryResetResponse)
            {
                responseHandle.parseFactoryRestResMethod((FactoryResetResponse) msgModel, attObj);
            }
            else if (msgModel instanceof GetRPCMethodsResponse)
            {
                responseHandle.parseGetRPCResMethod((GetRPCMethodsResponse) msgModel, attObj);
            }
            else
            {
                responseHandle.parseOtherMethod(msgModel, attObj);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Parsed CPE response method error:" + msgModel,e);
        }
    }
}
