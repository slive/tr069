package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * 该方法无参数
 * @author Slive
 *
 */
public class SetParameterAttributesResponse extends AbstractMethod
{
	private static final long serialVersionUID = -8119782940419419164L;

	public SetParameterAttributesResponse()
    {
        methodName = "SetParameterAttributesResponse";
    }
	

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
    	  // TODO Auto-generated method stub
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        // TODO Auto-generated method stub
    }

}
