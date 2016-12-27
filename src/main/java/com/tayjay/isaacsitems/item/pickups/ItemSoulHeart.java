package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.item.ItemHeart;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ItemSoulHeart extends ItemHeart
{
    public ItemSoulHeart(String name)
    {
        super(name);
    }

    @Override
    public boolean onPickupHeart(ItemStack heartStack, EntityPlayer entityPlayer)
    {
        IPlayerDataProvider data = CapHelper.getPlayerDataCap(entityPlayer);
        if(data.getSoulHearts()<20)
            data.changeSoulHearts(2*heartStack.stackSize);
        else
            data.changeSoulHearts(-1*data.getSoulHearts());
        return true;
    }

    @Override
    public boolean isSoulHeart(ItemStack heartStack)
    {
        return true;
    }

    @Override
    public boolean isFullHeart(ItemStack heartStack)
    {
        return true;
    }
}
