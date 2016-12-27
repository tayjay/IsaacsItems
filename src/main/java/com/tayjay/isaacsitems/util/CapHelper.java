package com.tayjay.isaacsitems.util;

import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import net.minecraft.entity.player.EntityPlayer;

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
}
