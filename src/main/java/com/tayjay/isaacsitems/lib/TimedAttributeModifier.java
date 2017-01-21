package com.tayjay.isaacsitems.lib;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

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
        //this.modifier.setSaved(false);
        this.attribute = attribute;
        this.ticksRemaining = time;
        this.player = player;
    }

    public boolean tickAttribute()
    {
        if (modifier != null && attribute != null && player != null)
        {

            if (--this.ticksRemaining < 0)
            {
                if (player.getEntityAttribute(attribute).hasModifier(modifier))
                    player.getEntityAttribute(attribute).removeModifier(modifier);
                return false;
            } else
            {
                if (!player.getEntityAttribute(attribute).hasModifier(modifier))
                    player.getEntityAttribute(attribute).applyModifier(modifier);
            }
        }
        return true;
    }

    public NBTTagCompound serializeNBT()
    {

        NBTTagCompound nbt  = new NBTTagCompound();

        if(modifier==null || attribute==null)
            return nbt;

        nbt.setTag("modifier",SharedMonsterAttributes.writeAttributeModifierToNBT(this.modifier!=null?this.modifier:null));
        nbt.setString("attributeName", attribute.getAttributeUnlocalizedName()!=null?attribute.getAttributeUnlocalizedName():"");
        nbt.setInteger("ticks",ticksRemaining);
        return nbt;

    }

    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("modifier"))
            this.modifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbt.getCompoundTag("modifier"));
        if(nbt.hasKey("attributeName"))
            this.attribute = player.getAttributeMap().getAttributeInstanceByName(nbt.getString("attributeName")).getAttribute();
        if(nbt.hasKey("ticks"))
            this.ticksRemaining = nbt.getInteger("ticks");
    }
}
