package org.slive.tr069.model;

public class SharkInfo
{
    private long lastTime;
    
    private int state;
    
    private String sn;
    
    private String oui;
    
    private String ip;

    public long getLastTime()
    {
        return lastTime;
    }

    public void setLastTime(long lastTime)
    {
        this.lastTime = lastTime;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public String getOui()
    {
        return oui;
    }

    public void setOui(String oui)
    {
        this.oui = oui;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }
}
