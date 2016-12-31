package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 * Items such as coins, keys, and bombs that are picked up and just add to the stat
 */
public interface IPickup
{
    boolean onPickup(ItemStack stack, EntityPlayer player);

    String getDescription();
}
