package org.slive.tr069.model;

public class HostsProp implements java.io.Serializable
{
	private static final long serialVersionUID = -3912661904826155206L;

	public String oui;
    public String serialno;

    public HostsProp()
    {
    }

    public HostsProp(String o, String s)
    {
        oui = o;
        serialno = s;
    }

    public boolean equals(java.lang.Object otherOb)
    {

        if (this == otherOb)
        {
            return true;
        }
        if (!(otherOb instanceof HostsProp))
        {
            return false;
        }
        HostsProp other = (HostsProp) otherOb;
        return (
        (oui == null ? other.oui == null : oui.equals(other.oui)) && (serialno == null ? other.serialno == null
                : serialno.equals(other.serialno))

        );
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return (
        (oui == null ? 0 : oui.hashCode()) ^ (serialno == null ? 0
                : serialno.hashCode())

        );
    }

}
