package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * @author Slive
 */
public class DeleteObject extends AbstractMethod
{
	private static final long serialVersionUID = 8407006987085444751L;
	private static final String ObjectName = "ObjectName";
	private static final String CommandKey = "CommandKey";
	
    private String commandKey;
    private String objectName;

    public DeleteObject()
    {
        methodName = "DeleteObject";
        objectName = "";
        commandKey = "";
        this.acs2CpeEnv = false;		// 默认是CPE（Required）,ACS（Optinal）
    }

    public DeleteObject(String objectName, String commandKey)
    {
        this();
        this.commandKey = commandKey;
        this.objectName = objectName;
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element objItem = soapMessageModel.createElement(ObjectName);
        objItem.setTextContent(objectName);
        Element commanItem = soapMessageModel.createElement(CommandKey, commandKey);
        commanItem.setTextContent(commandKey);
        body.appendChild(objItem);
        body.appendChild(commanItem);

    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	objectName = getRequestChildElement(body,ObjectName).getTextContent();
    	commandKey = getRequestChildElement(body,CommandKey).getTextContent();
    }

    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" ObjectName:" + objectName);
    	sbd.append(" CommandKey:" + commandKey);
    	return super.toString() + sbd.toString();
    }
    
	public String getCommandKey()
	{
		return commandKey;
	}

	public void setCommandKey(String commandKey)
	{
		this.commandKey = commandKey;
	}

	public String getObjectName()
	{
		return objectName;
	}

	public void setObjectName(String objectName)
	{
		this.objectName = objectName;
	}
    
}
