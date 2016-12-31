package com.tayjay.isaacsitems.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-12-28.
 */
public class ItemHelper
{
    public static boolean tryAddItemToItemHandlerPlayer(ItemStack stack, IItemHandler inv, EntityPlayer player, boolean forceSwapOut)
    {
        for(int i=0;i<inv.getSlots();i++)
        {
            if(inv.getStackInSlot(i)==null)
            {
                inv.insertItem(i, stack, false);
                return true;
            }
            else
            {
                if (forceSwapOut)
                {
                    player.dropItem(inv.extractItem(i,1,false),false,true);
                    inv.insertItem(i, stack, false);
                    return true;
                }
            }
        }
        return false;
    }

}
