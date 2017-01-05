package com.tayjay.isaacsitems.lib;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by tayjay on 2017-01-03.
 */
public class TimedAttributeModifier
{
    public AttributeModifier modifier;
    public IAttribute attribute;
    public int ticksRemaining;
    public EntityPlayer player;

    public TimedAttributeModifier(AttributeModifier modifier, IAttribute attribute, int time, EntityPlayer player)
    {
        this.modifier = modifier;
        this.modifier.setSaved(false);
        this.attribute = attribute;
        this.ticksRemaining = time;
        this.player = player;
    }

    public boolean tickAttribute()
    {
        if(--this.ticksRemaining<0)
        {
            if (player.getEntityAttribute(attribute).hasModifier(modifier))
                player.getEntityAttribute(attribute).removeModifier(modifier);
            return false;
        }
        else
        {
            if (!player.getEntityAttribute(attribute).hasModifier(modifier))
                player.getEntityAttribute(attribute).applyModifier(modifier);
        }
        return true;
    }
}
