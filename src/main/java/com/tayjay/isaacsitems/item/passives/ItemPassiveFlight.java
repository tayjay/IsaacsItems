package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.api.item.IFlightItem;
import com.tayjay.isaacsitems.item.ItemPassive;
import com.tayjay.isaacsitems.lib.FlightControl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-02.
 */
public class ItemPassiveFlight extends ItemPassive implements IFlightItem
{
    public ItemPassiveFlight(String name, String description)
    {
        super(name, description);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {
        if(player.worldObj.isRemote)
            return;
        //FlightControl.refreshFlight(player);
    }

    @Override
    public void doSpecialAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void doRandomAction(ItemStack stack, EntityPlayer player)
    {

    }
}
