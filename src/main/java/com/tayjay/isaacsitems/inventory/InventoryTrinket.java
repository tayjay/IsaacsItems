package com.tayjay.isaacsitems.inventory;

import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.ITrinket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Created by tayjay on 2016-12-30.
 */
public class InventoryTrinket implements IItemHandlerModifiable
{
    public final EntityPlayer player;
    private final IItemHandlerModifiable compose;
    private final IPlayerItemsProvider provider;

    public InventoryTrinket(IPlayerItemsProvider provider, EntityPlayer player)
    {
        this.player = player;
        this.compose = (IItemHandlerModifiable) provider.getTrinketInv();
        this.provider = provider;
    }
    @Override
    public void setStackInSlot(int i, ItemStack itemStack)
    {
        compose.setStackInSlot(i,itemStack);
    }

    @Override
    public int getSlots()
    {
        return compose.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        if(compose.getStackInSlot(i) !=null)
            return compose.getStackInSlot(i);
        return null;
    }

    @Override
    public ItemStack insertItem(int i, ItemStack itemStack, boolean b)
    {
        if(!(itemStack.getItem() instanceof ITrinket))
            return itemStack; //Prevent adding of non passive items
        else
            return  compose.insertItem(i,itemStack,b);
    }

    @Override
    public ItemStack extractItem(int i, int i1, boolean b)
    {
        return compose.extractItem(i,i1,b);
    }
}
