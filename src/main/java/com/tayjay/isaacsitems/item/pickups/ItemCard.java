package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.item.ItemPickup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-21.
 */
public class ItemCard extends ItemPickup
{
    public ItemCard(String name)
    {
        super(name);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        return false;
    }
}
