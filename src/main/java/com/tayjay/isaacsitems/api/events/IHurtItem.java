package com.tayjay.isaacsitems.api.events;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by tayjay on 2017-01-04.
 * Handle what  happens to the player when they are hurt.
 */
public interface IHurtItem
{
    boolean onHurtEvent(LivingHurtEvent event);
}
