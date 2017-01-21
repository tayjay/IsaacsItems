package com.tayjay.isaacsitems.item.pickups;

import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.item.ItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-16.
 */
public class ItemBattery extends ItemPickup
{
    public ItemBattery(String name)
    {
        super(name);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        ItemStack activeStack = CapHelper.getPlayerItemsCap(player).getActiveItem();

        if (activeStack != null && activeStack.getItem() instanceof IActive)
        {
            if(((IActive) activeStack.getItem()).getCurrentCharge(activeStack) < ((IActive) activeStack.getItem()).getMaxCharge(activeStack))
            {
                for(int i = 0;i<6;i++)
                {
                    ((IActive) activeStack.getItem()).addCharge(activeStack);
                }
                return true;
            }
        }
        return false;
    }
}
