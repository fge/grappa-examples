package com.github.parboiled1.grappa.quotedstring.stack;

import com.github.parboiled1.grappa.quotedstring.event.EventStringParser;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.support.ParsingResult;

import java.util.Map;

/**
 * Quoted string parser, stack version
 *
 * <p>When using the stack, you can retrieve the top value of the stack using
 * {@link ParsingResult#resultValue}; this of course requires that you push the
 * result value into the stack and retrieve it if and only if the result is
 * a success.</p>
 *
 * <p>This parser is very similar to the {@link EventStringParser}; there are,
 * in fact, only two differences:</p>
 *
 * <ul>
 *     <li></li>
 * </ul>
 */
public class StackStringParser
    extends BaseParser<String>
{
    protected static final Map<Character, Character> SPECIAL_ESCAPES;

    static {
        final ImmutableMap.Builder<Character, Character> builder
            = ImmutableMap.builder();

        builder.put('n', '\n');
        builder.put('f', '\f');
        builder.put('r', '\r');
        builder.put('t', '\t');

        SPECIAL_ESCAPES = builder.build();
    }

    /*
     * Instance variables accessed in a method returning a Rule cannot be
     * private!
     */
    protected final StringAccumulator accumulator = new StringAccumulator();

    Rule escaped()
    {
        return sequence(
            '\\',
            ANY,
            accumulator.appendChar(escapeChar(matchedChar()))
        );
    }

    Rule unescaped()
    {
        return sequence(
            zeroOrMore(noneOf("\\\"")),
            accumulator.append(match())
        );
    }

    Rule content()
    {
        return join(unescaped()).using(escaped()).min(0);
    }

    public Rule rule()
    {
        return sequence(
            '"',
            accumulator.reset(), content(), push(accumulator.build()),
            '"'
        );
    }

    protected final char escapeChar(final char c)
    {
        return Optional.fromNullable(SPECIAL_ESCAPES.get(c)).or(c);
    }
}
