package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.IPickup;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.Item;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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

    @SubscribeEvent
    public void doAttackItemEffects(LivingAttackEvent event)
    {
        if (event.getEntityLiving().worldObj.isRemote)
        {
            return;
        }
        if (event.getSource().getSourceOfDamage() instanceof EntityPlayer)
        {
            CapHelper.getPlayerItemsCap((EntityPlayer) event.getSource().getEntity()).activateAttackItems(event);
        }
    }

    @SubscribeEvent
    public void doOpenContainerEffects(PlayerContainerEvent event)
    {
        if (event.getEntityLiving().worldObj.isRemote)
        {
            return;
        }

        CapHelper.getPlayerItemsCap(event.getEntityPlayer()).activateContainerChangeItems(event);
    }

    @SubscribeEvent
    public void checkBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (event.getEntityPlayer().worldObj.isRemote)
        {
            return;
        }

        CapHelper.getPlayerItemsCap(event.getEntityPlayer()).activateBreakSpeedItems(event);

    }

    @SubscribeEvent
    public void checkHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getHarvester()==null || event.getHarvester().worldObj.isRemote)
        {
            return;
        }

        CapHelper.getPlayerItemsCap(event.getHarvester()).activateBlockHarvestItems(event);
    }

}
