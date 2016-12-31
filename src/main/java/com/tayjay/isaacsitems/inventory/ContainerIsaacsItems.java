package com.tayjay.isaacsitems.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-12-29.
 */
public class ContainerIsaacsItems extends Container
{
    public final EntityPlayer player;
    public final InventoryPassive passiveInventory;
    public final InventoryActive activeInventory;
    public final InventoryTrinket trinketInventory;

    public ContainerIsaacsItems(InventoryPlayer invPlayer, InventoryPassive inventoryPassive,InventoryActive inventoryActive,InventoryTrinket inventoryTrinket)
    {
        this.player = invPlayer.player;
        this.passiveInventory = inventoryPassive;
        this.activeInventory = inventoryActive;
        this.trinketInventory = inventoryTrinket;

        this.setupSlots(invPlayer,inventoryPassive,inventoryActive,inventoryTrinket);
    }

    private void setupSlots(InventoryPlayer inventoryPlayer, InventoryPassive inventoryPassive,InventoryActive inventoryActive,InventoryTrinket inventoryTrinket)
    {
        for(int i= 0;i<2;i++)
            for(int k = 0; k < inventoryPassive.getSlots()/2; k++)
                this.addSlotToContainer(new SlotPassive(inventoryPassive, k + i*(inventoryPassive.getSlots()/2), 48 + k*18 , 60 + i * 18));

        for(int i = 0;i<inventoryActive.getSlots();i++)
        {
            this.addSlotToContainer(new SlotActive(inventoryActive,i,10,10+i*18));
        }

        for(int j = 0;j<inventoryTrinket.getSlots();j++)
        {
            this.addSlotToContainer(new SlotTrinket(inventoryTrinket,j,200,40));
        }

        //Player Inventory
        for(int k = 0; k < 3; k++)
            for(int j = 0; j < 9; j++)
                this.addSlotToContainer(new Slot(inventoryPlayer, j + k * 9 + 9, 48 + j * 18, 152 + k * 18));
        //Player Hotbar
        for (int k = 0; k < 9; k++)
            this.addSlotToContainer(new Slot(inventoryPlayer, k, 48 + k * 18, 210));


    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = this.getSlot(index);
        if(slot==null || !slot.getHasStack())
            return null;

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        if(index < activeInventory.getSlots()+passiveInventory.getSlots()) //todo: When trinkets introduced include them here for count
        {
            if(!this.mergeItemStack(stack, activeInventory.getSlots()+passiveInventory.getSlots(), this.inventorySlots.size(),true))
                return null;
            slot.onSlotChanged();
        }
        else if(!this.mergeItemStack(stack,0,passiveInventory.getSlots(),true))
            return null;
        else if(!this.mergeItemStack(stack,passiveInventory.getSlots(),passiveInventory.getSlots()+activeInventory.getSlots(),true))
            return null;

        if (stack.stackSize == 0)
        {
            slot.putStack(null);
        }
        else
        {
            slot.onSlotChanged();
        }

        slot.onPickupFromSlot(playerIn,newStack);
        return newStack;
    }
}
