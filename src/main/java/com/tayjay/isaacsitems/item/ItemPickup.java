package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IPickup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemPickup extends ItemBase implements IPickup
{
    protected String description;
    public ItemPickup(String name,String description)
    {
        super(name);
        this.description = description;
        setMaxStackSize(2);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(description);
    }

    @Override
    public String getDescription()
    {
        return description;
    }
}
