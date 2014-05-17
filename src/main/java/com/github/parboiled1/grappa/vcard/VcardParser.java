package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.EventBusParser;
import com.google.common.collect.ImmutableList;
import ezvcard.VCardVersion;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

import java.util.List;

public class VcardParser
    extends EventBusParser<String>
{
    protected static final List<String> VCARD_VERSIONS;

    static {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (final VCardVersion version: VCardVersion.values())
            builder.add(version.getVersion());

        VCARD_VERSIONS = builder.build();
    }

    VcardParser()
    {
        addEvent("version", VcardVersionEvent.class);
    }

    private final VcardValueParser quotedPrintableValue
        = Parboiled.createParser(QuotedPrintableValueParser.class);
    private final VcardValueParser regularValue
        = Parboiled.createParser(RegularValueParser.class);


    Rule version()
    {
        return sequence("VERSION:", trie(VCARD_VERSIONS), fireEvent("version"));
    }

    Rule vcard()
    {
        return sequence("BEGIN:VCARD\r\n", version(), crlf(), "END:VCARD");
    }
}

