package com.tayjay.isaacsitems.api.events;

import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by tayjay on 2017-01-13.
 */
public interface IBlockHarvestItem
{
    boolean onBlockHarvest(BlockEvent.HarvestDropsEvent event);
}
