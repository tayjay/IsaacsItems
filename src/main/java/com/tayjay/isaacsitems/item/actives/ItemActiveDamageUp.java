package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * Created by tayjay on 2017-01-16.
 */
public class ItemActiveDamageUp extends ItemActive
{
    public ItemActiveDamageUp(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    protected void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        Buffs.addTimedBuff(new AttributeModifier(UUID.fromString("500bb5bb-6115-4948-8234-8e0b1236368f"), "tempDamageUp", 0.5, 1), SharedMonsterAttributes.ATTACK_DAMAGE, 400, player);
    }
}
