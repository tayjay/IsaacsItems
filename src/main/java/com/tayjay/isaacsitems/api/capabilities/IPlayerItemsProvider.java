package com.tayjay.isaacsitems.api.capabilities;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-28.
 */
public interface IPlayerItemsProvider extends INBTSerializable<NBTTagCompound>
{

    /**
     * These may be saved as separate inventories but these methods will get the selected one from each.
     * @return
     */
    ItemStack getActiveItem();

    IItemHandler getActiveInv();

    IItemHandler getPassiveItems();

    ItemStack getTrinketItem();

    IItemHandler getTrinketInv();

    /**
     * Get all mod attribute modifiers what should be active on the player.
     * Go through all items and check if that modifier is present.
     * If it is not in this list, it should be removed from the player's modifiers.
     * @return List of all attribute modifiers that should be applied to the player.
     */
    ArrayList<AttributeModifier> getActiveAttributeModifiers();

    void syncActiveItem(EntityPlayerMP player);

    void syncPassiveItems(EntityPlayerMP player);
}
