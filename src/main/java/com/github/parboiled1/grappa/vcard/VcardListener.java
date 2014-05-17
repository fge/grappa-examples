package com.github.parboiled1.grappa.vcard;

import com.google.common.eventbus.Subscribe;
import ezvcard.VCard;
import ezvcard.VCardVersion;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class VcardListener
{
    private final VCard vCard;

    public VcardListener()
    {
        vCard = new VCard();
    }

    @Subscribe
    public void setVersion(final VCardVersion version)
    {
        vCard.setVersion(version);
    }
}
