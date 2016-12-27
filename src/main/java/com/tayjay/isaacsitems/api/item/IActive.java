package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 * This item is triggered by the player and must be recharged to use again.
 */
public interface IActive
{

    void onPickupActive(ItemStack stack, EntityPlayer player);
    /**
     * Preform effect when item is activated
     * @param stack
     * @param player
     */
    void onActivate(ItemStack stack, EntityPlayer player);

    /**
     * How many charges does it take to activate the item.
     * This could be different with modifiers from other items.
     * @param stack
     * @return
     */
    int getMaxCharge(ItemStack stack);

    /**
     * Get how much charge this item currently has.
     * @param stack
     * @return
     */
    int getCurrentCharge(ItemStack stack);

    /**
     * Add a charge to this item.
     * @param stack
     */
    void addCharge(ItemStack stack);
}
