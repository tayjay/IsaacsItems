package com.tayjay.isaacsitems.inventory;

import com.tayjay.isaacsitems.api.item.IActive;
import net.minecraft.item.ItemStack;
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
}
