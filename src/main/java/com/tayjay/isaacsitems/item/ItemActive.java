package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.capability.ActiveDataImpl;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketSyncActiveItem;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemActive extends ItemPickup implements IActive
{
    protected final int CHARGES;

    public ItemActive(String name,String description, int charges)
    {
        super(name,description);
        this.CHARGES = charges;
        this.setMaxDamage(charges);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isDamageable()
    {
        return true;
    }

    @Override
    public int getMetadata(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    /*@Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new ActiveDataImpl.Provider(getMaxCharge(stack));
    }*/

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        if(player.worldObj.isRemote)
            return false;
        return onPickupActive(stack,player);
    }

    @Override
    public boolean onPickupActive(ItemStack stack, EntityPlayer player)
    {
        return ItemHelper.tryAddItemToItemHandlerPlayer(stack,CapHelper.getPlayerItemsCap(player).getActiveInv(),player,true);
        /*IPlayerItemsProvider playerItemsCap = CapHelper.getPlayerItemsCap(player);
        if(playerItemsCap.getActiveItem()==null)
        {
            playerItemsCap.getActiveInv().insertItem(0, stack, false);
        }
        else
        {
            player.dropItem(playerItemsCap.getActiveInv().extractItem(0,1,false),false,true);
            playerItemsCap.getActiveInv().insertItem(0, stack, false);
        }
        playerItemsCap.syncActiveItem((EntityPlayerMP) player);
        //playerItemsCap.syncActiveItem((EntityPlayerMP) player);
        //NetworkHandler.sendTo(new PacketSyncActiveItem(player.getEntityId(),playerItemsCap.serializeNBT()), (EntityPlayerMP) player);*/
    }

    @Override
    public void onActivate(ItemStack stack, EntityPlayer player)
    {
        if(drainCharge(stack) && !player.worldObj.isRemote)
            onActivateDoAction(stack,player);
    }

    protected abstract void onActivateDoAction(ItemStack stack, EntityPlayer player);

    @Override
    public void tickActive(ItemStack stack, EntityPlayer player)
    {
        if (player.worldObj.isRemote)
        {
            return;
        }
        if (player.worldObj.getTotalWorldTime() % 40 == 0)
        {
            this.addCharge(stack);
        }
    }

    @Override
    public int getMaxCharge(ItemStack stack)
    {
        return this.CHARGES;
    }

    @Override
    public int getCurrentCharge(ItemStack stack)
    {
        return stack.getMetadata();
        //return CapHelper.getActiveItemDataCap(stack).getCurrentCharge();
    }

    @Override
    public void addCharge(ItemStack stack)
    {
        if(stack.getMetadata()<stack.getMaxDamage())
        {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
        //CapHelper.getActiveItemDataCap(stack).addCharge(1);
    }

    @Override
    public boolean drainCharge(ItemStack stack)
    {
        if(stack.getMetadata()==stack.getMaxDamage())
        {
            stack.setItemDamage(stack.getMaxDamage());
            return true;
        }
        return false;
        //return CapHelper.getActiveItemDataCap(stack).drainCharge();
    }
}
