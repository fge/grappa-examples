package com.github.parboiled1.grappa.vcard;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

/**
 * Line in a vCard as defined in RFC 6350, section 3.2
 */
public class VCardLineParser
    extends BaseParser<Object>
{
    public Rule lineFold()
    {
        return sequence(crlf(), wsp());
    }
}
