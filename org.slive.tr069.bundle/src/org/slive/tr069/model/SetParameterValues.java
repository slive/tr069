package org.slive.tr069.model;


import org.slive.tr069.model.struct.ParameterList;
import org.w3c.dom.Element;


public class SetParameterValues extends AbstractMethod
{
	private static final long serialVersionUID = -7720852412139686283L;
    private static final String ParameterKey = "ParameterKey";
    
    private ParameterList parameterList;
    private String parameterKey;

    public SetParameterValues()
    {
        methodName = "SetParameterValues";
        parameterList = new ParameterList();	// TODO
    }


    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        parameterList.addThisToBody(body, soapMessageModel);
        Element parameter = soapMessageModel.createElement(ParameterKey);
        parameter.setTextContent(parameterKey);
        body.appendChild(parameter);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	parameterList.parseBodyOfThis(body, soapMessageModel);
    	parameterKey = getRequestElement(body,ParameterKey);
    }
    
	public String getParamterKey()
	{
		return parameterKey;
	}

	public void setParamterKey(String key)
	{
		this.parameterKey = key;
	}

	public ParameterList getParameterList()
	{
		return parameterList;
	}
}
