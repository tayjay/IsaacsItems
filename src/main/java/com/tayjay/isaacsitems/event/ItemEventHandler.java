package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.IPickup;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by tayjay on 2017-01-06.
 * Handle events on the common side that items will use to activate functions.
 */
public class ItemEventHandler
{
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

}
