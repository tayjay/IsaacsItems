package com.tayjay.isaacsitems.api.events;

import net.minecraftforge.event.entity.living.LivingAttackEvent;

/**
 * Created by tayjay on 2017-01-08.
 */
public interface IAttackItem
{
    boolean onAttackEvent(LivingAttackEvent event);
}
