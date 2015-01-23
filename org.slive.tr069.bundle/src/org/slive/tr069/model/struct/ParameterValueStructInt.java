package org.slive.tr069.model.struct;

/**
 * TODO Add class comment here<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author Slive
 * @history<br/>
 * ver date author desc
 * 1.0.0 2013-05-21 Slive created<br/>
 * <p/>
 */
public final class ParameterValueStructInt extends ParameterValueStruct<Integer>
{
    private static final long serialVersionUID = 4753580287517867419L;

    public ParameterValueStructInt(String name, Integer value)
    {
        super(name, value, Type_Int);
    }
}
