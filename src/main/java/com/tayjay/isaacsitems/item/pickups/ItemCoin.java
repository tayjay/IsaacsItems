package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.item.ItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemCoin extends ItemPickup
{
    private int value;
    public ItemCoin(String name,int value)
    {
        super(name,"Money");
        this.value = value;
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        CapHelper.getPlayerDataCap(player).addCoins(value * stack.stackSize);
        return true;
    }
}
