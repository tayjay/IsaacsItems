package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * Created by tayjay on 2017-01-02.
 */
public class ItemPassiveFastHands extends ItemPassive
{
    public ItemPassiveFastHands(String name, String description)
    {
        super(name, description);
        modifiers.add(Buffs.addBuff(new AttributeModifier(UUID.fromString("2ccae10f-e758-4b04-a94b-8e4b3ee64db3"),"Fast Hands Attack Speed Up",20.0,0), SharedMonsterAttributes.ATTACK_SPEED));
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
