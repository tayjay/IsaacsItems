package com.tayjay.isaacsitems.init;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.item.*;
import com.tayjay.isaacsitems.item.actives.ItemActiveDropPotion;
import com.tayjay.isaacsitems.item.actives.ItemActiveNightVision;
import com.tayjay.isaacsitems.item.actives.ItemActivePocketCreeper;
import com.tayjay.isaacsitems.item.actives.ItemActivePushUp;
import com.tayjay.isaacsitems.item.passives.*;
import com.tayjay.isaacsitems.item.pickups.*;
import com.tayjay.isaacsitems.item.trinkets.ItemTrinketBattery;
import com.tayjay.isaacsitems.item.trinkets.ItemTrinketBlank;
import com.tayjay.isaacsitems.item.trinkets.ItemTrinketLuck;
import com.tayjay.isaacsitems.item.trinkets.ItemTrinketXPGen;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ModItems
{

    //Declare Items here

    public static ItemBase entityProbe;

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
    public static ItemActive activePushUp;

    public static ItemPassive passiveBlank;
    public static ItemPassive passiveFlight;
    public static ItemPassive passiveMobPacifier;
    public static ItemPassive passiveFastHands;
    public static ItemPassive passiveCure;
    public static ItemPassive passivePoisonHurt;
    public static ItemPassive passiveHungerCure;

    public static ItemTrinket trinketBlank;
    public static ItemTrinket trinketLuckyFoot;
    public static ItemTrinket trinketXPGen;
    public static ItemTrinket trinketBattery;

    //Initialize items in here
    public static void init()
    {

        entityProbe = register(new ItemEntityProbe("entityProbe"));
        //exampleItem = register(new ItemBase(...));
        redHeartFull = register(new ItemRedHeart("redHeartFull",true));
        redHeartHalf = register(new ItemRedHeart("redHeartHalf", false));
        soulHeart = register(new ItemSoulHeart("soulHeart"));

        penny = register(new ItemCoin("penny",1));
        nickle = register(new ItemCoin("nickle",5));
        dime = register(new ItemCoin("dime",10));
        key = register(new ItemKey("key"));
        bomb = register(new ItemBomb("bomb"));

        activeNightVision = register(new ItemActiveNightVision("activeNightVision","See in the dark.",3));
        activePocketCreeper = register(new ItemActivePocketCreeper("activePocketCreeper","Little Boom.", 10));
        activeDropPotion = register(new ItemActiveDropPotion("activeDropPotion","Drops a potion around you",2));
        activePushUp = register(new ItemActivePushUp("activePushUp","I raise you up.",3));

        passiveBlank = register(new ItemPassiveBlank("passiveBlank","It does nothing."));
        passiveFlight = register(new ItemPassiveFlight("passiveFlight","I believe I can fly!"));
        passiveMobPacifier = register(new ItemPassiveMobPacifier("passiveMobPacifier","Getting the cold shoulder."));
        passiveFastHands = register(new ItemPassiveFastHands("passiveFastHands", "Quick draw."));
        passiveCure = register(new ItemPassiveCure("passiveCure","For what ails you."));
        passivePoisonHurt = register(new ItemPassivePoisonHurt("passivePoisonHurt","Right back at ya."));
        passiveHungerCure = register(new ItemPassiveHungerCure("passiveHungerCure","Mmm, Zombie Flesh."));

        trinketBlank = register(new ItemTrinketBlank("trinketBlank","Hold it, it won't bite. Or do anything."));
        trinketLuckyFoot = register(new ItemTrinketLuck("trinketLuckyFoot","+1024 Luck!!!",1024));
        trinketXPGen = register(new ItemTrinketXPGen("trinketXPGen","Gives Levels."));
        trinketBattery = register(new ItemTrinketBattery("trinketBattery","It's gonna charge you."));

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
