package org.slive.tr069.control;

import org.slive.tr069.model.AbstractMethod;
import org.slive.tr069.model.AddObjectResponse;
import org.slive.tr069.model.DeleteObjectResponse;
import org.slive.tr069.model.FactoryResetResponse;
import org.slive.tr069.model.Fault;
import org.slive.tr069.model.GetRPCMethodsResponse;
import org.slive.tr069.model.SetParameterAttributesResponse;
import org.slive.tr069.model.TransferComplete;
import org.slive.tr069.model.UploadResponse;

/**
 * 默认解析代理实现
 * @author Slive
 * @date 2013-5-24
 */
public abstract class DefaultResponseHandleAdpter implements IResponseHandle
{
	private ACSProcessControl acsProcessControl;
	
	@Override
    public ACSProcessControl getAcsProcessControl()
	{
		return acsProcessControl;
	}

	@Override
	public void setAcsProcessControl(ACSProcessControl acsProcessControl)
	{
		this.acsProcessControl = acsProcessControl;
	}


	/**
     * 在解析前，先执行该方法，个重写，以实现自己所需的方法
     * @param method
     * @param attObj
     */
    @Override
    public void beforeParse(AbstractMethod method, Object attObj)
    {
        // TODO Auto-generated method stub
    }
    
    
	@Override
	public void parseAddObjectResMethod(AddObjectResponse addObjectRes,
			Object attObj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseDelObjectResMethod(DeleteObjectResponse delObjectRes,
			Object attObj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseFactoryRestResMethod(FactoryResetResponse fRestLoadRes,
			Object attObj)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void parseOtherMethod(AbstractMethod otherMethod, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseFault(Fault faultMethod,Object attObj)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void parseSetParamAttrResMethod(
			SetParameterAttributesResponse setParamAttrRes, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseUploadResMethod(UploadResponse upLoadRes, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseGetRPCResMethod(GetRPCMethodsResponse grpcMethods, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseTranCmpMethod(TransferComplete tranfCmp)
	{
		// TODO Auto-generated method stub
	}
}
