package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * Created by tayjay on 2017-01-08.
 */
public class ItemActiveGiveHeart extends ItemActive
{
    public ItemActiveGiveHeart(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    protected void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        player.setAbsorptionAmount(player.getAbsorptionAmount()+4);
        Buffs.addTimedBuff(new AttributeModifier(UUID.fromString("274f0c84-585a-455a-8c7d-e3ded6e2ce10"), "tempHealthUp", 4.0, 0), SharedMonsterAttributes.MAX_HEALTH, 200, player);
    }
}
