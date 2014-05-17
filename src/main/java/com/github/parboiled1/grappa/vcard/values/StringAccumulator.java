package com.github.parboiled1.grappa.vcard.values;

import com.github.parboiled1.grappa.helpers.ValueBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class StringAccumulator
    implements ValueBuilder<String>
{
    @SuppressWarnings("StringBufferField")
    private StringBuilder sb = new StringBuilder();

    public boolean appendChar(final char c)
    {
        sb.append(c);
        return true;
    }

    public boolean append(final String s)
    {
        sb.append(s);
        return true;
    }

    @Override
    public boolean reset()
    {
        sb = new StringBuilder();
        return true;
    }

    @Override
    public String build()
    {
        return sb.toString();
    }
}
