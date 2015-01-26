package com.github.fge.grappa.examples.vcard.rfc2425;

import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class RFC2425Section584Parser
    extends RFC2425ParserBase
{
    protected final RFC1738Section5Parser urlParser
        = Parboiled.createParser(RFC1738Section5Parser.class);

    boolean integerBetween(final int min, final int max)
    {
        final int val = Integer.parseInt(match());
        return val >= min && val <= max;
    }

    Rule timeSecond()
    {
        // 60 for leap second...
        return sequence(digit(), digit(), integerBetween(0, 60));
    }

    Rule timeMinute()
    {
        return sequence(digit(), digit(), integerBetween(0, 59));
    }

    Rule timeHour()
    {
        return sequence(digit(), digit(), integerBetween(0, 23));
    }

    Rule timeSecFrac()
    {
        return sequence(',', oneOrMore(digit()));
    }

    Rule sign()
    {
        return anyOf("+-");
    }

    Rule timeNumZone()
    {
        return sequence(sign(), timeHour(), ':', timeMinute());
    }

    Rule timeZone()
    {
        return firstOf('Z', timeNumZone());
    }

    Rule time()
    {
        return sequence(
            timeHour(), ':', timeMinute(), ':', timeSecond(),
            optional(timeSecFrac()),
            optional(timeZone())
        );
    }

    Rule dateMday()
    {
        return sequence(digit(), digit(), integerBetween(1, 31));
    }

    Rule dateMonth()
    {
        return sequence(digit(), digit(), integerBetween(1, 12));
    }

    Rule dateFullYear()
    {
        return sequence(digit(), digit(), digit(), digit());
    }

    Rule date()
    {
        return sequence(dateFullYear(), '-', dateMonth(), '-', dateMday());
    }

    Rule floatNum()
    {
        return sequence(optional(sign()), oneOrMore(digit()), optional('.',
            oneOrMore(digit())));
    }

    Rule floatList()
    {
        return join(floatNum()).using(',').min(1);
    }

    Rule integer()
    {
        return sequence(optional(sign()), oneOrMore(digit()));
    }

    Rule integerList()
    {
        return join(integer()).using(',').min(1);
    }

    Rule booleanValue()
    {
        return trie("TRUE", "FALSE");
    }

    Rule dateTime()
    {
        return sequence(date(), 'T', time());
    }

    Rule dateTimeList()
    {
        return join(dateTime()).using(',').min(1);
    }

    Rule timeList()
    {
        return join(time()).using(',').min(1);
    }

    Rule dateList()
    {
        return join(date()).using(',').min(1);
    }

    Rule textListChar()
    {
        return firstOf(sequence(testNot(anyOf("\\\n,")), valueChar()), sequence(
            '\\', anyOf("\\,nN")));
    }

    Rule textList()
    {
        return join(zeroOrMore(textListChar())).using(',').min(0);
    }

    Rule valueSpec()
    {
        return firstOf(
            dateList(),
            timeList(),
            dateTimeList(),
            booleanValue(),
            integerList(),
            floatList(),
            urlParser.genericUrl(),
            textList()
        );
    }
}
