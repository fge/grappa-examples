package org.parboiled.matchers;

import com.github.parboiled1.grappa.MatchConsumer;
import com.google.common.base.Preconditions;
import org.parboiled.Rule;

import javax.annotation.Nonnull;

public final class ConsumingMatcherBuilder
{
    private final Rule[] rules;
    private String label;

    ConsumingMatcherBuilder(@Nonnull final Rule... rules)
    {
        this.rules = Preconditions.checkNotNull(rules);
    }

    public ConsumingMatcherBuilder withName(@Nonnull final String label)
    {
        this.label = Preconditions.checkNotNull(label);
        return this;
    }

    public Rule into(@Nonnull final MatchConsumer consumer)
    {
        Preconditions.checkNotNull(consumer);
        final ConsumingMatcher matcher = new ConsumingMatcher(consumer,
            rules);
        if (label != null)
            matcher.label(label);
        return matcher;
    }
}

