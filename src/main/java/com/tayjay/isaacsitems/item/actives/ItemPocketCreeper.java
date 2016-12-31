package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketSyncActiveItem;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.lwjgl.Sys;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemPocketCreeper extends ItemActive
{
    public ItemPocketCreeper(String name,String description, int charges)
    {
        super(name,description, charges);
    }

    @Override
    public void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 2, true);

        /*CapHelper.getPlayerItemsCap(player).getActiveInv().extractItem(0,1,false);
        CapHelper.getPlayerItemsCap(player).getActiveInv().insertItem(0,stack,false);
        NetworkHandler.sendTo(new PacketSyncActiveItem(player.getEntityId(), stack), (EntityPlayerMP) player);*/
    }




}
