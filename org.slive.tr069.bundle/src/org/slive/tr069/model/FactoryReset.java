package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * 这一方法将重置CPE到其出厂默认状态。该方法使用时需要万分小心
 */
public class FactoryReset extends AbstractMethod
{

    private static final long serialVersionUID = 1L;

    public FactoryReset()
    {
        methodName = "FactoryReset";
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
