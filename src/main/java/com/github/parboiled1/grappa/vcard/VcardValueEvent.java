package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.BasicMatchEvent;
import org.parboiled.Context;
import org.parboiled.support.ValueStack;

public final class VcardValueEvent
    extends BasicMatchEvent<String>
{
    private final String propertyName;

    public VcardValueEvent(final Context<String> context)
    {
        super(context);
        final ValueStack<String> stack = context.getValueStack();
        stack.pop();
        propertyName = stack.pop();
    }

    public String getPropertyName()
    {
        return propertyName;
    }
}
