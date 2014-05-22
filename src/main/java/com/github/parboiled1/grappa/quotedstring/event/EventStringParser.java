package com.github.parboiled1.grappa.quotedstring.event;

import com.github.parboiled1.grappa.parsers.EventBusParser;
import com.github.parboiled1.grappa.quotedstring.stack.StringAccumulator;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.parboiled.Rule;

import java.util.Map;

public class EventStringParser
    extends EventBusParser<Object>
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

    protected final StringAccumulator accumulator = new StringAccumulator();

    Rule escaped()
    {
        return sequence('\\', ANY,
            accumulator.appendChar(escapeChar(matchedChar())));
    }

    Rule unescaped()
    {
        return sequence(zeroOrMore(noneOf("\\\"")),
            accumulator.append(match()));
    }

    Rule content()
    {
        return join(unescaped()).using(escaped()).min(0);
    }

    public Rule rule()
    {
        return sequence('"', accumulator.reset(), content(),
            post(accumulator), '"');
    }

    protected final char escapeChar(final char c)
    {
        return Optional.fromNullable(SPECIAL_ESCAPES.get(c)).or(c);
    }
}
