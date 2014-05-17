package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.EventBusParser;
import com.github.parboiled1.grappa.vcard.values.VCardPropertyBuilder;
import com.github.parboiled1.grappa.vcard.values.VcardVersionBuilder;
import com.google.common.collect.ImmutableList;
import ezvcard.VCardVersion;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

import java.util.List;

public class VcardParser
    extends EventBusParser<String>
{
    /*
     * Can't be private: accessed in a Rule
     */
    protected static final List<String> VCARD_VERSIONS;

    static {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (final VCardVersion version: VCardVersion.values())
            builder.add(version.getVersion());

        VCARD_VERSIONS = builder.build();
    }

    protected final VcardVersionBuilder versionBuilder
        = new VcardVersionBuilder();
    protected final VCardPropertyBuilder propertyBuilder
        = new VCardPropertyBuilder();

    VcardParser()
    {
        addEvent("version", VcardVersionEvent.class);
        addEvent("value", VcardValueEvent.class);
    }

    protected final VcardValueParser quotedPrintableValue
        = Parboiled.createParser(QuotedPrintableValueParser.class);
    protected final VcardValueParser regularValue
        = Parboiled.createParser(RegularValueParser.class);
    protected final VcardValueParser valueParser = regularValue;


    Rule version()
    {
        return sequence(
            "VERSION:", trie(VCARD_VERSIONS),
            versionBuilder.setVersion(match()),
            propertyBuilder.setVersion(versionBuilder.build()),
            buildEvent(versionBuilder)
        );
    }

    Rule property()
    {
        return sequence(
            testNot(endVcard()), propertyBuilder.reset(),
            propertyName(), propertyBuilder.setName(match()),
            ':',
            valueParser.value(), propertyBuilder.setValue(pop()),
            buildEvent(propertyBuilder)
        );
    }

    // A regular name or an "X-" name
    Rule propertyName()
    {
        return sequence(optional(ignoreCase("x-")), oneOrMore(alpha()));
    }

    Rule vcard()
    {
        return sequence(
            beginVcard(),
            version(), crlf(),
            oneOrMore(property()),
            endVcard()
        );
    }

    Rule beginVcard()
    {
        return string("BEGIN:VCARD\r\n");
    }

    Rule endVcard()
    {
        return string("END:VCARD");
    }
}

