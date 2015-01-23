package org.slive.tr069.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.Element;

public class UploadResponse extends AbstractMethod
{
	private static final long serialVersionUID = 6314412918752326329L;
	
	private static final String Status = "Status";
	private static final String StartTime = "StartTime";
	private static final String CompleteTime = "CompleteTime";
	
	// TODO UTC时间？？
	private static final DateFormat DtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
    
	private int status;
    private String startTime;
    private String completeTime;

    public UploadResponse()
    {
        methodName = "UploadResponse";
    	status = 0;
    	
    	// 格式化为UTC时间
    	startTime = DtFormat.format(Calendar.getInstance().getTime());
    	completeTime = DtFormat.format(Calendar.getInstance().getTime());
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {

        Element statusItem = soapMessageModel.createElement(Status);
        statusItem.setTextContent(String.valueOf(status));
        Element startTimeItem = soapMessageModel.createElement(StartTime);
        startTimeItem.setTextContent(startTime);
        Element completeTimeItem = soapMessageModel.createElement(CompleteTime);
        completeTimeItem.setTextContent(completeTime);
        body.appendChild(statusItem);
        body.appendChild(startTimeItem);
        body.appendChild(completeTimeItem);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        startTime = getRequestElement(body, StartTime);
        completeTime = getRequestElement(body, CompleteTime);
        status = Integer.parseInt(getRequestElement(body, Status));
        
    }
    
    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getCompleteTime()
    {
        return completeTime;
    }

    public void setCompleteTime(String completeTime)
    {
        this.completeTime = completeTime;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

}
