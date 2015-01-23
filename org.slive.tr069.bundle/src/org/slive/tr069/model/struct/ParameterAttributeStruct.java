package org.slive.tr069.model.struct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取参数属性结构
 * @author Slive
 */
public class ParameterAttributeStruct implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String name;
    private int notification;
    private List<String> accessList;

    public ParameterAttributeStruct()
    {
        name = "";
        notification = 0;
        accessList = new ArrayList<String>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNotification()
    {
        return notification;
    }

    public void setNotification(int notification)
    {
        this.notification = notification;
    }

    public void addAccessObj(String object)
    {
        accessList.add(object);
    }

    public String[] getAccessList()
    {
        String[] al = new String[accessList.size()];
        accessList.toArray(al);
        return al;
    }

    @Override
    public String toString()
    {
        StringBuilder sbd = new StringBuilder();
        sbd.append("Name:" + name);
        sbd.append(" Notification:" + notification);
        sbd.append(" AccessList:" + accessList.toString());
        return sbd.toString();
    }
}
