package org.slive.tr069.model;

import org.w3c.dom.Element;

public class SetParameterValuesResponse extends AbstractMethod
{
	private static final long serialVersionUID = 2242691896959501518L;
	public static final int Status_Submit_Use = 0;
	public static final int Status_Submit = 1;

	private static final String Status = "Status";

    private int status;

    public SetParameterValuesResponse()
    {
        methodName = "SetParameterValuesResponse";
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element item = soapMessageModel.createElement(Status);
        item.setTextContent(Integer.toString(status));
        body.appendChild(item);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        status = Integer.parseInt(getRequestElement(body, Status));
    }
    
    public int getStatus()
    {
        return status;
    }

    /**
     * 只能设置0或者1状态
     * @param status
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    @Override
    public String toString()
    {
    	return super.toString() + " Status:" + (status == 0?"Set_And_Use":"Just_Set");
    }

}
