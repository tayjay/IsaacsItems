package com.tayjay.isaacsitems.lib;

import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;

import java.util.*;

/**
 * Created by tayjay on 2016-12-30.
 */
public class Buffs
{
    /*
--274f0c84-585a-455a-8c7d-e3ded6e2ce10
--500bb5bb-6115-4948-8234-8e0b1236368f
9ab14d73-b1d7-47fd-994a-7775dd9ef620
e6986c28-3cb4-469b-97f8-6a6d1a526453
355c85c7-b7e2-4920-9fcd-d33cf2b00c56
0e8d82de-0525-4eb3-bf03-f7f5df7a8363
147f47f1-89fc-4f3f-aa66-6871e022b279
aa7fcf2e-6ae4-4e45-b403-ea957f11874e
b622b844-19fb-46ee-8c4b-66d549274083
36db728f-76b1-455f-936c-98fc3f66c919
870d6577-fa21-4150-a00c-892b9e36d723
1a4bbb1b-0f7d-4ddf-92da-11233609c1bd
dbf8b128-cabd-42a1-848e-f1bfc8d8423c
7a0e940f-0f06-41fa-89ae-3cc62dea94a6
d7dac856-d2eb-40ae-a519-16ddd52eab64
     */
    /*private static final Random rand = new Random(27);
    private static HashMap<AttributeModifier,IAttribute> buffMap = new HashMap<AttributeModifier, IAttribute>();
    private static ArrayList<TimedAttributeModifier> timedBuffs = new ArrayList<TimedAttributeModifier>();*/

    //public static DataParameter<Byte> ENTITY_CHAMPION_TYPE = DataSerializers.BYTE.createKey(127);//EntityDataManager.<Byte>createKey(EntityLiving.class, DataSerializers.BYTE);
    public static AttributeModifier CHAMPION_HEALTH_BUFF = new AttributeModifier(UUID.fromString("d1caf66e-bb10-43a4-ac8d-24f23bd3c0ff"),"champion_health_buff",10.0,0);
    public static AttributeModifier CHAMPION_ATTACK_BUFF = new AttributeModifier(UUID.fromString("6c77317e-3d29-4f8a-b5c5-8c6a611a9ad0"),"champion_attack_buff",2.0,0);

    /*public static final UUID EXTRA_HEART_ID = MathHelper.getRandomUuid(rand);
    public static final UUID DAMAGE_UP_ID = MathHelper.getRandomUuid(rand);

    public static final AttributeModifier EXTRA_HEART = addBuff(new AttributeModifier(EXTRA_HEART_ID, "Extra Heart",2.0,0),SharedMonsterAttributes.MAX_HEALTH);  //0="d0 += attributemodifier.getAmount();"
                                                                                                                    //1=d1 += d0 * attributemodifier1.getAmount();
                                                                                                                    //2=d1 *= 1.0D + attributemodifier2.getAmount();
    public static final AttributeModifier DAMAGE_UP = addBuff(new AttributeModifier(DAMAGE_UP_ID,"Damage Up",5.0,0),SharedMonsterAttributes.ATTACK_DAMAGE);
*/
    /*public static AttributeModifier addBuff(AttributeModifier mod,IAttribute attribute)
    {
        buffMap.put(mod,attribute);
        return mod;
    }*/

    public static TimedAttributeModifier addTimedBuff(AttributeModifier mod, IAttribute attribute, int duration, EntityPlayer player)
    {
        TimedAttributeModifier timedAttributeModifier = new TimedAttributeModifier(mod, attribute, duration, player);
        CapHelper.getPlayerDataCap(player).addTimedModifier(timedAttributeModifier);
        return timedAttributeModifier;
    }

    public static void tickTimedBuffs(EntityPlayer player)
    {
        CapHelper.getPlayerDataCap(player).tickTimedAttributeModifiers();
    }

    /*public static void applyAttributeModifier(EntityPlayer player, AttributeModifier modifier)
    {
        if(!buffMap.values().contains(modifier))
        {
            System.out.println("Modifier " + modifier + " is not in this mod.");
            return;
        }
        if(!player.getEntityAttribute(buffMap.get(modifier)).hasModifier(modifier))
            player.getEntityAttribute(buffMap.get(modifier)).applyModifier(modifier);
    }*/

    public static byte getChampionType(EntityLiving mob)
    {
        return (byte)(mob.getUniqueID().getMostSignificantBits() % 20 - 15);
    }

    /**
     *
     * @param player
     */
    public static void confirmPlayerBuffs(EntityPlayer player)
    {
        ArrayList<AttributeModifierPair> activeModifiers = CapHelper.getPlayerItemsCap(player).getActiveAttributeModifiers();
        //Collection<IAttributeInstance> playerAttributes = player.getAttributeMap().getAllAttributes();
        for (IAttributeInstance attributeInstance : player.getAttributeMap().getAllAttributes())
        {
            for(AttributeModifier modifier : attributeInstance.getModifiers())
            {
                if (modifier.getName().contains("isaac"))
                {
                    attributeInstance.removeModifier(modifier);
                }
            }
        }
        for (AttributeModifierPair modifier : activeModifiers)
        {
            player.getAttributeMap().getAttributeInstance(modifier.attribute).applyModifier(modifier.modifier);
        }

        //Remove old modifiers and add missing ones.
        /*for (AttributeModifier modifier : buffMap.keySet())
        {
            if(activeModifiers.contains(modifier) && !player.getEntityAttribute(buffMap.get(modifier)).hasModifier(modifier))
                player.getEntityAttribute(buffMap.get(modifier)).applyModifier(modifier);

            if(player.getEntityAttribute(buffMap.get(modifier)).hasModifier(modifier) && !activeModifiers.contains(modifier))
                player.getEntityAttribute(buffMap.get(modifier)).removeModifier(modifier);
        }*/

    }


}
