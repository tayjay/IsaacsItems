package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.List;

/**
 * Created by tayjay on 2017-01-10.
 */
public class ItemActiveChampionSpawner extends ItemActive
{
    public ItemActiveChampionSpawner(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    protected void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        byte type;
        Entity entity;
        EntityLiving champion;
        List<String> entityNames = EntityList.getEntityNameList();

        do
        {
            do
            {
                entity = EntityList.createEntityByName(entityNames.get(player.getRNG().nextInt(entityNames.size() - 1)), player.worldObj);
            } while (!(entity instanceof EntityLiving));

            champion = (EntityLiving) entity;
            type = Buffs.getChampionType(champion);
        }
        while(type<=0);
        champion.setPosition(player.posX+2,player.posY,player.posZ+2);
        player.worldObj.spawnEntityInWorld(champion);
    }
}
