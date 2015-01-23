package org.slive.tr069.control.example;

import org.slive.tr069.control.DefaultResponseHandleAdpter;
import org.slive.tr069.model.DownloadResponse;
import org.slive.tr069.model.GetParameterAttributesResponse;
import org.slive.tr069.model.GetParameterNamesResponse;
import org.slive.tr069.model.GetParameterValuesResponse;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.RebootResponse;
import org.slive.tr069.model.SetParameterValuesResponse;
import org.slive.tr069.model.TransferComplete;


/**
 * @author Slive
 * @date 2013-5-24
 */
public class ResponseHandleExample extends DefaultResponseHandleAdpter
{
	
	@Override
	public void parseSetParamValuesResMethod(
			SetParameterValuesResponse setParamValuesRes, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseRebootResMethod(RebootResponse rebootRes, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseInformMethod(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseGetParamValuesResMethod(
			GetParameterValuesResponse getParamValuesRes, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseGetParamNamesResMethod(
			GetParameterNamesResponse getParamNamesRes, Object attObj)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void parseGetParamAttrResMethod(
			GetParameterAttributesResponse getParamAttrRes, Object attObj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void parseDownloadResMethod(DownloadResponse downLoadRes,
			Object attObj)
	{
		// TODO Auto-generated method stub
		System.out.println("DownloadResponse");
	}

	@Override
	public void parseTranCmpMethod(TransferComplete tranfCmp)
	{
		// TODO Auto-generated method stub
		super.parseTranCmpMethod(tranfCmp);
		System.out.println("TransferComplete");
		
	}
	
}
