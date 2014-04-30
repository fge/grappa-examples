package com.github.parboiled1.grappa.vcard;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

public abstract class RFC5234AppendixBParser<V>
    extends BaseParser<V>
{
    Rule alpha()
    {
        return FirstOf(CharRange('A', 'Z'), CharRange('a', 'z'));
    }
}
