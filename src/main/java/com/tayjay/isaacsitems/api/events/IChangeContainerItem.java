package com.tayjay.isaacsitems.api.events;

import net.minecraftforge.event.entity.player.PlayerContainerEvent;

/**
 * Created by tayjay on 2017-01-08.
 */
public interface IChangeContainerItem
{
    void onOpenContainer(PlayerContainerEvent.Open event);

    void onCloseContainer(PlayerContainerEvent.Close event);
}
