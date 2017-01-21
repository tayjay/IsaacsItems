package com.tayjay.isaacsitems.api.item;

import com.tayjay.isaacsitems.lib.AttributeModifierPair;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-30.
 */
public interface IStatModifier
{
    ArrayList<AttributeModifierPair> getStatModifiers(ItemStack stack);

    //void addStatModifier(AttributeModifier modifier, IAttribute attribute);
}
