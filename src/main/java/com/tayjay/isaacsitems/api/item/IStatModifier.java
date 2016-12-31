package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-30.
 */
public interface IStatModifier
{
    ArrayList<AttributeModifier> getStatModifiers(ItemStack stack);

    //void addStatModifier(AttributeModifier modifier, IAttribute attribute);
}
