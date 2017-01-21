package com.tayjay.isaacsitems.item.trinkets;

import com.tayjay.isaacsitems.item.ItemTrinket;
import com.tayjay.isaacsitems.lib.AttributeModifierPair;
import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

/**
 * Created by tayjay on 2016-12-30.
 */
public class ItemTrinketLuck extends ItemTrinket
{
    public ItemTrinketLuck(String name,int luckAmount)
    {
        super(name);
        modifiers.add(new AttributeModifierPair(new AttributeModifier(UUID.fromString("445ec31b-0842-456e-ba8f-86866040555a"),"isaac:"+luckAmount+" Luck",luckAmount,0), SharedMonsterAttributes.LUCK));
    }
}
