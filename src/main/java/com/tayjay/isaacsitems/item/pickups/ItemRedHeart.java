package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.item.ItemHeart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ItemRedHeart extends ItemHeart
{
    private boolean fullHeart;

    public ItemRedHeart(String name, boolean fullHeart)
    {
        super(name);
        this.fullHeart = fullHeart;
    }

    @Override
    public boolean onPickupHeart(ItemStack heartStack, EntityPlayer entityPlayer)
    {
        if (isFullHeart(heartStack))
        {
            entityPlayer.heal(2 * heartStack.stackSize);
        }
        else
            entityPlayer.heal(1 * heartStack.stackSize);
        return true;
    }

    @Override
    public boolean isSoulHeart(ItemStack heartStack)
    {
        return false;
    }

    @Override
    public boolean isFullHeart(ItemStack heartStack)
    {
        return fullHeart;
    }
}
