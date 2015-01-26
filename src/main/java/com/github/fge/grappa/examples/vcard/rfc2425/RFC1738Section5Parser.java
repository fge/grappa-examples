package com.github.fge.grappa.examples.vcard.rfc2425;

import com.github.parboiled1.grappa.parsers.EventBusParser;
import org.parboiled.Rule;

public class RFC1738Section5Parser
    extends EventBusParser<String>
{
    Rule genericUrl()
    {
        return NOTHING;
    }
}
