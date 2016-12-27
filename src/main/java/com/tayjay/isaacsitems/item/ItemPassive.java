package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.inventory.InventoryPassive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemPassive extends ItemBase implements IPassive
{
    public ItemPassive(String name)
    {
        super(name);
    }

    @Override
    public boolean onPickupPassive(ItemStack stack, EntityPlayer player)
    {
        return true;
    }

    private boolean canPickupItem(ItemStack stack, EntityPlayer player, InventoryPassive inv)
    {
        for(int i = 0 ; i<inv.getSlots();i++)
        {
            if(inv.insertItem(i,stack,true)==null)//Go through all items and see if it is adding to an empty slot.(Added and returns remaining null
            {
                inv.insertItem(i, stack, false);
                return true;//Item was added successfully
            }
        }
        return false;//Has gone through the whole inventory and not inserted item. Cannot be added.
    }
}
