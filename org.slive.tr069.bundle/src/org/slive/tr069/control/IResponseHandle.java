package org.slive.tr069.control;

import org.slive.tr069.model.AbstractMethod;
import org.slive.tr069.model.AddObjectResponse;
import org.slive.tr069.model.DeleteObjectResponse;
import org.slive.tr069.model.DownloadResponse;
import org.slive.tr069.model.FactoryResetResponse;
import org.slive.tr069.model.Fault;
import org.slive.tr069.model.GetParameterAttributesResponse;
import org.slive.tr069.model.GetParameterNamesResponse;
import org.slive.tr069.model.GetParameterValuesResponse;
import org.slive.tr069.model.GetRPCMethodsResponse;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.RebootResponse;
import org.slive.tr069.model.SetParameterAttributesResponse;
import org.slive.tr069.model.SetParameterValuesResponse;
import org.slive.tr069.model.TransferComplete;
import org.slive.tr069.model.UploadResponse;

/**
 * 根据不同方法进行解析
 * @author Slive
 * @date 2013-5-16
 */
public interface IResponseHandle extends IHandle
{
	
    /**
     * 在解析前，先执行该方法，个重写，以实现自己所需的方法
     * @param method
     * @param attObj
     */
    public void beforeParse(AbstractMethod method, Object attObj);

    /**
     * 解析"Inform"
     */
    public void parseInformMethod(Inform inform);
    
    /**
     * 解析"TransferComplete"
     */
    public void parseTranCmpMethod(TransferComplete tranfCmp);

    /**
     * 解析"GetParameterValuesResponse"
     */
    public void parseGetParamValuesResMethod(GetParameterValuesResponse getParamValuesRes, Object attObj);

    /**
     * 解析"SetParameterValuesResponse"
     */
    public void parseSetParamValuesResMethod(SetParameterValuesResponse setParamValuesRes, Object attObj);

    /**
     * 解析"GetParameterNamesResponse"
     */
    public void parseGetParamNamesResMethod(GetParameterNamesResponse getParamNamesRes, Object attObj);

    /**
     * 解析"GetParameterAttributesResponse"
     */
    public void parseGetParamAttrResMethod(GetParameterAttributesResponse getParamAttrRes, Object attObj);

    /**
     * 解析"SetParameterAttributesResponse"
     */
    public void parseSetParamAttrResMethod(SetParameterAttributesResponse setParamAttrRes, Object attObj);

    /**
     * 解析"AddObjectResponse"
     */
    public void parseAddObjectResMethod(AddObjectResponse addObjectRes, Object attObj);

    /**
     * 解析"DeleteObjectResponse"
     */
    public void parseDelObjectResMethod(DeleteObjectResponse delObjectRes, Object attObj);

    /**
     * 解析"RebootResponse"
     */
    public void parseRebootResMethod(RebootResponse rebootRes, Object attObj);

    /**
     * 解析"DownloadResponse"
     */
    public void parseDownloadResMethod(DownloadResponse downLoadRes, Object attObj);

    /**
     * 解析"UploadResponse"
     */
    public void parseUploadResMethod(UploadResponse upLoadRes, Object attObj);

    /**
     * 解析"FactoryResetResponse"
     */
    public void parseFactoryRestResMethod(FactoryResetResponse fRestLoadRes, Object attObj);

    /**
     * 解析Fault
     */
    public void parseFault(Fault fault, Object attObj);

    /**
     * 解析"GetRPCMethodsResponse"
     */
    public void parseGetRPCResMethod(GetRPCMethodsResponse grpcMethods, Object attObj);

    /**
     * 解析除了上述方法，用户自定义方法
     */
    public void parseOtherMethod(AbstractMethod otherMethod, Object attObj);
}
