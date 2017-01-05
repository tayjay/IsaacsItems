package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import com.tayjay.isaacsitems.lib.Buffs;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by tayjay on 2016-12-29.
 */
public class ItemPassiveBlank extends ItemPassive
{
    public ItemPassiveBlank(String name,String description)
    {
        super(name,description);
        modifiers.add(Buffs.addBuff(new AttributeModifier(UUID.fromString("8136aebd-c3ae-41d1-8289-e61c3d81bd55"), "Damage Boost", 10.0, 0), SharedMonsterAttributes.ATTACK_DAMAGE));
        //modifiers.add(Buffs.addBuff(new AttributeModifier(UUID.fromString("bb558ab4-68ff-4899-b6f2-70db6580a14d"), "2 Less Hearts", -4.0, 0), SharedMonsterAttributes.MAX_HEALTH));
        //modifiers.add(Buffs.addBuff(new AttributeModifier(UUID.fromString("44019de9-6b2f-4e9f-86f1-f2aa2733ea4a"),"3 Extra Heart",6.0,0),SharedMonsterAttributes.MAX_HEALTH));
    }

    @Override
    public void doTickAction(ItemStack stack, EntityPlayer player)
    {
        if (player.worldObj.getTotalWorldTime() % 7 == 0)
        {
            ItemStack potionStack = new ItemStack(Items.LINGERING_POTION,1);
            PotionEffect potioneffect = new PotionEffect(Potion.getPotionById(8),40);
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(player.worldObj, player.posX, player.posY, player.posZ);
            entityareaeffectcloud.setOwner(player);
            entityareaeffectcloud.setRadius(1.0F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
            //entityareaeffectcloud.setPotion(potiontype);
            entityareaeffectcloud.setDuration(10);

            entityareaeffectcloud.addEffect(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration(), potioneffect.getAmplifier()));
            player.worldObj.spawnEntityInWorld(entityareaeffectcloud);
        }
    }

    @Override
    public void doSpecialAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public void doRandomAction(ItemStack stack, EntityPlayer player)
    {

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        /*System.out.println(playerIn.getEntityAttribute(SharedMonsterAttributes.LUCK).getBaseValue());
        System.out.println(playerIn.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue());
*/
        if(!worldIn.isRemote)
        {
            playerIn.setAbsorptionAmount(playerIn.getAbsorptionAmount() + 4);
            Buffs.addTimedBuff(new AttributeModifier(UUID.randomUUID(),"Temp Health Down",-20,0),SharedMonsterAttributes.MAX_HEALTH,400,playerIn);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
