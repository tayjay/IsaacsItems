package com.tayjay.isaacsitems.item.trinkets;

import com.tayjay.isaacsitems.item.ItemTrinket;
import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

/**
 * Created by tayjay on 2016-12-30.
 */
public class ItemTrinketLuck extends ItemTrinket
{
    public ItemTrinketLuck(String name, String description,int luckAmount)
    {
        super(name, description);
        modifiers.add(Buffs.addBuff(new AttributeModifier(UUID.fromString("445ec31b-0842-456e-ba8f-86866040555a"),luckAmount+" Luck",luckAmount,0), SharedMonsterAttributes.LUCK));
    }
}
