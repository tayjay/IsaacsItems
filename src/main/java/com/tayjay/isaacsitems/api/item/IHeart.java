package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 * When this item is picked up it will give health. Either red heart or soul.
 */
public interface IHeart
{
    /**
     * Action to be preformed when the heart is picked up. Gives health or a soul heart.
     * @param heartStack
     * @param entityPlayer
     */
    boolean onPickupHeart(ItemStack heartStack, EntityPlayer entityPlayer);

    /**
     * Should this entity pickup the heart. Prevent when full health.
     * @param heartStack
     * @param entityPlayer
     * @return Heart can be picked up.
     */
    boolean canPickupHeart(ItemStack heartStack, EntityPlayer entityPlayer);

    /**
     * Is this heart a soul/demon heart? Or will it heal?
     * @param heartStack
     * @return This is a soul/demon heart.
     */
    boolean isSoulHeart(ItemStack heartStack);

    /**
     * Check if this is a full or just a half heart
     * @param heartStack
     * @return This is a full heart.
     */
    boolean isFullHeart(ItemStack heartStack);
}
