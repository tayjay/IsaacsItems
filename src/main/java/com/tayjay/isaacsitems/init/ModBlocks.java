package com.tayjay.isaacsitems.init;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.block.BlockBase;
import com.tayjay.isaacsitems.block.BlockPedestal;
import com.tayjay.isaacsitems.block.tileentity.TileEntityPedestal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ModBlocks
{

    //Declare blocks here
    //public static Block exampleBlock;
    public static BlockBase blockBase;
    public static BlockBase blockPedestal;

    //Initialize blocks
    public static void init()
    {
        //exampleBlock = register(new Block(...));
        blockBase = register(new BlockBase(Material.ROCK,"blockBase"));
        blockPedestal = register(new BlockPedestal("blockPedestal"));

        initTileEntities();
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock)
    {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if(block instanceof IItemModelProvider)
        {
            ((IItemModelProvider)block).registerItemModel(itemBlock);
        }

        return block;
    }

    private static <T extends Block> T register(T block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block,itemBlock);
    }

    private static void initTileEntities()
    {
        registerTile(TileEntityPedestal.class,"blockPedestal");
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, key);
    }
}
