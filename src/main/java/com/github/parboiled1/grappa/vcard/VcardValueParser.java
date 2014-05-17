package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.EventBusParser;
import org.parboiled.Rule;

public abstract class VcardValueParser
    extends EventBusParser<String>
{
    protected final StringAccumulator accumulator = new StringAccumulator();

    public Rule value()
    {
        return sequence(
            accumulator.clear(),
            join(content()).using(fold()).min(1),
            end(),
            push(accumulator.get())
        );
    }

    protected abstract Rule content();

    protected abstract Rule fold();

    protected abstract Rule end();
}
