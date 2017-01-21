package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Created by tayjay on 2017-01-12.
 */
public class ItemPassiveColdFeet extends ItemPassive
{
    public ItemPassiveColdFeet(String name)
    {
        super(name);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {
        EnchantmentFrostWalker.freezeNearby(player, player.worldObj, new BlockPos(player), 0);
    }

    @Override
    public void doSpecialAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void doRandomAction(ItemStack stack, EntityPlayer player)
    {

    }
}
