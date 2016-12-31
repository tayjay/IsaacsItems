package com.tayjay.isaacsitems.util;

import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IActiveDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 */
public class CapHelper
{
    public static IPlayerDataProvider getPlayerDataCap(EntityPlayer player)
    {
        if (hasPlayerDataCap(player))
        {
            return player.getCapability(IsaacAPI.PLAYER_DATA_CAPABILITY, null);
        }
        return null;
    }

    public static boolean hasPlayerDataCap(EntityPlayer player)
    {
        return player.hasCapability(IsaacAPI.PLAYER_DATA_CAPABILITY, null);
    }

    public static IPlayerItemsProvider getPlayerItemsCap(EntityPlayer player)
    {
        if (hasPlayerItemsCap(player))
        {
            return player.getCapability(IsaacAPI.PLAYER_ITEM_PROVIDER, null);
        }
        return null;
    }

    public static boolean hasPlayerItemsCap(EntityPlayer player)
    {
        return player.hasCapability(IsaacAPI.PLAYER_ITEM_PROVIDER,null);
    }

    public static IActiveDataProvider getActiveItemDataCap(ItemStack stack)
    {
        if (hasActiveItemDataCap(stack))
        {
            return stack.getCapability(IsaacAPI.ACTIVE_DATA_CAPABILITY, null);
        }
        return null;
    }

    public static boolean hasActiveItemDataCap(ItemStack stack)
    {
        return stack.hasCapability(IsaacAPI.ACTIVE_DATA_CAPABILITY, null);
    }
}
