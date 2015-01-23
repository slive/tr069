
package org.slive.tr069.control;

import org.slive.tr069.model.Inform;

/**
 * @version 1.0.0
 * @since 1.0.0
 * @author Slive
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2013-05-28 Slive    created<br/>
 * <p/>
 */
public abstract class DefaultInformHandleAdpter implements IInformHandle
{

	private ACSProcessControl acsProcessControl;

	@Override
	public ACSProcessControl getAcsProcessControl()
	{
		return acsProcessControl;
	}

	@Override
	public void setAcsProcessControl(ACSProcessControl acsProcessControl)
	{
		this.acsProcessControl = acsProcessControl;
	}

	@Override
	public void diagnosticsCompleteEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kickedEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void periodicEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}
	
}
