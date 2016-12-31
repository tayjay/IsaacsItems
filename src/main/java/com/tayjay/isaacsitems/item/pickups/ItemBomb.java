package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.item.ItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemBomb extends ItemPickup
{
    public ItemBomb(String name)
    {
        super(name,"It goes boom.");
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        CapHelper.getPlayerDataCap(player).addBombs(stack.stackSize);
        return true;
    }
}
