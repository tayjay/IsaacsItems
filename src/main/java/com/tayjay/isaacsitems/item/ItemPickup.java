package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IPickup;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemPickup extends ItemBase implements IPickup
{
    protected String description;
    public ItemPickup(String name)
    {
        super(name);
        this.description = this.getUnlocalizedName().concat(".desc");
        setMaxStackSize(2);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(getDescription());
    }

    @Override
    public String getDescription()
    {
        return I18n.format(description);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }
}
