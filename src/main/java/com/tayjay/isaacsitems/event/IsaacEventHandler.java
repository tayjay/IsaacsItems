package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.item.*;
import com.tayjay.isaacsitems.capability.PlayerDataImpl;
import com.tayjay.isaacsitems.capability.PlayerItemsImpl;
import com.tayjay.isaacsitems.lib.Buffs;
import com.tayjay.isaacsitems.lib.FlightControl;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
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
            event.addCapability(PlayerItemsImpl.Provider.NAME,new PlayerItemsImpl.Provider(((EntityPlayer) event.getObject())));
        }
    }

    /*@SubscribeEvent
    public void attachItemCapabilities(AttachCapabilitiesEvent.Item event)
    {
        if(event.getObject() instanceof IActive )
            event.addCapability(ActiveDataImpl.Provider.NAME,new ActiveDataImpl.Provider(((IActive)event.getObject()).getMaxCharge(event.getItemStack())));
    }*/

    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {
        //Decide whether player keeps items when dies.
    }

    @SubscribeEvent
    public void playerTakesDamage(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayerMP)
        {
            IPlayerDataProvider playerData = CapHelper.getPlayerDataCap((EntityPlayer) event.getEntityLiving());
            if (playerData.getSoulHearts() > 0)
            {
                if(playerData.getSoulHearts()<=event.getAmount())
                {
                    float currentAmount = event.getAmount();
                    float newAmount = event.getAmount() - playerData.getSoulHearts();
                    event.setAmount(newAmount);
                    playerData.changeSoulHearts(newAmount-currentAmount);
                }
                else
                {
                    playerData.changeSoulHearts(-1*event.getAmount());
                    event.setAmount(0);
                }
            }
        }
    }

    @SubscribeEvent
    public void syncCapabilties(TickEvent.PlayerTickEvent event)
    {

        if(event.player.worldObj.isRemote|| event.phase== TickEvent.Phase.START)
            return;



        if (event.player.worldObj.getTotalWorldTime() % 5 == 0)
        {
            CapHelper.getPlayerDataCap(event.player).sync((EntityPlayerMP) event.player);
        }
        if (event.player.worldObj.getTotalWorldTime() % 5 == 0)
        {
            CapHelper.getPlayerItemsCap(event.player).syncAllItems((EntityPlayerMP) event.player);
        }
        /*if (CapHelper.getPlayerItemsCap(event.player).getActiveItem()!=null && event.player.worldObj.getTotalWorldTime() % 60 == 0)
        {
            ((IActive) CapHelper.getPlayerItemsCap(event.player).getActiveItem().getItem()).addCharge(CapHelper.getPlayerItemsCap(event.player).getActiveItem());
        }*/

    }

    @SubscribeEvent
    public void handlePlayerTicks(TickEvent.PlayerTickEvent event)
    {
        if(event.player.worldObj.isRemote || event.phase== TickEvent.Phase.END)
        {
            //System.out.println(((EntityPlayerMP) event.player).isDead);
            return;
        }
        if (event.player.worldObj.getTotalWorldTime() % 15 == 0)
        {
            Buffs.confirmPlayerBuffs(event.player);
            FlightControl.refreshFlight(event.player);
        }
        Buffs.tickTimedBuffs();

        CapHelper.getPlayerItemsCap(event.player).tickAllItems((EntityPlayerMP) event.player);



    }

    public void playerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
    {

    }

    @SubscribeEvent
    public void pickupEvent(EntityItemPickupEvent event)
    {
        /*if(event.getEntityPlayer().worldObj.isRemote)
            return;*/
        if(event.getItem().getEntityItem().getItem() instanceof IPickup)
        {
            //Picking up item without activating onPickup event
            if(event.getEntityPlayer().isCreative() && event.getEntityPlayer().isSneaking())
                return;

            //Somehow this stops the item from going into the player's main inventory
            Item item = event.getItem().getEntityItem().getItem();
            if(((IPickup) item).onPickup(event.getItem().getEntityItem(), event.getEntityPlayer()))
            {
                event.getItem().setDead();
                event.setResult(Event.Result.ALLOW);
                //event.getEntityPlayer().addChatMessage(new TextComponentString(event.getItem().getEntityItem().getDisplayName() +" : " + ((IPickup) event.getItem().getEntityItem().getItem()).getDescription()));

                if(item instanceof IActive || item instanceof IPassive || item instanceof ITrinket)
                    NetworkHandler.sendTo(new PacketItemPickup(event.getItem().getEntityItem().getDisplayName(),((IPickup) event.getItem().getEntityItem().getItem()).getDescription()), (EntityPlayerMP) event.getEntityPlayer());
            }
            else
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void doHurtItemEffects(LivingHurtEvent event)
    {
        if(event.getEntityLiving().worldObj.isRemote)
            return;
        if (event.getEntityLiving() instanceof EntityPlayerMP)
        {
            CapHelper.getPlayerItemsCap((EntityPlayer) event.getEntityLiving()).activateHurtItems(event);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(LivingDeathEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            if (event.getEntityLiving().getMaxHealth() == 0)
            {
                if(event.getEntityLiving().getAbsorptionAmount()>0)
                {
                    event.setCanceled(true);//Player Shouldnt die if they have not red health, but do have absorption.
                }
            }
        }
    }

}
