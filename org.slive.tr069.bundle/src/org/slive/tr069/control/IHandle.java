package org.slive.tr069.control;
/**
 * @author Slive
 * @date 2013-5-29
 */
public interface IHandle
{
	/**
	 * 获取当前ACS控制流程类
	 * @return 不存在则返回null
	 */
	public ACSProcessControl getAcsProcessControl();

	public void setAcsProcessControl(ACSProcessControl pcl);
}
