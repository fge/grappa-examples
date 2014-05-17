package com.github.parboiled1.grappa.vcard.values;

import com.github.parboiled1.grappa.helpers.ValueBuilder;
import com.google.common.base.Preconditions;
import ezvcard.property.VCardProperty;

import javax.annotation.Nonnull;
import javax.annotation.Untainted;

public final class VCardPropertyBuilder
    implements ValueBuilder<VCardProperty>
{
    private VCardProperty property;
    private String propertyName;

    public boolean setPropertyName(
        @Untainted @Nonnull final String propertyName)
    {
        this.propertyName = Preconditions.checkNotNull(propertyName);
        return true;
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
        return null;
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
        return false;
    }
}
