package org.slive.tr069.model;

import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Slive
 *
 */
public class GetParameterValues extends AbstractMethod
{

    private static final long serialVersionUID = 4363092438209246529L;
    private static final String ParameterNames = "ParameterNames";
    private static final String STR = "string";
    private List<String> parameterNames;

    public GetParameterValues()
    {
        methodName = "GetParameterValues";
        parameterNames = new ArrayList<String>();
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element paramNameElement = soapMessageModel.createElement(ParameterNames);
        int size = parameterNames.size();
        if (size > 0)
        {
			getArrayTypeAttribute(paramNameElement, STR, size);
			for (String paraName : parameterNames)
			{
				Element paraItem = soapMessageModel.createElement(STR);
				paraItem.setTextContent(paraName);
				paramNameElement.appendChild(paraItem);
			}
        }
        body.appendChild(paramNameElement);

    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        Element paramNameElement = getRequestChildElement(body, ParameterNames);
        NodeList paramValueElements = paramNameElement.getElementsByTagName(STR);
        int length = paramValueElements.getLength();
		for (int index = 0; index < length; index++)
        {
            Node paramValue = paramValueElements.item(index);
            parameterNames.add(paramValue.getTextContent());
        }

    }

    public List<String> getParameterNames()
    {
        return parameterNames;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" ParameterNames:" + parameterNames);
    	return super.toString() + sbd.toString();
    }
}
