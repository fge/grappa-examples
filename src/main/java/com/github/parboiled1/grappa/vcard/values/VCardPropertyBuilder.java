package com.github.parboiled1.grappa.vcard.values;

import com.github.parboiled1.grappa.helpers.ValueBuilder;
import ezvcard.VCardDataType;
import ezvcard.VCardVersion;
import ezvcard.io.scribe.ScribeIndex;
import ezvcard.io.scribe.VCardPropertyScribe;
import ezvcard.parameter.VCardParameters;
import ezvcard.property.VCardProperty;

import javax.annotation.Nonnull;
import javax.annotation.Untainted;

public final class VCardPropertyBuilder
    implements ValueBuilder<VCardProperty>
{
    private final ScribeIndex index = new ScribeIndex();

    private VCardProperty property;
    private VCardPropertyScribe<? extends VCardProperty> scribe;
    private VCardDataType dataType;
    private VCardVersion version = VCardVersion.V2_1;

    // Called before .setName()
    public boolean setVersion(@Untainted @Nonnull final String version)
    {
        this.version = VCardVersion.valueOfByStr(version);
        return true;
    }

    public boolean setName(@Untainted @Nonnull final String name)
    {
        scribe = index.getPropertyScribe(name.toUpperCase());
        if (scribe == null)
            return false;
        dataType = scribe.defaultDataType(version);
        return true;
    }

    // Called after .setName()
    public boolean setValue(@Untainted @Nonnull final String value)
    {
        property = scribe.parseText(value, dataType, version,
            new VCardParameters()).getProperty();
        return true;
    }

    // Name is always set here
    public VCardPropertyBuilder withValue(
        @Untainted @Nonnull final String value)
    {
        setValue(value);
        return this;
    }

    /**
     * Build the value
     *
     * @return the built value
     */
    @Nonnull
    @Override
    public VCardProperty build()
    {
        return property;
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
        property = null;
        scribe = null;
        dataType = null;
        return true;
    }
}
