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
        final Rule special = quotedPrintable ? qpNextLine()
            : nonQpNextLine();
        final Rule normal
            = oneOrMore(testNot(special), ANY, sb.append(match()));

        sb.clear();
        return
            join(normal).using(special).min(1);
    }

    /*
     * Quoted printable
     */
    protected Rule qpNextLine()
    {
        return sequence("=0D=0A", crlf());
    }

    /*
     * Not quoted printable
     */
    protected Rule nonQpNextLine()
    {
        return sequence(crlf(), "  ");
    }
}
