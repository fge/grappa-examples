package com.github.parboiled1.grappa.quotedstring.event;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Scanner;

public final class EventExample
{
    public static void main(final String... args)
    {
        final EventStringParser parser
            = Parboiled.createParser(EventStringParser.class);

        final StringListener listener = new StringListener();
        parser.register(listener);

        final ParseRunner<Object> runner
            = new ReportingParseRunner<>(parser.rule());

        final Scanner scanner = new Scanner(System.in);

        String input;
        ParsingResult<Object> result;

        while (true) {
            System.out.print("Enter a double quoted string (empty to exit): ");
            input = scanner.nextLine();
            if (input.isEmpty())
                break;
            listener.reset();
            result = runner.run(input);
            if (result.hasErrors()) {
                System.out.println("Invalid input!");
                continue;
            }
            System.out.printf("Matched:\n---\n%s\n---\n", listener.getValue());
        }
        System.out.println("Exiting");
        System.exit(0);
    }
}
