package com.tayjay.isaacsitems.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-11.
 */
public interface IFollowerItem
{
    EntityLivingBase getFollower(ItemStack stack);

}
