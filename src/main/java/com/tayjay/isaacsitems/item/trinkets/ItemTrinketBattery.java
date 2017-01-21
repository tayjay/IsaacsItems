package com.tayjay.isaacsitems.item.trinkets;

import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.item.ItemTrinket;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-04.
 */
public class ItemTrinketBattery extends ItemTrinket
{
    public ItemTrinketBattery(String name)
    {
        super(name);
    }

    @Override
    public void tickTrinket(ItemStack stack, EntityPlayer player)
    {
        super.tickTrinket(stack, player);
        if (player.worldObj.getTotalWorldTime() % 5 == 0 && player.getRNG().nextInt(10) == 0)
        {
            IPlayerItemsProvider playerItemsProvider = CapHelper.getPlayerItemsCap(player);
            if (playerItemsProvider.getActiveItem() != null && playerItemsProvider.getActiveItem().getItem() instanceof IActive)
            {
                ((IActive) playerItemsProvider.getActiveItem().getItem()).addCharge(playerItemsProvider.getActiveItem());
            }
        }
    }
}
