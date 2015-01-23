package org.slive.tr069.control;

import org.slive.tr069.model.Inform;

/**
 * CPE与ACS第一次通信时，需要解析的Inform信息<br/>
 * <ul>
 * <li>"0 BOOTSTRAP" 表明会话发起原因是CPE首次安装或ACS的URL发生变化。
 * <li>"1 BOOT" 表明会话发起原因是CPE加电或重置，包括系统首次启动，以及因任何原因而引起的重启，包括使用Reboot方法。
 * <li>"2 PERIODIC" 表明会话发起原因是定期的Inform引起。
 * <li>"3 SCHEDULED" 表明会话发起原因是调用了ScheduleInform方法。
 * <li>"4 VALUE CHANGE" 表明会话发起原因是一个或多个参数值的变化。该参数值包括在Inform方法的调用中。例如CPE分配了新的IP地址。
 * <li>"5 KICKED" 表明会话发起原因是为了web标识管理（见附录D），及在本会话中将会调用Kicked方法（见A.4.2.1节）。
 * <li>"6 CONNECTION REQUEST" 表明会话发起原因是3.2节中定义的源自服务器的Connection Request
 * notification。
 * <li>"7 TRANSFER COMPLETE"
 * 表明会话的发起是为了表明以前请求的下载或上载（不管是否成功）已经结束，在此会话中将要调用一次或多次TransferComplete方法。
 * <li>"8 DIAGNOSTICS COMPLETE" 当完成由ACS发起的诊断测试结束后，重新与ACS建立连接时使用。如DSL环路诊断（见附录B）。
 * <li>"M "<method name> 如果这是另一方法的结果，值“M”后紧接着一个空格和方法的名称。例如： “M Reboot” “X “<OUI>
 * 厂商自定义事件。在“X”后的OUI和空格是组织唯一标识符，表示为6位十六进制值，均使用大写，并包括任何前置零。
 * 该值必须是在[9]中定义的有效OUI。对<event>的值与解释是厂商自定义的。例如：“X 00D09E MyEvent”
 * @version 1.0.0
 * @since 1.0.0
 * @author Slive
 * @history<br/> ver date author desc 1.0.0 2013-05-28 Slive created<br/>
 *               <p/>
 */
public interface IInformHandle extends IHandle
{
    /**
     * "0 BOOTSTRAP" 表明会话发起原因是CPE首次安装或ACS的URL发生变化。 
     */
    public void bootStrapEvent(Inform inform);
    
    /**
     * "1 BOOT" 表明会话发起原因是CPE加电或重置，包括系统首次启动，以及因任何原因而引起的重启，包括使用Reboot方法。
     * @param inform
     */
    public void bootEvent(Inform inform);
    
    /**
     * "2 PERIODIC" 表明会话发起原因是定期的Inform引起。
     * @param inform
     */
    public void periodicEvent(Inform inform);
    
    /**
     * "3 SCHEDULED" 表明会话发起原因是调用了ScheduleInform方法。
     * @param inform
     */
    public void scheduleEvent(Inform inform);
    
    
    /**
     * "4 VALUE CHANGE" 表明会话发起原因是一个或多个参数值的变化。该参数值包括在Inform方法的调用中。例如CPE分配了新的IP地址。
     * @param inform
     */
    public void valueChangeEvent(Inform inform);
    
    /**
     * "5 KICKED" 表明会话发起原因是为了web标识管理（见附录D），及在本会话中将会调用Kicked方法（见A.4.2.1节）。
     * @param inform
     */
    public void kickedEvent(Inform inform);
    
    /**
     * "6 CONNECTION REQUEST" 表明会话发起原因是3.2节中定义的源自服务器的Connection Request
     * @param inform
     */
    public void connectionRequestEvent(Inform inform);
    
    /**
     * "7 TRANSFER COMPLETE" 表明会话的发起是为了表明以前请求的下载或上载（不管是否成功）已经结束，在此会话中将要调用一次或多次TransferComplete方法。
     * @param inform
     */
    public void transferCompleteEvent(Inform inform);
    
    /**
     * "8 DIAGNOSTICS COMPLETE" 当完成由ACS发起的诊断测试结束后，重新与ACS建立连接时使用。如DSL环路诊断（见附录B）。
     * @param inform
     */
    public void diagnosticsCompleteEvent(Inform inform);
    
    /**
     *	"M "<method name> 如果这是另一方法的结果，值“M”后紧接着一个空格和方法的名称。例如： “M Reboot” “X “<OUI>
	 * 厂商自定义事件。在“X”后的OUI和空格是组织唯一标识符，表示为6位十六进制值，均使用大写，并包括任何前置零。
	 * 该值必须是在[9]中定义的有效OUI。对<event>的值与解释是厂商自定义的。例如：“X 00D09E MyEvent”
     */
    public void selfDefineEvent(Inform inform);
    
}
