package com.github.fge.grappa.examples.vcard.rfc2425;

import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class RFC2425Section582Parser
    extends RFC2425ParserBase
{
    protected final RFC2425Section584Parser valueSpecParser
        = Parboiled.createParser(RFC2425Section584Parser.class);

    /*
     * Section 5.8.2
     */

    Rule qsafeChar()
    {
        return firstOf(wsp(), ch((char) 0x21), charRange((char) 0x23,
            (char) 0x7e), nonAscii());
    }

    Rule safeChar()
    {
        return firstOf(
            wsp(),
            ch((char) 0x21),
            charRange((char) 0x23, (char) 0x2b),
            charRange((char) 0x2d, (char) 0x39),
            charRange((char) 0x3c, (char) 0x7e),
            nonAscii()
        );
    }

    Rule quotedString()
    {
        return sequence('"', zeroOrMore(qsafeChar()), '"');
    }

    // See also section 5.8.4 for "valueSpec()"
    Rule value()
    {
        return firstOf(valueSpecParser.valueSpec(), zeroOrMore(valueChar()));
    }

    Rule ptext()
    {
        return zeroOrMore(safeChar());
    }

    Rule paramValue()
    {
        return firstOf(ptext(), quotedString());
    }

    Rule ianaToken()
    {
        return  oneOrMore(firstOf(alpha(), digit(), '-'));
    }

    Rule xName()
    {
        return sequence(ignoreCase("x-"), ianaToken());
    }

    Rule paramName()
    {
        return firstOf(xName(), ianaToken());
    }

    Rule param()
    {
        return sequence(
            paramName(),
            '=',
            join(paramValue()).using(',').min(1)
        );
    }

    Rule name()
    {
        return firstOf(xName(), ianaToken());
    }

    Rule group()
    {
        return oneOrMore(firstOf(alpha(), digit(), '-'));
    }

    Rule contentLine()
    {
        return sequence(
            optional(sequence(group(), '.')),
            name(),
            zeroOrMore(';', param()),
            ':',
            value(),
            crlf()
        );
    }

    /*
     * Section 5.8.4: valueSpec()
     */

}
