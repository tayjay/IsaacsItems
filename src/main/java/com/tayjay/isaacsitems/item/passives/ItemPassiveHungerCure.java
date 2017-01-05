package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-05.
 */
public class ItemPassiveHungerCure extends ItemPassive
{
    public ItemPassiveHungerCure(String name, String description)
    {
        super(name, description);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {
        if (player.getActivePotionEffect(MobEffects.HUNGER) != null)
        {
            player.removePotionEffect(MobEffects.HUNGER);
        }
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
