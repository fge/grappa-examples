package com.github.parboiled1.grappa.vcard;

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
    public void versionEvent(final VcardVersionEvent event)
    {
        vCard.setVersion(event.getVersion());
    }

    @Subscribe
    public void valueEvent(final VcardValueEvent event)
    {
        final String name = event.getPropertyName();
        if ("FN".equals(name))
            vCard.setFormattedName(event.getMatch());
    }
}
