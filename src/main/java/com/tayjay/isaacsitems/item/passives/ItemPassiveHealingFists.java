package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.api.events.IAttackItem;
import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

/**
 * Created by tayjay on 2017-01-10.
 */
public class ItemPassiveHealingFists extends ItemPassive implements IAttackItem
{
    public ItemPassiveHealingFists(String name)
    {
        super(name);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void doSpecialAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void doRandomAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public boolean onAttackEvent(LivingAttackEvent event)
    {
        if (event.getSource().getSourceOfDamage() instanceof EntityPlayer)
        {
            if (((EntityPlayer) event.getSource().getSourceOfDamage()).getHeldItemMainhand() == null && event.getSource().getSourceOfDamage().isSneaking())
            {
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.REGENERATION,25,1));
                event.setCanceled(true);
                return true;
            }
        }
        return false;
    }
}
