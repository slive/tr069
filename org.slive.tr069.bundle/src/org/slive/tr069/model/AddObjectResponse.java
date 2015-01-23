package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * @author Slive
 */
public class AddObjectResponse extends AbstractMethod
{
    private static final long serialVersionUID = -5668990636555831105L;
    private static final String InstanceNumber = "InstanceNumber";
    private static final String Status = "Status";

	private int instanceNumber;
	private int status;

    public AddObjectResponse()
    {
        methodName = "AddObjectResponse";
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element insEle = soapMessageModel.createElement(InstanceNumber);
        insEle.setTextContent(String.valueOf(instanceNumber));
        Element staEle = soapMessageModel.createElement(Status);
        staEle.setTextContent(String.valueOf(status));
		body.appendChild(insEle);
		body.appendChild(staEle);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        instanceNumber = Integer.parseInt(getRequestElement(body, InstanceNumber));
        status = Integer.parseInt(getRequestElement(body, Status));

    }

    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" InstanceNumber:" + instanceNumber);
    	sbd.append(" Status:" + status);
    	return super.toString() + sbd.toString();
    }
    
	public int getInstanceNumber()
	{
		return instanceNumber;
	}

	public void setInstanceNumber(int instanceNumber)
	{
		this.instanceNumber = instanceNumber;
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
