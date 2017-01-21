package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

/**
 * Created by tayjay on 2017-01-02.
 * Remove all potion effects. Down side, positive effects won't work on you either.
 */
public class ItemPassiveCure extends ItemPassive
{
    public ItemPassiveCure(String name)
    {
        super(name);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {
        if(!player.getActivePotionEffects().isEmpty())
        {
            //player.clearActivePotions();
            for (PotionEffect potionEffect : player.getActivePotionEffects())
            {
                if(potionEffect.getPotion().isBadEffect())
                    player.removePotionEffect(potionEffect.getPotion());
            }
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
