package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.api.events.IHurtItem;
import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by tayjay on 2017-01-04.
 */
public class ItemPassivePoisonHurt extends ItemPassive implements IHurtItem
{
    public ItemPassivePoisonHurt(String name, String description)
    {
        super(name, description);
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
    public boolean onHurtEvent(LivingHurtEvent event)
    {
        if (event.getSource().getSourceOfDamage() != null)
        {
            EntityLivingBase entityLivingBase = ((EntityLivingBase) event.getSource().getEntity());
            //entityLivingBase.addPotionEffect(new PotionEffect(Potion.getPotionById(19),40,2));
            if(entityLivingBase instanceof EntityZombie || entityLivingBase instanceof EntitySkeleton)
                entityLivingBase.addPotionEffect(new PotionEffect(Potion.getPotionById(6),1,0));
            else
                entityLivingBase.addPotionEffect(new PotionEffect(Potion.getPotionById(7),1,0));
        }
        return false;
    }
}
