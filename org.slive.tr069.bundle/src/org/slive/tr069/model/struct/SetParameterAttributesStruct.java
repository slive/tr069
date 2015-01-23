package org.slive.tr069.model.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Slive
 */
public class SetParameterAttributesStruct
{
    private String name;					// 为空代表顶级路径
    private int notification;				// [0:2]
    private boolean notificationChange;
    private boolean accessListChange;
    private List<String> accesslist;

    public SetParameterAttributesStruct()
    {
        name = "";
        notification = 0;
        notificationChange = false;
        accessListChange = false;
        accesslist = new ArrayList<String>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

	/**
	 * 指定是否CPE应当将指定的该（或多个）参数的变化值放到其Inform报文中，以及是否当指定参数的值发生变化时，<br/>
	 * CPE应当发起一个到ACS的会话。值的定义如下：<br/>
	 * <ul>
	 * <li> 0 = Notification关闭。 CPE不必通过ACS指定参数的变化。
	 * <li> 1 = 被动式Notification。当指定的参数值变化时 ，CPE必须在下一次建立与ACS的会话时，<br/>
	 * 将这一新值包含在Inform报文的ParameterList中。
	 * <li> 2 = 主动式Notification。 一旦指定参数的值发生变化，CPE必须发起与ACS的会话，<br/>
	 * 将新值包含在相应的Inform报文的ParameterList中。因非零的Notification设置引起发送的Inform<br/>
	 * 报文中参数发生变化，事件代码“4 VALUE CHANGE”必须包括在事件列表中。如果试图给一个不适合设置<br/>
	 * notification值的参数（比如一个持续变化的统计值）设置notification值，CPE可以返回一个“notification request rejected”错误
	 * </ul>
	 */
    public int getNotification()
    {
        return notification;
    }

	/**
	 * 指定是否CPE应当将指定的该（或多个）参数的变化值放到其Inform报文中，以及是否当指定参数的值发生变化时，<br/>
	 * CPE应当发起一个到ACS的会话。值的定义如下：<br/>
	 * <ul>
	 * <li> 0 = Notification关闭。 CPE不必通过ACS指定参数的变化。
	 * <li> 1 = 被动式Notification。当指定的参数值变化时 ，CPE必须在下一次建立与ACS的会话时，<br/>
	 * 将这一新值包含在Inform报文的ParameterList中。
	 * <li> 2 = 主动式Notification。 一旦指定参数的值发生变化，CPE必须发起与ACS的会话，<br/>
	 * 将新值包含在相应的Inform报文的ParameterList中。因非零的Notification设置引起发送的Inform<br/>
	 * 报文中参数发生变化，事件代码“4 VALUE CHANGE”必须包括在事件列表中。如果试图给一个不适合设置<br/>
	 * notification值的参数（比如一个持续变化的统计值）设置notification值，CPE可以返回一个“notification request rejected”错误
	 * </ul>
	 */
    public void setNotification(int notification)
    {
        this.notification = notification;
    }
    
    /**
     * 如果为true，Notification的值将替换该参数或参数组的当前Notification的设置。<br/>
     * 如果是false，则不作任何Notification设置的改动。
     * @return
     */
    public boolean isNotificationChange()
    {
        return notificationChange;
    }

    /**
     * 如果为true，Notification的值将替换该参数或参数组的当前Notification的设置。<br/>
     * 如果是false，则不作任何Notification设置的改动。
     * @return
     */
    public void setNotificationChange(boolean notificationChange)
    {
        this.notificationChange = notificationChange;
    }

    public boolean isAccessListChange()
    {
        return accessListChange;
    }

    public void setAccessListChange(boolean accessListChange)
    {
        this.accessListChange = accessListChange;
    }

    public void addAccessObj(String object)
    {
        accesslist.add(object);
    }

    public String[] getAccessList()
    {
        String[] al = new String[accesslist.size()];
        accesslist.toArray(al);
        return al;
    }
}
