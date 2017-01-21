package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.api.events.IBlockHarvestItem;
import com.tayjay.isaacsitems.item.ItemPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by tayjay on 2017-01-13.
 */
public class ItemPassiveFirePick extends ItemPassive implements IBlockHarvestItem
{
    public ItemPassiveFirePick(String name)
    {
        super(name);
    }

    @Override
    public boolean onBlockHarvest(BlockEvent.HarvestDropsEvent event)
    {
        if(!event.isSilkTouching())
        {
            for (int i = 0;i<event.getDrops().size();i++)
            {
                ItemStack drop = event.getDrops().get(i);
                ItemStack smelted = FurnaceRecipes.instance().getSmeltingResult(drop);
                if (smelted != null)
                {
                    if (smelted.stackSize <= 0)
                    {
                        smelted.stackSize = 1;
                    }
                    if(!(smelted.getItem() instanceof ItemBlock))
                        smelted.stackSize*=2;

                    event.getDrops().remove(i);
                    event.getDrops().add(i,smelted);

                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {

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
