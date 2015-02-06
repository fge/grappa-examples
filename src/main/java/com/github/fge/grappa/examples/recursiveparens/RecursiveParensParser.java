package com.github.fge.grappa.examples.recursiveparens;

import com.github.parboiled1.grappa.parsers.EventBusParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;

public class RecursiveParensParser
    extends EventBusParser<Void>
{
    Rule exprContent()
    {
        return oneOrMore(noneOf("()"));
    }

    Rule expression()
    {
        return sequence(
            '(',
            exprContent(),
            zeroOrMore(expression()),
            optional(exprContent()),
            ')'
        );
    }

    public static void main(final String... args)
    {
        final RecursiveParensParser parser
            = Parboiled.createParser(RecursiveParensParser.class);
        final ParseRunner<Void> runner =
            new BasicParseRunner<>(parser.expression());
        System.out.println(runner.run("(a(b(c))d)").isSuccess());
    }
}
