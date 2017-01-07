package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.item.*;
import com.tayjay.isaacsitems.capability.PlayerDataImpl;
import com.tayjay.isaacsitems.capability.PlayerItemsImpl;
import com.tayjay.isaacsitems.init.ModItems;
import com.tayjay.isaacsitems.lib.Buffs;
import com.tayjay.isaacsitems.lib.FlightControl;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketItemPickup;
import com.tayjay.isaacsitems.network.packets.PacketRegisterChampionType;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
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

    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {
        //Decide whether player keeps items when dies.
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

    @SubscribeEvent
    public void addChampionData(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityLiving)
        {
            byte type = (byte) ((byte) event.getEntity().getUniqueID().getMostSignificantBits() % 20 - 15);
            event.getEntity().getDataManager().register(Buffs.ENTITY_CHAMPION_TYPE, type <= 0 ? 0 : type);
            if (type>0&&!((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(Buffs.CHAMPION_HEALTH_BUFF))
            {
                ((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(Buffs.CHAMPION_HEALTH_BUFF);
                ((EntityLiving) event.getEntity()).heal(((EntityLiving) event.getEntity()).getMaxHealth()/2);
            }
            else if(type<=0&&((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(Buffs.CHAMPION_HEALTH_BUFF))
            {
                ((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(Buffs.CHAMPION_HEALTH_BUFF);
            }

            if(((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)!=null)
            {
                if (type > 0 && !((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(Buffs.CHAMPION_ATTACK_BUFF))
                {
                    ((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(Buffs.CHAMPION_ATTACK_BUFF);
                    ((EntityLiving) event.getEntity()).heal(((EntityLiving) event.getEntity()).getMaxHealth() / 2);
                } else if (type <= 0 && ((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(Buffs.CHAMPION_ATTACK_BUFF))
                {
                    ((EntityLiving) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(Buffs.CHAMPION_ATTACK_BUFF);
                }
            }

        }
    }

    @SubscribeEvent
    public void championMobDrops(LivingDropsEvent event)
    {
        if (event.getEntityLiving() instanceof EntityLiving && event.getEntityLiving().getDataManager().get(Buffs.ENTITY_CHAMPION_TYPE) != null && event.getEntityLiving().getDataManager().get(Buffs.ENTITY_CHAMPION_TYPE).byteValue() > 0)
        {
            event.getEntityLiving().dropItem(ModItems.soulHeart, 1);
            //event.getDrops().add(new EntityItem(((EntityLiving) event.getEntityLiving()).worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(ModItems.soulHeart, 1)));
        }
    }




    //todo: Maybe add this later. For now this impementation is too overpowered
    /*@SubscribeEvent
    public void checkEnchant(TickEvent.PlayerTickEvent event)
    {
        if (event.player.worldObj.isRemote)
        {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        if (player.openContainer instanceof ContainerEnchantment)
        {
            int[] levels = ((ContainerEnchantment) player.openContainer).enchantLevels;
            for(int i = 0;i<levels.length;i++)
            {
                if(levels[i]<20)
                {
                    ((ContainerEnchantment) player.openContainer).enchantLevels[i] = 20;
                }
            }
            player.openContainer.detectAndSendChanges();
        }
    }*/

}
