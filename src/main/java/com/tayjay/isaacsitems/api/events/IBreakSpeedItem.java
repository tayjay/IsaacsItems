package com.tayjay.isaacsitems.api.events;

import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Created by tayjay on 2017-01-12.
 */
public interface IBreakSpeedItem
{
    void checkBreakSpeed(PlayerEvent.BreakSpeed event);
}
