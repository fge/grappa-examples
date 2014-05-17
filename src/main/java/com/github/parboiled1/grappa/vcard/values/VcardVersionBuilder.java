package com.github.parboiled1.grappa.vcard.values;

import com.github.parboiled1.grappa.helpers.ValueBuilder;
import ezvcard.VCardVersion;

import javax.annotation.Nonnull;
import javax.annotation.Untainted;

public final class VcardVersionBuilder
    implements ValueBuilder<VCardVersion>
{
    private VCardVersion version = VCardVersion.V2_1;

    public boolean setVersion(@Nonnull @Untainted final String versionAsString)
    {
        version = VCardVersion.valueOfByStr(versionAsString);
        return true;
    }

    public VcardVersionBuilder withVersion(
        @Untainted @Nonnull final String versionAsString)
    {
        version = VCardVersion.valueOfByStr(versionAsString);
        return this;
    }


    @Nonnull
    @Override
    public VCardVersion build()
    {
        return version;
    }

    /**
     * Reset this builder to a pristine state
     * <p>When this method has been called, all injected values are lost and
     * cannot be relied upon anymore.</p>
     * <p>Note that this interface makes no guarantee as to how {@link #build()}
     * behaves with an empty value builder.</p>
     *
     * @return always {@code true}
     */
    @Override
    public boolean reset()
    {
        version = null;
        return true;
    }
}
