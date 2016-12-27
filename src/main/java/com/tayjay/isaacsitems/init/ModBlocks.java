package com.tayjay.isaacsitems.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ModBlocks
{
    //Declare blocks here
    //public static Block exampleBlock;

    //Initialize blocks
    public static void init()
    {
        //exampleBlock = register(new Block(...));
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
}
