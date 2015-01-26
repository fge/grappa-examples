package com.github.fge.grappa.examples.vcard.rfc2425;

import com.github.parboiled1.grappa.parsers.EventBusParser;
import org.parboiled.Rule;

public class RFC1738Section5Parser
    extends EventBusParser<String>
{
    Rule genericUrl()
    {
        return sequence(scheme(), ':', schemePart());
    }

    Rule scheme()
    {
        return oneOrMore(firstOf(alpha(), digit(), anyOf("+-.")));
    }

    Rule schemePart()
    {
        return firstOf(ipSchemePart(), zeroOrMore(xchar()));
    }

    Rule ipSchemePart()
    {
        return sequence("//", login(), optional('/', urlPath()));
    }

    Rule login()
    {
        return sequence(optional(user(), optional(':', password()), '@'),
            hostPort());
    }

    Rule hostPort()
    {
        return sequence(host(), optional(':', port()));
    }

    Rule host()
    {
        return firstOf(hostname(), hostNumber());
    }

    Rule hostname()
    {
        return sequence(zeroOrMore(domainLabel(), '.'), topLabel());
    }

    Rule domainLabel()
    {
        return join(oneOrMore(alphaDigit())).using('.').min(1);
    }

    Rule topLabel()
    {
        return sequence(alpha(), domainLabel());
    }

    Rule alphaDigit()
    {
        return firstOf(alpha(), digit());
    }

    Rule hostNumber()
    {
        return join(digits()).using('.').times(3);
    }

    Rule port()
    {
        return digits();
    }

    Rule user()
    {
        return zeroOrMore(firstOf(uchar(), anyOf(";?&=")));
    }

    Rule password()
    {
        return user();
    }

    Rule urlPath()
    {
        return zeroOrMore(xchar());
    }

    Rule digits()
    {
        return oneOrMore(digit());
    }

    Rule uchar()
    {
        return firstOf(unreserved(), escape());
    }

    Rule unreserved()
    {
        return firstOf(alpha(), digit(), safe(), extra());
    }

    Rule safe()
    {
        return anyOf("$-_.+");
    }

    Rule extra()
    {
        return anyOf("!*'(),");
    }

    Rule escape()
    {
        return sequence('%', hexDigit(), hexDigit());
    }

    Rule xchar()
    {
        return firstOf(unreserved(), reserved(), escape());
    }

    Rule reserved()
    {
        return anyOf(";/?:@&=");
    }
}
