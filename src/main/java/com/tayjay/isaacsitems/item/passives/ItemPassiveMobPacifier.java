package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

/**
 * Created by tayjay on 2017-01-02.
 */
public class ItemPassiveMobPacifier extends ItemPassive
{
    public ItemPassiveMobPacifier(String name, String description)
    {
        super(name, description);
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {
        List<EntityLiving> closeMobs = player.worldObj.getEntitiesWithinAABB(EntityLiving.class, player.getEntityBoundingBox().expand(40,40,40));
        for (EntityLiving mob : closeMobs)
        {
            for (Object a : mob.targetTasks.taskEntries.toArray())
            {
                EntityAIBase ai = ((EntityAITasks.EntityAITaskEntry)a).action;
                if ((ai instanceof EntityAINearestAttackableTarget) || ai instanceof EntityAIFindEntityNearestPlayer)
                {
                    mob.targetTasks.removeTask(ai);
                }
            }

        }
    }

    @Override
    public void doSpecialAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void doRandomAction(ItemStack stack, EntityPlayer player)
    {

    }
}
