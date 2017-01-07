package com.tayjay.isaacsitems.block;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.block.tileentity.TileEntityBase;
import com.tayjay.isaacsitems.init.IItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2017-01-05.
 */
public class BlockBase extends Block implements IItemModelProvider, ITileEntityProvider
{
    protected String name;

    public BlockBase(Material blockMaterialIn, String name)
    {
        super(blockMaterialIn);
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(IsaacsItems.isaacsItemsTab);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", IsaacsItems.modId.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void registerItemModel(Item item)
    {
        IsaacsItems.proxy.registerItemRenderer(item, 0, name);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBase();
    }
}
