package com.github.parboiled1.grappa.quotedstring.event;

import com.google.common.eventbus.Subscribe;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class StringListener
{
    private String value;

    @Subscribe
    public void setValue(final String value)
    {
        this.value = value;
    }

    public void reset()
    {
        value = null;
    }

    @Nullable
    public String getValue()
    {
        return value;
    }
}
