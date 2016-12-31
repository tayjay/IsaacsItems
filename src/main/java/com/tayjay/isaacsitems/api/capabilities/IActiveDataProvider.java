package com.tayjay.isaacsitems.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by tayjay on 2016-12-27.
 * Data stored on itemstacks for activated items. Holds charge values and such.
 */
public interface IActiveDataProvider extends INBTSerializable<NBTTagCompound>
{
    int getCurrentCharge();

    int getMaxCharge();

    void addCharge(int amount);

    boolean drainCharge();
}
