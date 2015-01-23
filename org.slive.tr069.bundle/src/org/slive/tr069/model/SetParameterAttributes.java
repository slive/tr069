package org.slive.tr069.model;

import java.util.ArrayList;
import java.util.List;


import org.slive.tr069.model.struct.SetParameterAttributesStruct;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SetParameterAttributes extends AbstractMethod
{

    private static final long serialVersionUID = -7757475898124093029L;
    private static final String ParameterList = "ParameterList";
    private static final String SetParameterAttributesStruct = "SetParameterAttributesStruct";
    private static final String Name = "Name";
    private static final String NotificationChange = "NotificationChange";
    private static final String Notification = "Notification";
    private static final String AccessList = "AccessList";
    private static final String AccessListChange = "AccessListChange";
    private static final String STR = "string";
    private List<SetParameterAttributesStruct> setParamAttrList;

    public SetParameterAttributes()
    {
        methodName = "SetParameterAttributes";
        setParamAttrList = new ArrayList<SetParameterAttributesStruct>();
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {

        Element paramElement = soapMessageModel.createElement(ParameterList);
        if(setParamAttrList.size() > 0)
        {
	        getArrayTypeAttribute(paramElement, SetParameterAttributesStruct, setParamAttrList.size());
	        for (SetParameterAttributesStruct spa : setParamAttrList)
	        {
	            Element paramAttrStructElement = soapMessageModel.createElement(SetParameterAttributesStruct);
	
	            Element nameItem = soapMessageModel.createElement(Name);
	            nameItem.setTextContent(spa.getName());
	            paramAttrStructElement.appendChild(nameItem);
	            Element notificationItem = soapMessageModel.createElement(Notification);
	            notificationItem.setTextContent(String.valueOf(spa.getNotification()));
	            paramAttrStructElement.appendChild(notificationItem);
	            Element notifchangeItem = soapMessageModel.createElement(NotificationChange);
	            notifchangeItem.setTextContent(Boolean.toString(spa.isNotificationChange()));
	            paramAttrStructElement.appendChild(notifchangeItem);
	            Element accChangeItem = soapMessageModel.createElement(AccessListChange);
	            accChangeItem.setTextContent(Boolean.toString(spa.isAccessListChange()));
	            paramAttrStructElement.appendChild(accChangeItem);
	            String[] accessArray = spa.getAccessList();
	            
	            Element accessListItem = soapMessageModel.createElement(AccessList);
	            getArrayTypeAttribute(accessListItem, STR, accessArray.length);
	            for (String access : accessArray)
	            {
	                Element strItem = soapMessageModel.createElement(STR);
	                strItem.setTextContent(access);
	                accessListItem.appendChild(strItem);
	            }
	            paramAttrStructElement.appendChild(accessListItem);
	            paramElement.appendChild(paramAttrStructElement);
	        }
        }

        body.appendChild(paramElement);       

    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {

        //////////////////////////////
        Element paramElement = getRequestChildElement(body, ParameterList);
        NodeList nodeList = paramElement.getChildNodes();

        for (int index = 0; index < nodeList.getLength(); index++)
        {
            Node item = nodeList.item(index);
            if (item.getNodeName().equals(SetParameterAttributesStruct))
            {
                SetParameterAttributesStruct pas = new SetParameterAttributesStruct();
                NodeList structList = item.getChildNodes();

                //获取ParameterAttributesStruct下的
                for (int i = 0; i < structList.getLength(); i++)
                {
                    Node element = structList.item(i);
                    if (element.getNodeName().equals(Name))
                    {
                        pas.setName(element.getTextContent());

                    }
                    else if (element.getNodeName().equals(Notification))
                    {
                        pas.setNotification(Integer.parseInt(element.getTextContent()));
                    }
                    else if (element.getNodeName().equals(NotificationChange))
                    {
                        pas.setNotificationChange(Boolean.parseBoolean(element.getTextContent()));
                    }
                    else if (element.getNodeName().equals(AccessListChange))
                    {
                        pas.setAccessListChange(Boolean.parseBoolean(element.getTextContent()));
                    }
                    //读取STR
                    else if (element.getNodeName().equals(AccessList))
                    {
                        NodeList accessNodeList = element.getChildNodes();
                        for (int acc = 0; acc < accessNodeList.getLength(); acc++)
                        {
                            Node accElement = structList.item(i);
                            if (accElement.getNodeName().equals(STR))
                            {
                                pas.addAccessObj(accElement.getTextContent());
                            }
                        }
                    }
                }
                setParamAttrList.add(pas);
            }

        }

    }

    public List<SetParameterAttributesStruct> getSetParamAttrList()
    {
        return setParamAttrList;
    }
}
