package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IStatModifier;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.item.ItemPickup;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-30.
 */
public class ItemTrinket extends ItemPickup implements ITrinket,IStatModifier
{
    protected ArrayList<AttributeModifier> modifiers;
    public ItemTrinket(String name,String description)
    {
        super(name,description);
        modifiers = new ArrayList<AttributeModifier>();
        setMaxStackSize(1);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        return onPickupTrinket(stack,player);
    }

    @Override
    public boolean onPickupTrinket(ItemStack stack, EntityPlayer player)
    {
        return ItemHelper.tryAddItemToItemHandlerPlayer(stack, CapHelper.getPlayerItemsCap(player).getTrinketInv(),player,true);
    }

    @Override
    public void doTrinketAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void tickTrinket(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public boolean shouldTickTrinket(ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ArrayList<AttributeModifier> getStatModifiers(ItemStack stack)
    {
        return modifiers;
    }
}
