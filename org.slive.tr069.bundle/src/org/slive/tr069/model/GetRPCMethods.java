package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * 
 * @author Slive
 * 用于CPE或者ACS来发现对方的方法集。<br>
 * 任何无法解析的方法都应该忽略掉。
 *
 */
public class GetRPCMethods extends AbstractMethod
{
	private static final long serialVersionUID = 335073989232385528L;

	public GetRPCMethods() 
	{
        methodName = "GetRPCMethods";
    }
    public GetRPCMethods(String _id) 
    {
        this();
        requestId = _id;
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        
    }

}
