package org.slive.tr069.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.struct.Event;
import org.slive.tr069.model.struct.EventStruct;


/**
 * @author Slive
 * @date 2013-5-29
 */
public final class InformParse
{
	private static final Logger LOGGER = LoggerFactory.getLogger(InformParse.class);
	private IInformHandle informHandle;
	
	public InformParse(IInformHandle informHandle)
	{
		if(informHandle == null)
		{
			throw new NullPointerException("Instance InformParse error,IInformHandle is null!");
		}
		this.informHandle = informHandle;
	}
	
	public IInformHandle getInformHandle()
	{
		return informHandle;
	}



	public void parseInform(Inform inform)
	{
		try
		{
			EventStruct[] events = inform.getEvent().getEventCodes();
			for (EventStruct eventStruct : events)
			{
				if(eventStruct.getEvenCode().equals(Event.CONNECTION_REQUEST))
				{
					informHandle.connectionRequestEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.BOOT))
				{
					informHandle.bootEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.TRANSFER_COMPLETE))
				{
					informHandle.transferCompleteEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.VALUE_CHANGE))
				{
					informHandle.valueChangeEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.BOOTSTRAP))
				{
					informHandle.bootStrapEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.DIALGONSTICS_COMPLETE))
				{
					informHandle.diagnosticsCompleteEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.KICKED))
				{
					informHandle.kickedEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.PERIODIC))
				{
					informHandle.periodicEvent(inform);
				}
				else if(eventStruct.getEvenCode().equals(Event.SCHEDULED))
				{
					informHandle.scheduleEvent(inform);
				}
				else
				{
					informHandle.scheduleEvent(inform);
				}

			}
			
		} catch (Exception e)
		{
			String msg = "parseInform error!";
			LOGGER.error(msg ,e);
		}
	}
}
