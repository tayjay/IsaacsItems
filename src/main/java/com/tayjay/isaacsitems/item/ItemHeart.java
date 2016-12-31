package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.api.item.IHeart;
import com.tayjay.isaacsitems.item.ItemBase;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-12-26.
 */
public abstract class ItemHeart extends ItemPickup implements IHeart
{
    public ItemHeart(String name)
    {
        super(name,"Good for what ails you.");
        setMaxStackSize(2);
    }

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        if(canPickupHeart(stack,player))
            return onPickupHeart(stack,player);
        return false;
    }

    @Override
    public boolean canPickupHeart(ItemStack heartStack, EntityPlayer entityPlayer)
    {
        if(entityPlayer.isCreative())
            return true;
        if(isSoulHeart(heartStack) && !heartsAreFull(entityPlayer))
            return true;
        else//This is a red heart
            return entityPlayer.getHealth()<entityPlayer.getMaxHealth();
    }

    public boolean heartsAreFull(EntityPlayer entityPlayer)
    {
        //todo: Do checks with capabilities to make sure the limit of hearts hasn't been reached.
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(worldIn.isRemote)
        {
            playerIn.addChatMessage(new TextComponentString(CapHelper.getPlayerDataCap(playerIn).getRedHearts() + " Red Hearts"));
            playerIn.addChatMessage(new TextComponentString(CapHelper.getPlayerDataCap(playerIn).getSoulHearts() + " Soul Hearts"));
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
