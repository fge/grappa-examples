package com.github.parboiled1.grappa.vcard;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.StringBuilderVar;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class VcardParser
    extends BaseParser<String>
{
    protected final VCardConsumer consumer;

    public VcardParser(@Nonnull final VCardConsumer consumer)
    {
        Preconditions.checkNotNull(consumer);
        this.consumer = consumer;
    }

    Rule header()
    {
        return Sequence("BEGIN:VCARD", nl());
    }

    Rule trailer()
    {
        return Sequence("END:VCARD", nl());
    }

    Rule fieldName()
    {
        return Sequence(OneOrMore(NoneOf("\r\n:")),
            consumer.field().set(match()));
    }

    Rule value()
    {
        return Sequence(OneOrMore(NoneOf("\r\n")),
            consumer.value().set(match()));
    }

    Rule line()
    {
        return Sequence(fieldName(), ':', value(), nl());
    }

    Rule nl()
    {
        return Sequence(Optional('\r'), '\n');
    }

    Rule vCard()
    {
        return Sequence(header(), OneOrMore(TestNot(trailer()), line()),
            trailer());
    }

    Rule caretEscape(final StringBuilderVar sb)
    {
        return Sequence('^', FirstOf(
            Sequence('\'', sb.append('"')),
            Sequence('n', sb.append('\n')),
            Sequence('^', sb.append('^'))
        ));
    }

    Rule nonCaret(final StringBuilderVar sb)
    {
        return Sequence(OneOrMore(NoneOf("^\r\n")), sb.append(match()));
    }

    Rule continuation()
    {
        return Sequence(nl(), OneOrMore(' '));
    }

    Rule join(@Nonnull final Rule normal, @Nonnull final Rule special)
    {
        Preconditions.checkNotNull(normal);
        Preconditions.checkNotNull(special);
        return Sequence(normal,
            ZeroOrMore(Sequence(special, normal))
        );
    }

    Rule quotedLine(final StringBuilderVar sb)
    {
        return Sequence(
            '"',
            join(join(nonCaret(sb), caretEscape(sb)), continuation()),
            '"'
        );
    }

    Rule vcardLine()
    {
        final StringBuilderVar sb = new StringBuilderVar();
        return FirstOf(quotedLine(sb), line());
    }

    public static void main(final String... args)
        throws IOException, URISyntaxException
    {
        final VCardConsumer consumer = new VCardConsumer();
        final VcardParser parser
            = Parboiled.createParser(VcardParser.class, consumer);

        final URL url = Resources.getResource("test.txt");
        final String s = Resources.toString(url, Charsets.UTF_8);
        final ParseRunner<String> runner
            = new BasicParseRunner<String>(parser.vCard());
        runner.run((CharSequence) s);
        System.out.println(consumer.getEntries());
    }
}
