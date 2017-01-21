package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.IStatModifier;
import com.tayjay.isaacsitems.lib.AttributeModifierPair;
import com.tayjay.isaacsitems.lib.Buffs;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemPassive extends ItemPickup implements IPassive,IStatModifier
{
    protected ArrayList<AttributeModifierPair> modifiers;
    public ItemPassive(String name)
    {
        super(name);
        modifiers = new ArrayList<AttributeModifierPair>();
        setMaxStackSize(1);
        setCreativeTab(IsaacsItems.passiveItemsTab);
    }

    public void addModifier(AttributeModifier mod, IAttribute attribute)
    {
        this.modifiers.add(new AttributeModifierPair(mod, attribute));
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
    public ArrayList<AttributeModifierPair> getStatModifiers(ItemStack stack)
    {
        return modifiers;
    }
}
