package org.slive.tr069.model;

import java.io.Serializable;

public class SoftwareProp implements Serializable
{

    private static final long serialVersionUID = 1L;

    private java.lang.String hardware;

    private java.lang.String version;

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public SoftwareProp()
    {
    }

    public SoftwareProp(String h, String v)
    {
        hardware = h;
        version = v;
    }

    public boolean equals(java.lang.Object otherOb)
    {

        if (this == otherOb)
        {
            return true;
        }
        if (!(otherOb instanceof SoftwareProp))
        {
            return false;
        }
        SoftwareProp other = (SoftwareProp) otherOb;
        return (

        (hardware == null ? other.hardware == null
                : hardware.equals(other.hardware)) && (version == null ? other.version == null
                : version.equals(other.version)));
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return (

        (hardware == null ? 0 : hardware.hashCode()) ^ (version == null ? 0
                : version.hashCode())

        );
    }

}
