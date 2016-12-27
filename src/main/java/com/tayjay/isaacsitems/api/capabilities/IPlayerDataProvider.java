package com.tayjay.isaacsitems.api.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-26.
 */
public interface IPlayerDataProvider extends INBTSerializable<NBTTagCompound>
{
    /**
     * These may be saved as separate inventories but these methods will get the selected one from each.
     * @return
     */
    ItemStack getActiveItem();

    IItemHandler getActiveInv();

    IItemHandler getPassiveItems();

    ItemStack getUsableItem();

    IItemHandler getUsableInv();

    ItemStack getTrinketItem();

    IItemHandler getTrinketInv();

    /**
     * Number of red heart canisters the player can have
     * @return
     */
    float getRedHearts();

    void changeRedHearts(float changeBy);

    /**
     * Number of sould hearts the player is holding.
     * @return
     */
    float getSoulHearts();

    void changeSoulHearts(float changeBy);

    //todo: May rename these to other items that make more sence in minecraft
    void addCoins(int coins);

    int getCoins();

    void addKeys(int keys);

    int getKeys();

    void addBombs(int bombs);

    int getBombs();
    //**************************

    void sync(EntityPlayerMP player);
}
