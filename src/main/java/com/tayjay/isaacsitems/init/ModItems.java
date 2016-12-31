package com.tayjay.isaacsitems.init;

import com.tayjay.isaacsitems.item.*;
import com.tayjay.isaacsitems.item.actives.ItemNightVision;
import com.tayjay.isaacsitems.item.actives.ItemPocketCreeper;
import com.tayjay.isaacsitems.item.passives.ItemPassiveBlank;
import com.tayjay.isaacsitems.item.pickups.*;
import com.tayjay.isaacsitems.item.trinkets.ItemTrinketBlank;
import com.tayjay.isaacsitems.item.trinkets.ItemTrinketLuck;
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

    public static ItemPickup penny;
    public static ItemPickup nickle;
    public static ItemPickup dime;
    public static ItemPickup key;
    public static ItemPickup bomb;

    public static ItemActive activeNightVision;
    public static ItemActive activePocketCreeper;

    public static ItemPassive passiveBlank;

    public static ItemTrinket trinketBlank;
    public static ItemTrinket trinketLuckyFoot;

    //Initialize items in here
    public static void init()
    {
        //exampleItem = register(new ItemBase(...));
        redHeartFull = register(new ItemRedHeart("redHeartFull",true));
        redHeartHalf = register(new ItemRedHeart("redHeartHalf", false));
        soulHeart = register(new ItemSoulHeart("soulHeart"));

        penny = register(new ItemCoin("penny",1));
        nickle = register(new ItemCoin("nickle",5));
        dime = register(new ItemCoin("dime",10));
        key = register(new ItemKey("key"));
        bomb = register(new ItemBomb("bomb"));

        activeNightVision = register(new ItemNightVision("activeNightVision","See in the dark.",3));
        activePocketCreeper = register(new ItemPocketCreeper("activePocketCreeper","Little Boom.", 10));

        passiveBlank = register(new ItemPassiveBlank("passiveBlank","It does nothing."));

        trinketBlank = register(new ItemTrinketBlank("trinketBlank","Hold it, it won't bite. Or do anything."));
        trinketLuckyFoot = register(new ItemTrinketLuck("trinketLuckyFoot","+2 Luck",2));
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
