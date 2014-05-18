package com.github.parboiled1.grappa.quotedstring.stack;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Scanner;

public final class StackExample
{
    public static void main(final String... args)
    {
        final StackStringParser parser
            = Parboiled.createParser(StackStringParser.class);

        final ParseRunner<String> runner
            = new BasicParseRunner<>(parser.rule());

        final Scanner scanner = new Scanner(System.in);

        String input;
        ParsingResult<String> result;

        while (true) {
            System.out.print("Enter a double quoted string (empty to exit): ");
            input = scanner.nextLine();
            if (input.isEmpty())
                break;
            result = runner.run(input);
            if (result.hasErrors()) {
                System.out.println("Invalid input!");
                continue;
            }
            System.out.printf("Matched:\n---\n%s\n---\n", result.resultValue);
        }
        System.out.println("Exiting");
        System.exit(0);
    }
}
