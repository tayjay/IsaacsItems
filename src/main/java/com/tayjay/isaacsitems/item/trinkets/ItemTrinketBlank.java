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
public class ItemTrinketBlank extends ItemTrinket
{
    public ItemTrinketBlank(String name)
    {
        super(name);
        modifiers.add(new AttributeModifierPair(new AttributeModifier(UUID.fromString("2db55bd0-b1b4-4597-a3be-957ed76f736c"),"isaac:-7 Hearts, sorry.",-14.0,0),SharedMonsterAttributes.MAX_HEALTH));
    }

    /*@Override
    public boolean onPickupTrinket(ItemStack stack, EntityPlayer player)
    {
        if( ItemHelper.tryAddItemToItemHandlerPlayer(stack, CapHelper.getPlayerItemsCap(player).getTrinketInv(),player,false))
        {
            if(!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(Buffs.EXTRA_HEART))
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(Buffs.EXTRA_HEART);
            return true;
        }
        return false;
    }*/
}
