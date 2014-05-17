package com.github.parboiled1.grappa.vcard;

import org.parboiled.support.Var;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class StringAccumulator
    extends Var<String>
{
    @SuppressWarnings("StringBufferField")
    private final StringBuilder sb = new StringBuilder();

    public boolean append(final char c)
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
    public boolean clear()
    {
        sb.delete(0, sb.length());
        return true;
    }

    @Override
    public String get()
    {
        return sb.toString();
    }
}
