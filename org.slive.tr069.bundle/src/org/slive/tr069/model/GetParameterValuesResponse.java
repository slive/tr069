package org.slive.tr069.model;


import org.slive.tr069.model.struct.ParameterList;
import org.w3c.dom.Element;


public class GetParameterValuesResponse extends AbstractMethod
{

    private static final long serialVersionUID = -759158187039539126L;
    private ParameterList parameterList;

    public GetParameterValuesResponse()
    {
        methodName = "GetParameterValuesResponse";
        parameterList = new ParameterList();
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        parameterList.addThisToBody(body, soapMessageModel);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        parameterList.parseBodyOfThis(body, soapMessageModel);
    }

    public ParameterList getParameterList()
    {
        return parameterList;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append("\r\nParameterList:" + parameterList);
    	return super.toString() + sbd.toString();
    }
}
