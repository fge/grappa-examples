package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.BasicMatchEvent;
import org.parboiled.Context;

public final class VcardValueEvent
    extends BasicMatchEvent<String>
{
    private final String propertyName;

    public VcardValueEvent(final Context<String> context)
    {
        super(context);
        propertyName = context.getValueStack().pop();
    }

    public String getPropertyName()
    {
        return propertyName;
    }
}
