package org.slive.tr069.control.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slive.tr069.control.DefaultInformHandleAdpter;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.struct.DeviceId;
import org.slive.tr069.model.struct.ParameterList;
import org.slive.tr069.model.struct.ParameterValueStruct;


/**
 * @author Slive
 * @date 2013-5-28
 */
public class InformHandleExample extends DefaultInformHandleAdpter
{
	private static final Logger LOGGER = LoggerFactory.getLogger(InformHandleExample.class);

	@Override
	public void bootEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		updateSfuDevice(inform);
		
	}

	@Override
	public void bootStrapEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		updateSfuDevice(inform);
		
	}
	
	@Override
	public void periodicEvent(Inform inform)
	{
		updateSfuDevice(inform);
		super.periodicEvent(inform);
	}

	@Override
	public void connectionRequestEvent(Inform inform)
	{
		// TODO 连接后进行的操作，比如创建抽象设备之类的
		updateSfuDevice(inform);
	}

	@Override
	public void scheduleEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selfDefineEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferCompleteEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChangeEvent(Inform inform)
	{
		// TODO Auto-generated method stub
		
	}
	
    /**
     * Update sfu device information by cpe ip;<br>
     * Update elements mainly contain oui and serialNumber
     * @param inform
     * @param force
     */
	@SuppressWarnings("unchecked")
	private synchronized void updateSfuDevice(Inform inform)
	{
		try
		{
			List<ParameterValueStruct> parameterValueStructs = inform
					.getParameterList().getParameterValueStructs();
			for (ParameterValueStruct pvs : parameterValueStructs)
			{
				if (ParameterList.ConnectionRequestURL.equals(pvs.getName()))
				{
					String connUrl = (String) pvs.getValue();
					String ip = connUrl.split("://")[1].split(":")[0]; // 获取IP
					System.out.println("ACS ip is " + ip);
					DeviceId deviceId = inform.getDeviceId();
					System.out.println("ACS Device is " + deviceId);
				}
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			LOGGER.error("Get ip from Inform method error!", e);
		}
	}
}
