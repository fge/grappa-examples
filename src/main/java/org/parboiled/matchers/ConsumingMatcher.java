package org.parboiled.matchers;

import com.github.parboiled1.grappa.MatchConsumer;
import com.google.common.base.Preconditions;
import org.parboiled.MatcherContext;
import org.parboiled.Rule;
import org.parboiled.matchervisitors.MatcherVisitor;

import javax.annotation.Nonnull;
import java.util.List;

public class ConsumingMatcher
    extends SequenceMatcher
{
    private final MatchConsumer consumer;

    public static ConsumingMatcherBuilder consume(@Nonnull final Rule... rules)
    {
        return new ConsumingMatcherBuilder(rules);
    }

    public ConsumingMatcher(@Nonnull final MatchConsumer consumer,
        @Nonnull final Rule... subRules)
    {
        super(Preconditions.checkNotNull(subRules, "subRules"));
        label("Consumer");
        this.consumer = consumer;
    }

    @Override
    public boolean match(final MatcherContext context) {
        final Object snapshot = context.getValueStack().takeSnapshot();

        final List<Matcher> children = getChildren();
        final int size = children.size();

        Matcher matcher;
        for (int i = 0; i < size; i++) {
            matcher = children.get(i);

            // remember the current index in the context, so we can access it for building the current follower set
            context.setIntTag(i);

            if (!matcher.getSubContext(context).runMatcher()) {
                // rule failed, so invalidate all stack actions the rule might have done
                context.getValueStack().restoreSnapshot(snapshot);
                return false;
            }
        }
        context.createNode();
        consumer.consume(context.getMatch());
        return true;
    }

    @Override
    public <R> R accept(@Nonnull final MatcherVisitor<R> visitor) {
        Preconditions.checkNotNull(visitor, "visitor");
        return visitor.visit(this);
    }
}
