package com.tayjay.isaacsitems.item.passives;

import com.tayjay.isaacsitems.item.ItemPassive;
import com.tayjay.isaacsitems.lib.Buffs;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
        System.out.println(playerIn.getEntityAttribute(SharedMonsterAttributes.LUCK).getBaseValue());
        System.out.println(playerIn.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue());

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
