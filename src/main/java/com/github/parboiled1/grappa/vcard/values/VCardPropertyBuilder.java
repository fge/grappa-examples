package com.github.parboiled1.grappa.vcard.values;

import com.github.parboiled1.grappa.helpers.ValueBuilder;
import ezvcard.VCardDataType;
import ezvcard.VCardVersion;
import ezvcard.io.scribe.RawPropertyScribe;
import ezvcard.io.scribe.ScribeIndex;
import ezvcard.io.scribe.VCardPropertyScribe;
import ezvcard.parameter.VCardParameters;
import ezvcard.property.VCardProperty;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class VCardPropertyBuilder
    implements ValueBuilder<VCardProperty>
{
    /*
     * The vCard version
     */
    private VCardVersion version = VCardVersion.V2_1;

    /*
     * Index of vCard property scribes
     */
    private final ScribeIndex index = new ScribeIndex();

    /*
     * The property scribe
     */
    private VCardPropertyScribe<? extends VCardProperty> scribe;

    /*
     * The generated property
     */
    private VCardProperty property;


    // Unused at the moment
    private VCardParameters parameters = new VCardParameters();

    // vCard property value type
    private VCardDataType dataType;

    // Called before .setName()
    public boolean setVersion(final VCardVersion version)
    {
        this.version = version;
        return true;
    }

    // A name or an extended name
    public boolean setName(final String name)
    {
        scribe = index.getPropertyScribe(name);
        if (scribe == null)
            scribe = new RawPropertyScribe(name);

        dataType = scribe.defaultDataType(version);
        return true;
    }

    // Called after .setName()
    public boolean setValue(final String value)
    {
        /*
         * Use the below when parameters are accounted for
         */
        /*
        String valueParam = parameters.getValue();
        VCardDataType dataType;
        if (valueParam == null) {
            dataType = scribe.defaultDataType(version);
        } else {
            dataType = VCardDataType.get(valueParam);
            parameters.removeValue();
        }
        */
        property = scribe.parseText(value, dataType, version, parameters)
            .getProperty();
        return true;
    }

    // Name is always set here
    public VCardPropertyBuilder withValue(final String value)
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
        /*
         * Do NOT reset version here
         */
        scribe = null;
        parameters = new VCardParameters();
        dataType = null;
        property = null;

        return true;
    }
}
