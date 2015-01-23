package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * 无应答参数
 * @author Slive
 *
 */
public class RebootResponse extends AbstractMethod
{
	private static final long serialVersionUID = 5343616840954981656L;

	public RebootResponse()
    {
        methodName = "RebootResponse";
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        // TODO 无应答参数

    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        // TODO 无应答参数

    }

}
