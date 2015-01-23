package org.slive.tr069.model;

import java.util.ArrayList;
import java.util.List;


import org.slive.tr069.model.struct.ParameterInfoStruct;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class GetParameterNamesResponse extends AbstractMethod
{
    private static final long serialVersionUID = 6784697052875935411L;
    private static final String ParameterList = "ParameterList";
    private static final String ParameterInfoStruct = "ParameterInfoStruct";
    private static final String Name = "Name";
    private static final String Writable = "Writable";
    private List<ParameterInfoStruct> parameterList;

    public GetParameterNamesResponse()
    {
        methodName = "GetParameterNamesResponse";
        parameterList = new ArrayList<ParameterInfoStruct>();
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element paramListElement = soapMessageModel.createElement(ParameterList); 
        if(parameterList.size() > 0)
        {
        	getArrayTypeAttribute(paramListElement, ParameterList, parameterList.size());
	        for (ParameterInfoStruct pis : parameterList)
	        {
	            Element paramStructElement = soapMessageModel.createElement(ParameterInfoStruct);
	            Element nameItem = soapMessageModel.createElement(Name);
	            nameItem.setTextContent(pis.getName());
	            Element wtableItem = soapMessageModel.createElement(Writable);
	            wtableItem.setTextContent(Boolean.toString(pis.isWritable()));
	            paramStructElement.appendChild(nameItem);
	            paramStructElement.appendChild(wtableItem);
	            paramListElement.appendChild(paramStructElement);
	        }
        }
        body.appendChild(paramListElement);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
        Element paramListElement = getRequestChildElement(body, ParameterList);
        NodeList nodeList = paramListElement.getElementsByTagName(ParameterInfoStruct);
        for (int index = 0; index < nodeList.getLength(); index++)
        {
            Node paramStruct = nodeList.item(index);
            if (paramStruct instanceof Element)
            {
                ParameterInfoStruct pis = new ParameterInfoStruct();
                pis.setName(getRequestChildElement((Element) paramStruct, Name).getTextContent());
                String wrString = getRequestChildElement((Element) paramStruct, Writable).getTextContent();
                if("true".equalsIgnoreCase(wrString) || "false".equalsIgnoreCase(wrString))
                {
                	pis.setWritable(Boolean.parseBoolean(wrString));		// 标准的
                }
                else
                {
                	pis.setWritable("1".equals(wrString));		// 0代表false,1代表true
                }
                parameterList.add(pis);
            }
        }
    }

    public List<ParameterInfoStruct> getParameterList()
    {
        return parameterList;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append("\r\nParameterInfoStruct:");
    	for (ParameterInfoStruct pis : parameterList)
		{
    		sbd.append("\r\n" + pis);
		}
    	return super.toString() + sbd.toString();
    }
}
