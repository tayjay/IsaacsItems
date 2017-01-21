package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;

import java.util.List;

/**
 * Created by tayjay on 2017-01-04.
 */
public class ItemActivePushUp extends ItemActive
{
    public ItemActivePushUp(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    protected void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        List<Entity> entityList=  player.worldObj.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().expand(6, 2, 6), EntitySelectors.IS_ALIVE);
        for (Entity e : entityList)
        {
            e.setVelocity(0,2,0);
        }
    }
}
