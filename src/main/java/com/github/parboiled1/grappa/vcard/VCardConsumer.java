package com.github.parboiled1.grappa.vcard;

import org.parboiled.support.Var;

import java.util.HashMap;
import java.util.Map;

public final class VCardConsumer
{
    private final Map<String, String> entries = new HashMap<String, String>();

    private String fieldName;

    public Var<String> field()
    {
        return new Var<String>()
        {
            @Override
            public boolean set(final String value)
            {
                fieldName = value;
                return true;
            }
        };
    }

    public Var<String> value()
    {
        return new Var<String>()
        {
            @Override
            public boolean set(final String value)
            {
                entries.put(fieldName, value);
                return true;
            }
        };
    }

    public Map<String, String> getEntries()
    {
        return entries;
    }
}
