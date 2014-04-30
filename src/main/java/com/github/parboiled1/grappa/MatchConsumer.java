package com.github.parboiled1.grappa;

import com.google.common.annotations.Beta;

@Beta
public interface MatchConsumer
{
    void consume(final String match);
}
