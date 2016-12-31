package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.IStatModifier;
import com.tayjay.isaacsitems.inventory.InventoryPassive;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemPassive extends ItemPickup implements IPassive,IStatModifier
{
    protected ArrayList<AttributeModifier> modifiers;
    public ItemPassive(String name,String description)
    {
        super(name,description);
        modifiers = new ArrayList<AttributeModifier>();
        setMaxStackSize(1);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        return onPickupPassive(stack,player);
    }

    @Override
    public boolean onPickupPassive(ItemStack stack, EntityPlayer player)
    {
        return ItemHelper.tryAddItemToItemHandlerPlayer(stack, CapHelper.getPlayerItemsCap(player).getPassiveItems(),player,false);
    }

    @Override
    public ArrayList<AttributeModifier> getStatModifiers(ItemStack stack)
    {
        return modifiers;
    }
}
