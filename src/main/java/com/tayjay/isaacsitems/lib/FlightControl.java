package com.tayjay.isaacsitems.lib;

import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IFlightItem;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

/**
 * Created by tayjay on 2017-01-02.
 */
public class FlightControl
{
    public static ArrayList<String> flyingPlayers = new ArrayList<String>();

    public static void grantFlight(EntityPlayer player)
    {
        if(canPlayerFly(player))
            player.capabilities.allowFlying = true;

    }

    public static boolean canPlayerFly(EntityPlayer player)
    {
        return flyingPlayers.contains(player.getName());
    }

    public static void refreshFlight(EntityPlayer player)
    {
        if(player!=null && (!player.isCreative()&& !player.isSpectator())&&CapHelper.getPlayerItemsCap(player) !=null)
        {
            IPlayerItemsProvider playerItemsProvider = CapHelper.getPlayerItemsCap(player);
            IItemHandler items = playerItemsProvider.getPassiveItems();
            boolean wasFlying = player.capabilities.isFlying;
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.sendPlayerAbilities();
            for (int i = 0; i < items.getSlots(); i++)
            {
                if (items.getStackInSlot(i)!=null && items.getStackInSlot(i).getItem() instanceof IFlightItem)
                {
                    player.capabilities.allowFlying = true;
                    player.capabilities.isFlying = wasFlying;
                    player.sendPlayerAbilities();
                    return;
                }
            }
        }
    }
}
