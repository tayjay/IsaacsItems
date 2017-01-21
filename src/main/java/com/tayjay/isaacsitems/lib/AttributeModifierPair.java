package com.tayjay.isaacsitems.lib;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;

/**
 * Created by tayjay on 2017-01-16.
 */
public class AttributeModifierPair
{
    AttributeModifier modifier;
    IAttribute attribute;

    public AttributeModifierPair(AttributeModifier modifier, IAttribute attribute)
    {
        this.modifier = modifier;
        this.attribute = attribute;
    }
}
