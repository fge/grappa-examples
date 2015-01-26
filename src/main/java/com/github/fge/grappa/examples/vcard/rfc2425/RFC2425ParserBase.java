package com.github.fge.grappa.examples.vcard.rfc2425;

import com.github.parboiled1.grappa.parsers.EventBusParser;
import org.parboiled.Rule;

public class RFC2425ParserBase
    extends EventBusParser<String>
{
    Rule nonAscii()
    {
        return charRange((char) 0x80, (char) 0xff);
    }

    Rule valueChar()
    {
        return firstOf(wsp(), vchar(), nonAscii());
    }

}
