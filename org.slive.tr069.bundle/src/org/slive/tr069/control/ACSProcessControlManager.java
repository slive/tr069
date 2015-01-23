package org.slive.tr069.control;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Tr069控制流程管理类
 * @author Slive
 * @date 2013-5-14
 */
public class ACSProcessControlManager
{
    private static ACSProcessControlManager pcManager = null;
    private Map<String, ACSProcessControl> processControlMap;

    private ACSProcessControlManager()
    {
        processControlMap = Collections.synchronizedMap(new HashMap<String, ACSProcessControl>());
    }

    public synchronized static ACSProcessControlManager getInstance()
    {
        if (pcManager == null)
        {
            pcManager = new ACSProcessControlManager();
        }
        return pcManager;
    }

    public boolean addProcessControl(ACSProcessControl pcl)
    {
        if (pcl == null)
        {
            return false;
        }
        processControlMap.put(pcl.getCpeId(), pcl);
        return true;
    }

    public ACSProcessControl removeProcessControl(String _cpeId)
    {
        return processControlMap.remove(_cpeId);
    }

    public ACSProcessControl getProcessControl(String _cpeId)
    {
        return processControlMap.get(_cpeId);
    }

    public ACSProcessControl[] getProcessControls()
    {
        ACSProcessControl[] pcls = new ACSProcessControl[processControlMap.size()];
        processControlMap.values().toArray(pcls);
        return pcls;
    }
}
