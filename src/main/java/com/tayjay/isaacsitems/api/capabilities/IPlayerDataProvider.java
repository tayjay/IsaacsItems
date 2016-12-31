package com.tayjay.isaacsitems.api.capabilities;

import net.minecraft.entity.ai.attributes.AttributeModifier;
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

    //Breaking up sync operations to avoid conflicts
    void sync(EntityPlayerMP player);



}
