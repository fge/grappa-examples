package com.github.parboiled1.grappa.quotedstring.event;

import com.google.common.eventbus.Subscribe;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class StringListener
{
    @SuppressWarnings("StringBufferField")
    private final StringBuilder sb = new StringBuilder();

    @Subscribe
    public void appendString(final String s)
    {
        sb.append(s);
    }

    @Subscribe
    public void appendChar(final Character c)
    {
        sb.append(c);
    }

    public void reset()
    {
        sb.setLength(0);
    }

    @Nullable
    public String getValue()
    {
        return sb.toString();
    }
}
