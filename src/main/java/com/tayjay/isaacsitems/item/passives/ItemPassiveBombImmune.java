package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.api.events.IHurtItem;
import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by tayjay on 2017-01-10.
 */
public class ItemPassiveBombImmune extends ItemPassive implements IHurtItem
{
    public ItemPassiveBombImmune(String name)
    {
        super(name);
    }

    @Override
    public boolean onHurtEvent(LivingHurtEvent event)
    {
        if("explosion.player".equals(event.getSource().getDamageType()) || "explosion".equals(event.getSource().getDamageType()))
        {
            event.setCanceled(true);
            return true;
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
