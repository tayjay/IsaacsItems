package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.w3c.dom.Attr;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-26.
 */
public interface IPassive
{
    /**
     * Runs when the player picks up this item.
     * Could change player stats, add health, etc.
     * @param stack
     * @param player
     */
    boolean onPickupPassive(ItemStack stack, EntityPlayer player);

    void doTickAction(ItemStack stack, EntityPlayer player);

    /**
     * Code to be ran when a certain case for this item happens.
     * Ex. Killing enemies gives flies.
     * @param stack
     * @param player
     */
    void doSpecialAction(ItemStack stack, EntityPlayer player);

    /**
     * Do this when RNG dictates.
     * Ex. Random effect added when shooting.
     * @param stack
     * @param player
     */
    void doRandomAction(ItemStack stack, EntityPlayer player);



}
