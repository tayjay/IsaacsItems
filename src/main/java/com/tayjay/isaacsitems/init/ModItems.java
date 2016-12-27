package com.tayjay.isaacsitems.init;

import com.tayjay.isaacsitems.item.ItemHeart;
import com.tayjay.isaacsitems.item.pickups.ItemRedHeart;
import com.tayjay.isaacsitems.item.pickups.ItemSoulHeart;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ModItems
{
    //Declare Items here
    //public static ItemBase exampleItem;
    public static ItemHeart redHeartFull;
    public static ItemHeart redHeartHalf;
    public static ItemHeart soulHeart;

    //Initialize items in here
    public static void init()
    {
        //exampleItem = register(new ItemBase(...));
        redHeartFull = register(new ItemRedHeart("redHeartFull",true));
        redHeartHalf = register(new ItemRedHeart("redHeartHalf", false));
        soulHeart = register(new ItemSoulHeart("soulHeart"));
    }

    //Register item with game and give it an item model
    private static <T extends Item> T register(T item)
    {
        GameRegistry.register(item);
        if (item instanceof IItemModelProvider)
        {
            ((IItemModelProvider) item).registerItemModel(item);
        }
        return item;
    }
}
