package com.tayjay.isaacsitems.inventory;

import com.tayjay.isaacsitems.api.item.IActive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by tayjay on 2016-12-27.
 */
public class SlotActive extends SlotItemHandler
{
    public SlotActive(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof IActive;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return playerIn.isCreative();
    }

    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        super.onPickupFromSlot(playerIn, stack);
        playerIn.addChatMessage(new TextComponentString("Picking up "+ stack.getDisplayName()));
    }
}
