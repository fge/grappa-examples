package com.github.parboiled1.grappa.vcard;

import org.parboiled.Rule;

public class RegularValueParser
    extends VcardValueParser
{
    @Override
    protected Rule content()
    {
        return sequence(
            oneOrMore(testNot("\r\n"), ANY),
            accumulator.append(match())
        );
    }

    @Override
    protected Rule fold()
    {
        return sequence(crlf(), oneOrMore(wsp()));
    }

    @Override
    protected Rule end()
    {
        return crlf();
    }
}
