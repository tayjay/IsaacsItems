package com.tayjay.isaacsitems.handler;

import com.tayjay.isaacsitems.client.gui.GuiIsaacsItems;
import com.tayjay.isaacsitems.inventory.ContainerIsaacsItems;
import com.tayjay.isaacsitems.inventory.InventoryActive;
import com.tayjay.isaacsitems.inventory.InventoryPassive;
import com.tayjay.isaacsitems.inventory.InventoryTrinket;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by tayjay on 2016-12-29.
 */
public class GuiHandler implements IGuiHandler
{
    public enum GuiIDs
    {
        ISAACS_ITEMS
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case ISAACS_ITEMS:
                return new ContainerIsaacsItems(player.inventory,new InventoryPassive(CapHelper.getPlayerItemsCap(player),player),new InventoryActive(CapHelper.getPlayerItemsCap(player),player),new InventoryTrinket(CapHelper.getPlayerItemsCap(player),player));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case ISAACS_ITEMS:
                return new GuiIsaacsItems(player.inventory, new InventoryPassive(CapHelper.getPlayerItemsCap(player), player),new InventoryActive(CapHelper.getPlayerItemsCap(player),player),new InventoryTrinket(CapHelper.getPlayerItemsCap(player),player));
        }
        return null;
    }
}
