package org.slive.tr069.model;


import org.w3c.dom.Element;

/**
 * @author Slive
 * @date 2013-5-17
 */
public class HttpEmpty extends AbstractMethod
{

	private static final long serialVersionUID = -5264058671336779410L;
	
	public HttpEmpty()
	{
		methodName = "HttpEmpty";
	}

	@Override
	protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parseBody2Filed(Element body,
			SoapMessageModel soapMessageModel)
	{
		// TODO Auto-generated method stub
		
	}

}
