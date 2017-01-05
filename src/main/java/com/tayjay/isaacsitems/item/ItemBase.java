package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.init.IItemModelProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ItemBase extends Item implements IItemModelProvider
{
    protected String name;

    public ItemBase(String name)
    {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(IsaacsItems.isaacsItemsTab);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s",IsaacsItems.modId.toLowerCase()+":",getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return String.format("item.%s%s",IsaacsItems.modId.toLowerCase()+":",getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

    @Override
    public void registerItemModel(Item item)
    {
        IsaacsItems.proxy.registerItemRenderer(this,0,name);
    }
}
