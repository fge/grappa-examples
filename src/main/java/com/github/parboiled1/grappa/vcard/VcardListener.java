package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.BasicMatchEvent;
import com.google.common.eventbus.Subscribe;
import ezvcard.VCard;

public final class VcardListener
{
    private final VCard vCard;

    public VcardListener()
    {
        vCard = new VCard();
    }

    @Subscribe
    public void foo(final BasicMatchEvent<String> event)
    {
    }
}
