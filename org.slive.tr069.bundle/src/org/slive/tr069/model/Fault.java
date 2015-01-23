package org.slive.tr069.model;


import org.slive.tr069.model.struct.FaultStruct;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Fault extends AbstractMethod
{
	private static final long serialVersionUID = -5209440807540799860L;
	
	private FaultStruct faultStruct;
	
	public Fault()
	{
		methodName ="Fault";
		faultStruct = new FaultStruct();
	}
	
	protected void parseFaultDetail(NodeList details)
	{
	}
	
    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        // TODO Auto-generated method stub
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    }

	public FaultStruct getFaultStruct()
	{
		return faultStruct;
	}


}
