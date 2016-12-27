package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.api.item.*;
import com.tayjay.isaacsitems.capability.PlayerDataImpl;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-12-26.
 */
public class IsaacEventHandler
{
    @SubscribeEvent
    public void attachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(PlayerDataImpl.Provider.NAME,new PlayerDataImpl.Provider(((EntityPlayer) event.getObject())));
        }
    }

    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {
        //Decide whether player keeps items when dies.
    }

    @SubscribeEvent
    public void syncCapabilties(TickEvent.PlayerTickEvent event)
    {
        if(event.player.worldObj.isRemote)
            return;
        if (event.player.worldObj.getTotalWorldTime() % 5 == 0)
        {
            CapHelper.getPlayerDataCap(event.player).sync((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void pickupEvent(EntityItemPickupEvent event)
    {
        if(event.getEntityPlayer().worldObj.isRemote)
            return;
        if(event.getItem().getEntityItem().getItem() instanceof IPickup)
        {
            if(((IPickup) event.getItem().getEntityItem().getItem()).onPickup(event.getItem().getEntityItem(), event.getEntityPlayer()))
            {
                event.getItem().setDead();
                event.setResult(Event.Result.ALLOW);
                //Somehow this stops the item from going into the player's main inventory
            }
            else
                event.setCanceled(true);
        }
    }

}
