package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

/**
 * Created by tayjay on 2017-01-10.
 */
public class ItemPassiveGills extends ItemPassive
{
    public ItemPassiveGills(String name)
    {
        super(name);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {

        player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 40, 1));
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
