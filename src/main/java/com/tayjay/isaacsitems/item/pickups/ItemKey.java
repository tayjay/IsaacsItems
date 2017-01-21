package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.item.ItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemKey extends ItemPickup
{
    public ItemKey(String name)
    {
        super(name);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        CapHelper.getPlayerDataCap(player).addKeys(stack.stackSize);
        return true;
    }
}
