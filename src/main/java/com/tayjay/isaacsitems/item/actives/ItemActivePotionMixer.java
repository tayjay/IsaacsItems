package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.awt.*;

/**
 * Created by tayjay on 2017-01-10.
 */
public class ItemActivePotionMixer extends ItemActive
{
    public ItemActivePotionMixer(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    public void onActivate(ItemStack stack, EntityPlayer player)
    {
        if(!player.worldObj.isRemote)
        {
            if(drainCharge(stack))
            {
                onActivateDoAction(stack, player);
            }
            else
            {
                if (player.isSneaking() && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemPotion)
                {
                    if(stack.getTagCompound()==null)
                        stack.setTagCompound(new NBTTagCompound());
                    NBTTagCompound compound = stack.getTagCompound();
                    if (!compound.hasKey("StoredEffects"))
                        compound.setTag("StoredEffects", new NBTTagList());
                    NBTTagList tagList = compound.getTagList("StoredEffects", 10);
                    NBTTagCompound effectNBT = new NBTTagCompound();
                    for (PotionEffect effect : PotionUtils.getEffectsFromStack(player.getHeldItemMainhand()))
                    {
                        effect.writeCustomPotionEffectToNBT(effectNBT);
                        tagList.appendTag(effectNBT);
                        player.addChatMessage(new TextComponentString("Added " + effect.getEffectName() + " to mixer."));
                    }

                    //Add charge when a potion is added
                    if(stack.getMetadata()<stack.getMaxDamage())
                    {
                        stack.setItemDamage(stack.getItemDamage() - 1);
                    }
                    player.inventory.deleteStack(player.getHeldItemMainhand());
                } else
                {

                }
            }
        }

    }

    @Override
    protected void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound.hasKey("StoredEffects", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("StoredEffects", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

                if (potioneffect != null)
                {
                    if (player.getActivePotionEffect(potioneffect.getPotion()) != null)
                    {
                        PotionEffect effectOld = player.getActivePotionEffect(potioneffect.getPotion());
                        player.addPotionEffect(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration() + effectOld.getDuration(), Math.max(potioneffect.getAmplifier(), effectOld.getAmplifier())));
                    }
                    else
                    {
                        player.addPotionEffect(potioneffect);
                        player.addChatMessage(new TextComponentString("Applied " + potioneffect.getEffectName()));
                    }
                }

            }
            compound.removeTag("StoredEffects");
        }
        else
        {
            player.addChatMessage(new TextComponentString("No potion effects stored."));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if(!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.setItemDamage(stack.getMaxDamage());
        }
    }

    @Override
    public void addCharge(ItemStack stack)
    {
        return;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.addChatMessage(new TextComponentString(itemStackIn.getTagCompound().toString()));
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        if(!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.setItemDamage(stack.getMaxDamage());
        }
        return super.onPickup(stack, player);
    }

    @Override
    public void renderInGUI(ItemStack stack)
    {
        super.renderInGUI(stack);
        Minecraft mc = Minecraft.getMinecraft();
        if(stack.getTagCompound()==null)
            return;
        NBTTagCompound compound = stack.getTagCompound();
        if(!compound.hasKey("StoredEffects"))
            return;
        NBTTagList nbttaglist = compound.getTagList("StoredEffects", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

            if (potioneffect != null)
            {
                mc.fontRendererObj.drawString(potioneffect.getEffectName(), 44, 8 * i + 4, Color.BLACK.getRGB());
            }
        }
    }
}
