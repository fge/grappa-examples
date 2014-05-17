package com.github.parboiled1.grappa.vcard;

import com.github.parboiled1.grappa.event.BasicMatchEvent;
import ezvcard.VCardVersion;
import org.parboiled.Context;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class VcardVersionEvent
    extends BasicMatchEvent<String>
{
    private final VCardVersion version;

    public VcardVersionEvent(final Context<String> context)
    {
        super(context);
        version = VCardVersion.valueOfByStr(getMatch());
    }

    @Nonnull
    public VCardVersion getVersion()
    {
        return version;
    }
}
