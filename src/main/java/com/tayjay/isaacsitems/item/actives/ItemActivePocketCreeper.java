package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemActivePocketCreeper extends ItemActive
{
    public ItemActivePocketCreeper(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    public void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 4, true);

        /*CapHelper.getPlayerItemsCap(player).getActiveInv().extractItem(0,1,false);
        CapHelper.getPlayerItemsCap(player).getActiveInv().insertItem(0,stack,false);
        NetworkHandler.sendTo(new PacketSyncActiveItem(player.getEntityId(), stack), (EntityPlayerMP) player);*/
    }




}
