package com.tayjay.isaacsitems.item.trinkets;

import com.tayjay.isaacsitems.item.ItemTrinket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-04.
 */
public class ItemTrinketXPGen extends ItemTrinket
{
    public ItemTrinketXPGen(String name)
    {
        super(name);
    }

    @Override
    public void tickTrinket(ItemStack stack, EntityPlayer player)
    {
        super.tickTrinket(stack, player);
        if(player.worldObj.getTotalWorldTime()%40==0 && player.getRNG().nextInt(20)==0)
        {
            player.addExperience(20);
        }
    }
}
