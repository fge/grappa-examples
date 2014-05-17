package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.EventBusParser;
import com.github.parboiled1.grappa.vcard.values.StringAccumulator;
import org.parboiled.Rule;

public abstract class VcardValueParser
    extends EventBusParser<String>
{
    protected final StringAccumulator accumulator = new StringAccumulator();

    public Rule value()
    {
        return sequence(
            accumulator.reset(),
            join(content()).using(fold()).min(1),
            end(),
            push(accumulator.build())
        );
    }

    protected abstract Rule content();

    protected abstract Rule fold();

    protected abstract Rule end();
}
