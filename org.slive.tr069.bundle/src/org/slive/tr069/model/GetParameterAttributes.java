package org.slive.tr069.model;

import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetParameterAttributes extends AbstractMethod
{
	private static final long serialVersionUID = -2626085124691881106L;
	private static final String ParameterNames = "ParameterNames";
	private static final String STR = "string";
	
	private List<String> parameterNames;
	
    public GetParameterAttributes()
    {
        methodName = "GetParameterAttributes";
        parameterNames = new ArrayList<String>();
    }

	public synchronized void addParameterName(String parameterName)
	{
		parameterNames.add(parameterName);
	}
	
    public List<String> getParameterNames()
	{
		return parameterNames;
	}
    
    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element paramAttrElement = soapMessageModel.createElement(ParameterNames);
        if(parameterNames.size() > 0)
        {
        	getArrayTypeAttribute(paramAttrElement,STR,parameterNames.size());
	        for (String pName: parameterNames)
	        {
	            Element pElement = soapMessageModel.createElement(STR);
	            pElement.setTextContent(pName);
				paramAttrElement.appendChild(pElement);
	        }
        }
        body.appendChild(paramAttrElement);
    }

	@Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        Element paramNameElement = getRequestChildElement(body, ParameterNames);
        NodeList nodeList = paramNameElement.getChildNodes();
        for (int index = 0; index < nodeList.getLength(); index++)
        {
            Node item = nodeList.item(index);
            if (item.getNodeName().equals(STR))
            {
                String value = item.getTextContent();
                if (value != null && !value.equals(""))
                {
                    parameterNames.add(value);
                }
            }
        }
    }

    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" ParameterNames:" + parameterNames.toString());
    	return super.toString() + sbd.toString();
    }
	
}
