package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 *
 * 该方法是由服务器用于创建多实例对象
 * @author Slive
 *
 */
public class AddObject extends AbstractMethod
{
    private static final long serialVersionUID = 3249418278006385847L;
    private static final String ObjectName = "ObjectName";
    private static final String CommandKey = "CommandKey";
    
    private String objectName;
    private String commandKey;

    public AddObject()
    {
        methodName = "AddObject";
        objectName = "";
        commandKey = "";
        this.acs2CpeEnv = false;		// 默认是CPE（Required）,ACS（Optinal）
    }

    public AddObject(String objectName, String commandKey)
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
        objectName = getRequestChildElement(body, ObjectName).getTextContent();
        commandKey = getRequestChildElement(body, CommandKey).getTextContent();
    }

    @Override
    public String toString()
    {
        StringBuilder sbd = new StringBuilder();
        sbd.append(" ObjectName:" + objectName);
        sbd.append(" CommandKey:" + commandKey);
        return super.toString() + sbd.toString();
    }

    public String getObjectName()
    {
        return objectName;
    }

    public void setObjectName(String objectName)
    {
        this.objectName = objectName;
    }

    public String getCommandKey()
    {
        return commandKey;
    }

    public void setCommandKey(String commandKey)
    {
        this.commandKey = commandKey;
    }
}
