package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 * This is an item that can be used by the player. Single use, give an effect.
 */
public interface IUsable
{
    /**
     * Preform effect on player/world on use.
     * @param stack
     * @param player
     */
    void useItem(ItemStack stack, EntityPlayer player);

    /**
     * Get what type of usable this item is.
     * @param stack
     * @return
     */
    UseType getUseType(ItemStack stack);

    /**
     * Enum for what type of usable this is.
     */
    enum UseType
    {
        CARD,PILL
    }
}
