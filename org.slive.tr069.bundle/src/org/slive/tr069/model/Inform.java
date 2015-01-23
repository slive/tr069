package org.slive.tr069.model;

import org.slive.tr069.model.struct.DeviceId;
import org.slive.tr069.model.struct.Event;
import org.slive.tr069.model.struct.ParameterList;
import org.w3c.dom.Element;

public class Inform extends AbstractMethod
{
    private static final long serialVersionUID = -2030090558965444198L;
    public static final String MAXENV = "MaxEnvelopes";
    public static final String RETRYCOUNT = "RetryCount";
    public static final String CURRENTTIME = "CurrentTime";
    private DeviceId deviceId;
    private Event event;
    private int retryCount;
    private int maxEnvelopes;
    private String currentTime;
    private ParameterList parameterList;

    public Inform() 
    {
        methodName = "Inform";
        deviceId = new DeviceId();
        event = new Event();
        parameterList = new ParameterList();
    }

    public DeviceId getDeviceId()
	{
		return deviceId;
	}

	public Event getEvent()
	{
		return event;
	}

    public ParameterList getParameterList()
	{
		return parameterList;
	}

	@Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
		deviceId.addThisToBody(body, soapMessageModel);
		event.addThisToBody(body, soapMessageModel);
        Element maxnv = soapMessageModel.createElement(MAXENV);
        maxnv.setTextContent(Integer.toString((maxEnvelopes)));
        Element retry = soapMessageModel.createElement(RETRYCOUNT);
        retry.setTextContent(Integer.toString(retryCount));
        Element curren = soapMessageModel.createElement(CURRENTTIME);
        curren.setTextContent(currentTime);
        body.appendChild(maxnv);
        body.appendChild(retry);
        body.appendChild(curren);
		parameterList.addThisToBody(body, soapMessageModel);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        deviceId.parseBodyOfThis(body, soapMessageModel);
        maxEnvelopes = Integer.parseInt(getRequestElement(body, MAXENV));
        retryCount = Integer.parseInt(getRequestElement(body, RETRYCOUNT));
        currentTime = getRequestElement(body, CURRENTTIME);
        event.parseBodyOfThis(body, soapMessageModel);
        parameterList.parseBodyOfThis(body, soapMessageModel);
    }

    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" DeviceId:" + deviceId);
    	sbd.append("\r\nMaxEnvelopes:" + maxEnvelopes);
    	sbd.append(" RetryCount:" + retryCount);
    	sbd.append(" CurrentTime:" + currentTime);
    	sbd.append("\r\nEvent:" + event);
    	sbd.append("\r\nParameterList:" + parameterList.toString());
    	return super.toString() + sbd.toString();
    }

    public int getRetryCount()
    {
        return retryCount;
    }

    public void setRetryCount(int retryCount)
    {
        this.retryCount = retryCount;
    }

    public String getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(String currentTime)
    {
        this.currentTime = currentTime;
    }

    public int getMaxEnvelopes()
    {
        return maxEnvelopes;
    }

    public void setMaxEnvelopes(int maxEnvelopes)
    {
    	if(maxEnvelopes <= 0)
    	{
    		 this.maxEnvelopes = 1;
    	}
    	else
    	{
    		this.maxEnvelopes = maxEnvelopes;
    	}
    }
}
