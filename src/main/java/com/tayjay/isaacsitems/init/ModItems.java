package com.tayjay.isaacsitems.init;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.item.*;
import com.tayjay.isaacsitems.item.actives.*;
import com.tayjay.isaacsitems.item.passives.*;
import com.tayjay.isaacsitems.item.pickups.*;
import com.tayjay.isaacsitems.item.trinkets.*;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ModItems
{

    public static List<Item> activeItems = new ArrayList<Item>();
    public static List<Item> passiveItems = new ArrayList<Item>();
    public static List<Item> trinketItems = new ArrayList<Item>();

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
    public static ItemPickup battery;
    public static ItemPickup pill;

    public static ItemActive activeNightVision;
    public static ItemActive activePocketCreeper;
    public static ItemActive activeDropPotion;
    public static ItemActive activePushUp;
    public static ItemActive activeGiveHeart;
    public static ItemActive activePotionMixer;
    public static ItemActive activeChampionSpawner;
    public static ItemActive activeDamageUp;

    public static ItemPassive passiveBlank;
    public static ItemPassive passiveFlight;
    public static ItemPassive passiveMobPacifier;
    public static ItemPassive passiveFastHands;
    public static ItemPassive passiveCure;
    public static ItemPassive passivePoisonHurt;
    public static ItemPassive passiveHungerCure;
    public static ItemPassive passivePoisonBlade;
    public static ItemPassive passiveHealingFists;
    public static ItemPassive passiveGills;
    public static ItemPassive passiveBombImmune;
    public static ItemPassive passiveHealthUp;
    public static ItemPassive passiveMuscles;
    public static ItemPassive passiveUnderArmor;
    public static ItemPassive passiveStrongArmor;
    public static ItemPassive passiveLowKnockback;
    public static ItemPassive passiveSteroids;
    //public static ItemPassive passiveRunningShoes; todo: re-add when solve issue of FOV changing on speed up.
    public static ItemPassive passiveColdFeet;
    public static ItemPassive passiveFirePick;

    public static ItemTrinket trinketBlank;
    public static ItemTrinket trinketLuckyFoot;
    public static ItemTrinket trinketXPGen;
    public static ItemTrinket trinketBattery;
    public static ItemTrinket trinketBargainingChip;


    //Initialize items in here
    public static void init()
    {

        entityProbe = register(new ItemEntityProbe("entityProbe"));

        redHeartFull = register(new ItemRedHeart("redHeartFull",true));
        redHeartHalf = register(new ItemRedHeart("redHeartHalf", false));
        soulHeart = register(new ItemSoulHeart("soulHeart"));

        penny = register(new ItemCoin("penny",1));
        nickle = register(new ItemCoin("nickle",5));
        dime = register(new ItemCoin("dime",10));
        key = register(new ItemKey("key"));
        bomb = register(new ItemBomb("bomb"));
        battery = register(new ItemBattery("battery"));
        pill = register(new ItemPill("pill"));

        activeNightVision = register(new ItemActiveNightVision("activeNightVision",3));
        activePocketCreeper = register(new ItemActivePocketCreeper("activePocketCreeper", 10));
        activeDropPotion = register(new ItemActiveDropPotion("activeDropPotion",2));
        activePushUp = register(new ItemActivePushUp("activePushUp",3));
        activeGiveHeart = register(new ItemActiveGiveHeart("activeGiveHeart", 5));
        activePotionMixer = register(new ItemActivePotionMixer("activePotionMixer",4));
        activeChampionSpawner = register(new ItemActiveChampionSpawner("activeChampionSpawner",2));
        activeDamageUp = register(new ItemActiveDamageUp("activeDamageUp", 3));

        passiveBlank = register(new ItemPassiveBlank("passiveBlank"));
        passiveFlight = register(new ItemPassiveFlight("passiveFlight"));
        passiveMobPacifier = register(new ItemPassiveMobPacifier("passiveMobPacifier"));
        passiveFastHands = register(new ItemPassiveFastHands("passiveFastHands"));
            //passiveFastHands.addModifier(new AttributeModifier(UUID.fromString("2ccae10f-e758-4b04-a94b-8e4b3ee64db3"),"Fast Hands Attack Speed Up",20.0,0), SharedMonsterAttributes.ATTACK_SPEED);
        passiveCure = register(new ItemPassiveCure("passiveCure"));
        passivePoisonHurt = register(new ItemPassivePoisonHurt("passivePoisonHurt"));
        passiveHungerCure = register(new ItemPassiveHungerCure("passiveHungerCure"));
        passivePoisonBlade = register(new ItemPassivePoisonBlade("passivePoisonBlade"));
        passiveHealingFists = register(new ItemPassiveHealingFists("passiveHealingFists"));
        passiveGills = register(new ItemPassiveGills("passiveGills"));
        passiveBombImmune = register(new ItemPassiveBombImmune("passiveBombImmune"));
        passiveHealthUp = register(new ItemPassiveStatMod("passiveHealthUp"));
            passiveHealthUp.addModifier(new AttributeModifier(UUID.fromString("1b1a7562-25ec-48a5-973a-d9e201048c24"), "isaac:passiveHealthUp", 4.0, 0), SharedMonsterAttributes.MAX_HEALTH);
        passiveMuscles = register(new ItemPassiveStatMod("passiveMuscles"));
            passiveMuscles.addModifier(new AttributeModifier(UUID.fromString("b442c2af-8894-419d-8158-a27ada864c0d"),"isaac:passiveMusclesAttack",2.0,0),SharedMonsterAttributes.ATTACK_DAMAGE);
        passiveUnderArmor = register(new ItemPassiveStatMod("passiveUnderArmor"));
            passiveUnderArmor.addModifier(new AttributeModifier(UUID.fromString("a7171542-d493-4116-a012-3f3da213be93"),"isaac:passiveUnderArmor",40.0,0),SharedMonsterAttributes.ARMOR);
        passiveStrongArmor = register(new ItemPassiveStatMod("passiveStrongArmor"));
            passiveStrongArmor.addModifier(new AttributeModifier(UUID.fromString("6acccb4f-23a0-4d36-9f90-dc4f7d5525d9"),"isaac:passiveUnderArmorToughness",2.0,0),SharedMonsterAttributes.ARMOR_TOUGHNESS);
        passiveLowKnockback = register(new ItemPassiveStatMod("passiveLowKnockback"));
            passiveLowKnockback.addModifier(new AttributeModifier(UUID.fromString("7a23e9b5-c871-4b65-9c56-c762a59bc17d"), "isaac:passiveLowKnockback", 5.0, 0), SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        passiveSteroids = register(new ItemPassiveStatMod("passiveSteroids"));
            passiveSteroids.addModifier(new AttributeModifier(UUID.fromString("ee72d199-944a-4853-97a4-b16f59f68045"), "isaac:passiveSteroidDamageMulti", 0.3, 1), SharedMonsterAttributes.ATTACK_DAMAGE);
        //passiveRunningShoes = register(new ItemPassiveStatMod("passiveRunningShoes"));
        //  passiveRunningShoes.addModifier(new AttributeModifier(UUID.fromString("253f3b31-ddec-4c3e-bc84-d7a164ada7f7"), "isaac:passiveRunningShoesSpeedUp", 0.01, 0), SharedMonsterAttributes.MOVEMENT_SPEED);
        passiveColdFeet = register(new ItemPassiveColdFeet("passiveColdFeet"));
        passiveFirePick = register(new ItemPassiveFirePick("passiveFirePick"));

        trinketBlank = register(new ItemTrinketBlank("trinketBlank"));
        trinketLuckyFoot = register(new ItemTrinketLuck("trinketLuckyFoot",1024));
        trinketXPGen = register(new ItemTrinketXPGen("trinketXPGen"));
        trinketBattery = register(new ItemTrinketBattery("trinketBattery"));
        trinketBargainingChip = register(new ItemTrinketBargainingChip("trinketBargainingChip"));

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
        if(item instanceof IActive)
            activeItems.add(item);
        if(item instanceof IPassive)
            passiveItems.add(item);
        if(item instanceof ITrinket)
            trinketItems.add(item);
        return item;
    }
}
