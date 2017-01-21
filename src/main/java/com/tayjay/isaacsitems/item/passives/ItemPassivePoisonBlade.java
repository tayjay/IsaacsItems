package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.api.events.IAttackItem;
import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

/**
 * Created by tayjay on 2017-01-08.
 */
public class ItemPassivePoisonBlade extends ItemPassive implements IAttackItem
{
    public ItemPassivePoisonBlade(String name)
    {
        super(name);
    }

    @Override
    public boolean onAttackEvent(LivingAttackEvent event)
    {
        if (event.getEntityLiving().worldObj.isRemote)
        {
            return false;
        }
        if (event.getSource().getSourceOfDamage() instanceof EntityPlayer)
        {
            EntityPlayer player = ((EntityPlayer) event.getSource().getSourceOfDamage());
            if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemSword)
            {

                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.GLOWING, 200));
                return true;
            }
        }
        return false;
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
}
