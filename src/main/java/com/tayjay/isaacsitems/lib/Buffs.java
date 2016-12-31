package com.tayjay.isaacsitems.lib;

import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.*;

/**
 * Created by tayjay on 2016-12-30.
 */
public class Buffs
{
    /*
    --bb558ab4-68ff-4899-b6f2-70db6580a14d
--8136aebd-c3ae-41d1-8289-e61c3d81bd55
--44019de9-6b2f-4e9f-86f1-f2aa2733ea4a
--2db55bd0-b1b4-4597-a3be-957ed76f736c
--445ec31b-0842-456e-ba8f-86866040555a
2ccae10f-e758-4b04-a94b-8e4b3ee64db3
d1caf66e-bb10-43a4-ac8d-24f23bd3c0ff
6c77317e-3d29-4f8a-b5c5-8c6a611a9ad0
1b1a7562-25ec-48a5-973a-d9e201048c24
b442c2af-8894-419d-8158-a27ada864c0d
a7171542-d493-4116-a012-3f3da213be93
6acccb4f-23a0-4d36-9f90-dc4f7d5525d9
7a23e9b5-c871-4b65-9c56-c762a59bc17d
ee72d199-944a-4853-97a4-b16f59f68045
253f3b31-ddec-4c3e-bc84-d7a164ada7f7
     */
    private static final Random rand = new Random(27);
    private static HashMap<AttributeModifier,IAttribute> buffMap = new HashMap<AttributeModifier, IAttribute>();

    /*public static final UUID EXTRA_HEART_ID = MathHelper.getRandomUuid(rand);
    public static final UUID DAMAGE_UP_ID = MathHelper.getRandomUuid(rand);

    public static final AttributeModifier EXTRA_HEART = addBuff(new AttributeModifier(EXTRA_HEART_ID, "Extra Heart",2.0,0),SharedMonsterAttributes.MAX_HEALTH);  //0="d0 += attributemodifier.getAmount();"
                                                                                                                    //1=d1 += d0 * attributemodifier1.getAmount();
                                                                                                                    //2=d1 *= 1.0D + attributemodifier2.getAmount();
    public static final AttributeModifier DAMAGE_UP = addBuff(new AttributeModifier(DAMAGE_UP_ID,"Damage Up",5.0,0),SharedMonsterAttributes.ATTACK_DAMAGE);
*/
    public static AttributeModifier addBuff(AttributeModifier mod,IAttribute attribute)
    {
        buffMap.put(mod,attribute);
        return mod;
    }

    public static void applyAttributeModifier(EntityPlayer player, AttributeModifier modifier)
    {
        if(!buffMap.values().contains(modifier))
        {
            System.out.println("Modifier " + modifier + " is not in this mod.");
            return;
        }
        if(!player.getEntityAttribute(buffMap.get(modifier)).hasModifier(modifier))
            player.getEntityAttribute(buffMap.get(modifier)).applyModifier(modifier);
    }

    /**
     *
     * @param player
     */
    public static void confirmPlayerBuffs(EntityPlayer player)
    {
        ArrayList<AttributeModifier> activeModifiers = CapHelper.getPlayerItemsCap(player).getActiveAttributeModifiers();
        //Remove old modifiers and add missing ones.
        for (AttributeModifier modifier : buffMap.keySet())
        {
            if(player.getEntityAttribute(buffMap.get(modifier)).hasModifier(modifier) && !activeModifiers.contains(modifier))
                player.getEntityAttribute(buffMap.get(modifier)).removeModifier(modifier);

            if(activeModifiers.contains(modifier) && !player.getEntityAttribute(buffMap.get(modifier)).hasModifier(modifier))
                player.getEntityAttribute(buffMap.get(modifier)).applyModifier(modifier);
        }

    }


}
