package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 * Trinkets that can be held by the player that add effects.
 */
public interface ITrinket
{
    boolean onPickupTrinket(ItemStack stack, EntityPlayer player);

    void doTrinketAction(ItemStack stack, EntityPlayer player);

    void tickTrinket(ItemStack stack, EntityPlayer player);

    boolean shouldTickTrinket(ItemStack stack, EntityPlayer player);
}
