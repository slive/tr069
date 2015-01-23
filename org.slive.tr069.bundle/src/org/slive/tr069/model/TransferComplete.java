package org.slive.tr069.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.slive.tr069.model.struct.FaultStruct;
import org.w3c.dom.Element;

/**
 * 
 * @author Slive
 *
 */
public class TransferComplete extends AbstractMethod
{
	private static final long serialVersionUID = 4576451215825091417L;
	
	// TODO UTC时间？？
	private static final DateFormat DtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	
	private static final String StartTime = "StartTime";
	private static final String CompleteTime = "CompleteTime";
	private static final String CommandKey = "CommandKey";
	private static final String FaultStruct = "FaultStruct";
	private static final String FaultCode = "FaultCode";
	private static final String FaultString = "FaultString";
	
	private String commandKey;
    private String startTime;
    private String completeTime;
    private FaultStruct faultStruct;

    public TransferComplete()
    {
        methodName = "TransferComplete";
        commandKey = "";
        
    	// 格式化为UTC时间
    	startTime = DtFormat.format(Calendar.getInstance().getTime());
    	completeTime = DtFormat.format(Calendar.getInstance().getTime());
    	faultStruct = new FaultStruct();
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element commandItem = soapMessageModel.createElement(CommandKey, commandKey);
        body.appendChild(commandItem);
        
        Element faultStructItem = soapMessageModel.createElement(FaultStruct);
        Element faultCodeItem = soapMessageModel.createElement(FaultCode);
        faultCodeItem.setTextContent(String.valueOf(faultStruct.getFaultCode()));
        faultStructItem.appendChild(faultCodeItem);
        Element faultStringItem = soapMessageModel.createElement(FaultString);
        faultStringItem.setTextContent(String.valueOf(faultStruct.getFaultString()));
        faultStructItem.appendChild(faultStringItem);
        body.appendChild(faultStructItem);

        Element startTimeItem = soapMessageModel.createElement(StartTime);
        startTimeItem.setTextContent(startTime);
        body.appendChild(startTimeItem);
        Element completeTimeItem = soapMessageModel.createElement(CompleteTime);
        completeTimeItem.setTextContent(completeTime);
        body.appendChild(completeTimeItem);

    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	commandKey = getRequestElement(body, CommandKey);
        startTime = getRequestElement(body, StartTime);
        completeTime = getRequestElement(body, CompleteTime);
        Element faultElement = getRequestChildElement(body, FaultStruct);
        int faultCode = 0;
        String faultString = "";
        if (faultElement != null)
        {
            faultCode= Integer.parseInt(getRequestElement(faultElement, FaultCode));
            faultString = getRequestElement(faultElement, FaultString);
        }
        faultStruct.setFault(faultCode, faultString);
    }

    public String getCommandKey()
    {
        return commandKey;
    }

    public void setCommandKey(String commandKey)
    {
        this.commandKey = commandKey;
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

    public FaultStruct getFaultStruct()
    {
        return faultStruct;
    }

    @Override
    public String toString()
    {
        return "TransferComplete [CommandKey=" + commandKey + ", FaultCode="
                + faultStruct.getFaultCode() + ", FaultString=" + faultStruct.getFaultString() + "]";
    }

}
