package com.github.parboiled1.grappa.vcard;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class Main
{
    public static void main(final String... args)
        throws IOException
    {
        final VcardListener listener = new VcardListener();
        final VcardParser parser = Parboiled.createParser(VcardParser.class);
        final ParseRunner<Object> runner
            = new TracingParseRunner<>(parser.vcard());

        parser.register(listener);

        try (
            final InputStream in
                = Main.class.getResourceAsStream("/testVcard.txt");
            final InputStreamReader reader
                = new InputStreamReader(in, StandardCharsets.UTF_8);
        ) {
            final char[] buf = new char[1024];
            final StringBuilder sb = new StringBuilder();
            int count;
            while ((count = reader.read(buf)) != -1)
                sb.append(buf, 0, count);

            final String input = sb.toString();
            System.out.println(input);
            System.out.println(runner.run(input).hasErrors());
        }
    }
}
