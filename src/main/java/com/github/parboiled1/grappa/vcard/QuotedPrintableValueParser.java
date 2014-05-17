package com.github.parboiled1.grappa.vcard;

import org.parboiled.Rule;

public class QuotedPrintableValueParser
    extends VcardValueParser
{
    @Override
    protected Rule content()
    {
        return sequence(
            oneOrMore(noneOf("=\r\n")),
            accumulator.append(match())
        );
    }

    @Override
    protected Rule fold()
    {
        return string("=0D0A\r\n");
    }

    @Override
    protected Rule end()
    {
        return string("=\r\n");
    }
}
