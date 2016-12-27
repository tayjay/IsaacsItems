package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IPickup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemPickup extends ItemBase implements IPickup
{
    public ItemPickup(String name)
    {
        super(name);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        return false;
    }
}
