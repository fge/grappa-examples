package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.EventBusParser;
import org.parboiled.Rule;
import org.parboiled.support.StringBuilderVar;

public class VcardValueParser
    extends EventBusParser<String>
{
    private final StringBuilderVar sb = new StringBuilderVar();


    public Rule value(final boolean quotedPrintable)
    {
        final Rule special = quotedPrintable ? quotedPrintableLineSeparator()
            : nonQuotedPrintableLineSeparator();
        final Rule normal
            = oneOrMore(testNot(special), ANY, sb.append(match()));

        sb.clear();
        return
            join(normal).using(special).min(1);
    }

    /*
     * Quoted printable
     */
    protected Rule quotedPrintableLineSeparator()
    {
        return sequence("=0D=0A", crlf());
    }

    protected Rule quotedPrintableEOI()
    {
        return string("=\r\n");
    }

    protected Rule quotedPrintableContent()
    {
        return sequence(oneOrMore(noneOf("=")), sb.append(match()));
    }

    protected Rule quotedPrintableFull()
    {
        return sequence(
            join(quotedPrintableContent())
                .using(quotedPrintableLineSeparator())
                .min(1),
            quotedPrintableEOI()
        );
    }

    /*
     * Not quoted printable
     */
    protected Rule nonQuotedPrintableLineSeparator()
    {
        return sequence(crlf(), oneOrMore(wsp()));
    }

    protected Rule nonQuotedPrintableContent()
    {
        return sequence(oneOrMore(noneOf("\r"), ANY), sb.append(match()));
    }

    protected Rule nonQuotedPrintableFull()
    {
        return join(nonQuotedPrintableContent())
            .using(nonQuotedPrintableLineSeparator())
            .min(1);
    }

    public String getValue()
    {
        return sb.get().toString();
    }
}
