package org.slive.tr069.control;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slive.tr069.model.AbstractMethod;
import org.slive.tr069.model.Inform;
import org.slive.tr069.model.InformResponse;
import org.slive.tr069.model.struct.DeviceId;


/**
 * 流程过程控制
 * @author Slive
 * @date 2013-5-14
 */
public class ACSProcessControl
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ACSProcessControl.class);
    // CPE(Customer Premise Equipment 客户终端设备)设备唯一识别号，由OUI Product SerialNumber等组成,不对应网管的任何设备的ID
    private String cpeId;
    private int maxEnvelopes;
    private long gRequestId;						// 全局请求ID
    private Inform deviceInfor;						// 保存的设备信息
    private ResponseParse responseParse;			// 针对cpe的方法进行解析，出去inform外
    private String urnCwmpVersion;                              // tr069协议版本
    
    // 请求控制流程
    private LinkedList<AbstractMethod> requestMethodList;
    // 解析应答确认，控制流程
    private Map<String, ACSConfirmMethod> confirmMethodMap;

    /**
     * @param _cpeId {@linkplain DeviceId#getCpeId(String, String, String)}
     * @param urnCwmpVersion 默认{@linkplain AbstractMethod#URN_CWMP1_1}
     * @see {@linkplain DeviceId#getCpeId(String, String, String)}
     * @see {@linkplain AbstractMethod#URN_CWMP1_0}
     * @see {@linkplain AbstractMethod#URN_CWMP1_1}
     */
    public ACSProcessControl(String _cpeId, String urnCwmpVersion)
    {
        if (_cpeId == null || urnCwmpVersion == null)
        {
            throw new NullPointerException();
        }
        this.cpeId = _cpeId;
        this.urnCwmpVersion = urnCwmpVersion;
        this.maxEnvelopes = 1; // 默认为1
        requestMethodList = new LinkedList<AbstractMethod>();

        // 线程安全
        confirmMethodMap = Collections.synchronizedMap(new HashMap<String, ACSConfirmMethod>());
        gRequestId = 2;

    }

    /**
     * @param _cpeId {@linkplain DeviceId#getCpeId(String, String, String)}
     * @param urnCwmpVersion 默认{@linkplain AbstractMethod#URN_CWMP1_1}
     * @see {@linkplain DeviceId#getCpeId(String, String, String)}
     * @see {@linkplain AbstractMethod#URN_CWMP1_0}
     * @see {@linkplain AbstractMethod#URN_CWMP1_1}
     */
    public ACSProcessControl(String _cpeId, String urnCwmpVersion, int maxEnvelopes)
    {
        this(_cpeId, urnCwmpVersion);
        this.maxEnvelopes = maxEnvelopes;
    }

    /**
     * CPE(Customer Premise Equipment 客户终端设备)设备唯一识别号<br/>
     * 由OUI+ProductClass+SerialNumber等组成,不对应网管的任何设备的ID.
     * @return
     */
    public String getCpeId()
    {
        return cpeId;
    }

    public int getMaxEnvelopes()
    {
        return maxEnvelopes;
    }

    public void setMaxEnvelopes(int maxEnvelopes)
    {
        if (maxEnvelopes <= 0)
        {
            this.maxEnvelopes = 1;
        }
        else
        {
            this.maxEnvelopes = maxEnvelopes;
        }
    }

    /**
     * 获取设备信息
     * @return
     */
    public Inform getDeviceInfor()
    {
        return deviceInfor;
    }

    public void setDeviceInfor(Inform deviceInfor)
    {
        this.deviceInfor = deviceInfor;
    }

    public ResponseParse getResponseParse()
    {
        return responseParse;
    }

    public void setResponseParse(ResponseParse responseParse)
    {
        this.responseParse = responseParse;
        responseParse.getResponseHandle().setAcsProcessControl(this);
    }

    /**
     * 添加ACS端的请求方法，缓存等待会话启动后进行发送
     * @param method 请求方法
     */
    public void addACSRequestMethod(AbstractMethod method)
    {
        addACSRequestMethod(method, null);
    }

    /**
     * 添加ACS端的请求方法，缓存等待会话启动后进行发送
     * @param method 请求方法
     * @param attachObj 附加信息，比如区分标识或者日志之类的
     */
    public void addACSRequestMethod(AbstractMethod method, Object attachObj)
    {
        if (method == null)
        {
            return;
        }
        synchronized (requestMethodList)
        {
            requestMethodList.add(method);
        }
        method.setRequestId(String.valueOf(gRequestId));		// 自动设置请求ID
        gRequestId++;											// 递增2，避免相同的requestId
        gRequestId++;
        confirmMethodMap.put(method.getRequestId(), new ACSConfirmMethod(method, attachObj));
    }

    /**
     * 添加ACS应答方法，一般只有InformResponse方法
     * @param method
     */
    public void addACSResponseMethod(AbstractMethod method)
    {
        if (method == null)
        {
            return;
        }
        synchronized (requestMethodList)
        {
            requestMethodList.addFirst(method);
        }
    }

    /**
     * 确认之前的请求方法是否有response，对应{@link #addACSRequestMethod(AbstractMethod)}}
     * @param method 一般是response方法
     * @return 确认成功与否
     */
    public boolean revAndConfirmMethod(AbstractMethod method)
    {
        if (method == null)
        {
            return false;
        }
        else if (confirmMethodMap.containsKey(method.getRequestId()))
        {
            ACSConfirmMethod confirmMethod = confirmMethodMap.get(method.getRequestId());
            confirmMethod.setResponseMethod(method);

            // 使用同步方式进行解析，解析过程中需防止阻塞。不使用异步，是因为解析过程中会流程交互，需要执行tr069方法
            if (responseParse != null)
            {
            	responseParse.parseCPEResponseMethod(confirmMethod);
            }

            // 分发后，删除
            confirmMethodMap.remove(method.getRequestId());
            return true;
        }
        else
        {
        	revCPEResquestMethod(method);
            return false;
        }
    }
    
    private void revCPEResquestMethod(AbstractMethod method)
    {
    	 // 使用同步方式进行解析，解析过程中需防止阻塞。不使用异步，是因为解析过程中会流程交互，需要执行tr069方法
        if (responseParse != null)
        {
        	responseParse.parseCPEResquestMethod(method);
        }
    }
    
    
    public ACSConfirmMethod removeConfirmMethod(String requestId)
    {
        if (requestId == null)
        {
            return null;
        }
        else
        {
            return confirmMethodMap.remove(requestId);
        }
    }

    /**
     * 执行ACS端的方法，按照顺序执行
     * @return 执行成功与否，为null是代表执行失败
     */
    public int doACSEMethods(OutputStream out)
    {
        synchronized (requestMethodList)
        {
            if (requestMethodList.isEmpty())
            {
                return 0;
            }
            else
            {
                try
                {
                    int responseLen = 0;
                    int methodSize = maxEnvelopes;	// 每次最大的执行数
                    if (maxEnvelopes > requestMethodList.size())
                    {
                        methodSize = requestMethodList.size();

                    }
                    for (int index = 0; index < methodSize; index++)
                    {
                        AbstractMethod requestMethod = requestMethodList.poll();	// 移除头部，如果不存在则返回空
                        if(requestMethod instanceof InformResponse)
                        {
                        	((InformResponse)requestMethod).setMaxEnvelopes(maxEnvelopes);
                        }
                        if (requestMethod != null)
                        {
                            requestMethod.setAcs2CpeEnv(true);						// 指明是从ACS-CPE的信包
                            if ((index + 1) == methodSize)
                            {
                                // 最后一个信包，设置为NoMoreReqs 为false
//                                requestMethod.setNoMoreReqs(true);

                                // 同时指示CPE不要发送请求
                                requestMethod.setHoldReqs(false);
                            }
                            else
                            {
                                // 非最后一个信包，设置为NoMoreReqs 为true
//                                requestMethod.setNoMoreReqs(false);

                                // 同时指示CPE可以发送请求
                                requestMethod.setHoldReqs(true);
                            }
                            requestMethod.setUrnCwmpVersion(urnCwmpVersion);
                            responseLen += requestMethod.writeTo(out);
                        }
                    }
                    return responseLen;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    LOGGER.error("Do ACSEMethods error!", e);
                }
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        else if (obj instanceof ACSProcessControl)
        {
            if (((ACSProcessControl) obj).getCpeId().equals(this.getCpeId()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        if (cpeId != null)
        {
            byte[] bytes = cpeId.getBytes();
            for (byte b : bytes)
            {
                result += b;
            }
        }
        return result * 37;
    }

    class Handler implements Runnable
    {
        private ACSConfirmMethod confirmMethod;

        public Handler()
        {
        }

        @Override
        public void run()
        {
            responseParse.parseCPEResponseMethod(confirmMethod);
        }

        /**
         * @param confirmMethod the confirmMethod to set
         */
        public void setConfirmMethod(ACSConfirmMethod confirmMethod)
        {
            this.confirmMethod = confirmMethod;
        }
    }
}
