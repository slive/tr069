package org.slive.tr069.model;

import org.w3c.dom.Element;

public class InformResponse extends AbstractMethod
{
	private static final long serialVersionUID = 6681115948730320709L;
	public static final String MAXENV = "MaxEnvelopes";

    private int maxEnvelopes;

    public InformResponse()
    {
        methodName = "InformResponse";
    }

    public InformResponse(String _id, int maxEvn)
    {
        methodName = "InformResponse";
        requestId = _id;
        maxEnvelopes = maxEvn;
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
         Element maxItem = soapMessageModel.createElement(MAXENV);
         maxItem.setTextContent(String.valueOf(maxEnvelopes));
         body.appendChild(maxItem);

    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	maxEnvelopes = Integer.parseInt(getRequestChildElement(body,MAXENV).getTextContent());
    }

    /**
     * 为0时，不限制
     * @return
     */
	public int getMaxEnvelopes()
	{
		return maxEnvelopes;
	}

	public void setMaxEnvelopes(int maxEnvelopes)
	{
		if(maxEnvelopes < 0)
		{
			this.maxEnvelopes = 0;
		}
		else
		{
			this.maxEnvelopes = maxEnvelopes;
		}
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " MaxEnvelopes:" + maxEnvelopes;
	}

}
