package org.slive.tr069.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.Element;

/**
 * @author Slive
 */
public class DownloadResponse extends AbstractMethod
{
	private static final long serialVersionUID = 576689709773811355L;
	
	public static final int Status_DownLoad_Use = 0;
	public static final int Status_DownLoad_NotUse = 1;
	public static final int Status_NoResponse = 2;

	private static final String Status = "Status";
	private static final String StartTime = "StartTime";
	private static final String CompleteTime = "CompleteTime";
	
	// TODO UTC时间？？
	private static final DateFormat DtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	

	private int status;
    private String startTime;
    private String completeTime;
    
    public DownloadResponse()
    {
    	methodName = "DownloadResponse";
    	status = 0;
    	
    	// 格式化为UTC时间
    	startTime = DtFormat.format(Calendar.getInstance().getTime());
    	completeTime = DtFormat.format(Calendar.getInstance().getTime());
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element statusItem = soapMessageModel.createElement(Status);
        statusItem.setTextContent(String.valueOf(status));
        Element startTimeItem = soapMessageModel.createElement(StartTime);
        startTimeItem.setTextContent(startTime);
        Element completeTimeItem = soapMessageModel.createElement(CompleteTime);
        completeTimeItem.setTextContent(completeTime);
        body.appendChild(statusItem);
        body.appendChild(startTimeItem);
        body.appendChild(completeTimeItem);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	status = Integer.parseInt(getRequestElement(body, Status));
        startTime = getRequestElement(body, StartTime);
        completeTime = getRequestElement(body, CompleteTime);

    }

    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" Status:" + status);
    	sbd.append(" StartTime:" + startTime);
    	sbd.append(" CompleteTime:" + completeTime);
    	return super.toString() + sbd.toString();
    }

	/**
	 * 对该方法的成功应答返回一个整数型的枚举值定义如下： 0 = 下载已结束并已应用。<br/>
	 * 1 = 下载还没有结束（比如，CPE 需要重新启动后才能执行文 件的下载）。 如果该参数的值非零，<br/>
	 * 则 CPE 必须在本会话的后续时间，或者 在其后的会话中，调用 TransferComplete方法来说明本次下载的 完成状态（成功或者失败）。
	 */
	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getCompleteTime()
	{
		return completeTime;
	}

	public void setCompleteTime(String completeTime)
	{
		this.completeTime = completeTime;
	}
}
