package com.github.parboiled1.grappa.vcard;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public final class Test
{
    public static void main(final String... args)
    {
        String str =
            "BEGIN:VCARD\r\n" +
                "VERSION:4.0\r\n" +
                "N:Doe;Jonathan;;Mr;\r\n" +
                "FN:John Doe\r\n" +
                "END:VCARD\r\n";

        VCard vcard = Ezvcard.parse(str).first();
        String fullName = vcard.getFormattedName().getValue();
        String lastName = vcard.getStructuredName().getFamily();
        System.out.println(fullName);
        System.out.println(lastName);

    }
}
