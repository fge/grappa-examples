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

    VcardParser()
    {
        addEvent("version", VcardVersionEvent.class);
        addEvent("value", VcardValueEvent.class);
    }

    protected final VcardValueParser quotedPrintableValue
        = Parboiled.createParser(QuotedPrintableValueParser.class);
    protected final VcardValueParser regularValue
        = Parboiled.createParser(RegularValueParser.class);


    Rule version()
    {
        return sequence("VERSION:", trie(VCARD_VERSIONS), fireEvent("version"));
    }

    Rule property()
    {
        return sequence(
            testNot(endVcard()),
            oneOrMore(charRange('A', 'Z')), push(match()),
            ':',
            regularValue.value(), fireEvent("value")
        );
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

    Rule geoNumber()
    {
        // TODO: create a repeat(...).{times,min,max}()
        return sequence(oneOrMore(digit()), '.', nTimes(6, digit()));
    }
}

