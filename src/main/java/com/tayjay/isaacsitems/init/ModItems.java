package com.tayjay.isaacsitems.init;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.item.*;
import com.tayjay.isaacsitems.item.actives.ItemActiveDropPotion;
import com.tayjay.isaacsitems.item.actives.ItemNightVision;
import com.tayjay.isaacsitems.item.actives.ItemPocketCreeper;
import com.tayjay.isaacsitems.item.passives.*;
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
    public static ItemActive activeDropPotion;

    public static ItemPassive passiveBlank;
    public static ItemPassive passiveFlight;
    public static ItemPassive passiveMobPacifier;
    public static ItemPassive passiveFastHands;
    public static ItemPassive passiveCure;

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
        activeDropPotion = register(new ItemActiveDropPotion("activeDropPotion","Drops a potion around you",2));

        passiveBlank = register(new ItemPassiveBlank("passiveBlank","It does nothing."));
        passiveFlight = register(new ItemPassiveFlight("passiveFlight","I believe I can fly!"));
        passiveMobPacifier = register(new ItemPassiveMobPacifier("passiveMobPacifier","Getting the cold shoulder."));
        passiveFastHands = register(new ItemPassiveFastHands("passiveFastHands", "Do everything faster."));
        passiveCure = register(new ItemPassiveCure("passiveCure","For what ails you."));



        trinketBlank = register(new ItemTrinketBlank("trinketBlank","Hold it, it won't bite. Or do anything."));
        trinketLuckyFoot = register(new ItemTrinketLuck("trinketLuckyFoot","+4 Luck!!!",4));
    }

    //Register item with game and give it an item model
    private static <T extends Item> T register(T item)
    {
        System.out.println("Registering "+item.getUnlocalizedName());
        String comparing;
        for(int i = 0; i< IsaacsItems.blacklistItems.length;i++)
        {
            comparing = item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(":")+1);
            if(IsaacsItems.blacklistItems[i].equals(comparing))
                return null;
        }

        GameRegistry.register(item);
        if (item instanceof IItemModelProvider)
        {
            ((IItemModelProvider) item).registerItemModel(item);
        }
        return item;
    }
}
