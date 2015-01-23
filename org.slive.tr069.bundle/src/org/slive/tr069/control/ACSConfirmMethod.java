package org.slive.tr069.control;

import org.slive.tr069.model.AbstractMethod;

/**
 * ACS请求，CPE回复，并最终确认
 * @author Slive
 * @date 2013-5-15
 */
public class ACSConfirmMethod
{
    private AbstractMethod requestMethod;
    private AbstractMethod responseMethod;
    private boolean confirm;
    private long startTime;
    private long endTime;
    private int timeOut;
    private Object attachObj;                             // 日志或者标识

    public ACSConfirmMethod(AbstractMethod requestMethod)
    {
        if (requestMethod == null)
        {
            throw new NullPointerException();
        }
        this.requestMethod = requestMethod;
        this.confirm = false;
        startTime = System.currentTimeMillis();
        timeOut = 1000 * 60 * 10;
    }

    public ACSConfirmMethod(AbstractMethod requestMethod, Object attachObj)
    {
        this(requestMethod);
        this.attachObj = attachObj;
    }

    public AbstractMethod getResponseMethod()
    {
        return responseMethod;
    }

    /**
     * 注意：只能确认一次
     * @param responseMethod
     */
    public void setResponseMethod(AbstractMethod responseMethod)
    {
        if (responseMethod == null)
        {
            return;
        }

        if (!confirm && (requestMethod.getRequestId().equals(responseMethod.getRequestId())))
        {
            confirm = true;
            this.responseMethod = responseMethod;
            endTime = System.currentTimeMillis();
        }
    }

    public AbstractMethod getRequestMethod()
    {
        return requestMethod;
    }

    /**
     * 当请求的方法的ID和回复的方法的ID一致时，为已确认
     * @return
     */
    public boolean isConfirm()
    {
        return confirm;
    }

    public boolean isTimeOut()
    {
        if ((System.currentTimeMillis() - startTime) > timeOut)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getTimeOut()
    {
        return timeOut;
    }

    public void setTimeOut(int timeOut)
    {
        this.timeOut = timeOut;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public long getEndTime()
    {
        return endTime;
    }

    /**
     * 返回附加对象
     * @return 返回null代表无附加信息
     */
    public Object getAttachObj()
    {
        return attachObj;
    }
}
