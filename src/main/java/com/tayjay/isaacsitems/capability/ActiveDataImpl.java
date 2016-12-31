package com.tayjay.isaacsitems.capability;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IActiveDataProvider;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ActiveDataImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IActiveDataProvider.class,
                new Capability.IStorage<IActiveDataProvider>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IActiveDataProvider> capability, IActiveDataProvider instance, EnumFacing side)
                    {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IActiveDataProvider> capability, IActiveDataProvider instance, EnumFacing side, NBTBase nbt)
                    {
                        if (nbt instanceof NBTTagCompound)
                        {
                            instance.deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },DefaultImpl.class);
    }

    private static class DefaultImpl implements IActiveDataProvider
    {
        private int currentCharge;
        private int maxCharge;

        private static final String TAG_NAME_CURRENT = "current_charge";
        private static final String TAG_NAME_MAX = "max_charge";

        public DefaultImpl()
        {
            this(0);
        }

        public DefaultImpl(int charge)
        {
            currentCharge=charge;
            maxCharge = charge;
        }

        @Override
        public int getCurrentCharge()
        {
            return currentCharge;
        }

        @Override
        public int getMaxCharge()
        {
            return maxCharge;
        }

        @Override
        public void addCharge(int amount)
        {
            currentCharge+=amount;
            if(currentCharge>=maxCharge)
                currentCharge = maxCharge;
            if(currentCharge<0)
                currentCharge = 0;
        }

        @Override
        public boolean drainCharge()
        {
            if(currentCharge>=maxCharge)
            {
                currentCharge=0;
                System.out.println("Draining energy.");
                return true;
            }
            System.out.println("Failed to drain energy.");
            return false;
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger(TAG_NAME_CURRENT, currentCharge);
            tag.setInteger(TAG_NAME_MAX, maxCharge);
            return tag;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return writeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            if(nbt.hasKey(TAG_NAME_CURRENT))
                currentCharge = nbt.getInteger(TAG_NAME_CURRENT);
            if (nbt.hasKey(TAG_NAME_MAX))
            {
                maxCharge = nbt.getInteger(TAG_NAME_MAX);
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation(IsaacsItems.modId.toLowerCase(),"active_data");
        private final IActiveDataProvider cap;

        public Provider()
        {
            cap = new DefaultImpl();
        }

        public Provider(int charges)
        {
            cap = new DefaultImpl(charges);
        }




        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == IsaacAPI.ACTIVE_DATA_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == IsaacAPI.ACTIVE_DATA_CAPABILITY)
                return IsaacAPI.ACTIVE_DATA_CAPABILITY.cast(cap);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            cap.deserializeNBT(nbt);
        }
    }
    private ActiveDataImpl(){}
}
