package com.tayjay.isaacsitems.capability;

import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.lib.TimedAttributeModifier;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketSyncPlayerData;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjay on 2016-12-26.
 */
public class PlayerDataImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IPlayerDataProvider.class,
                new Capability.IStorage<IPlayerDataProvider>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IPlayerDataProvider> capability, IPlayerDataProvider iPlayerDataProvider, EnumFacing enumFacing)
                    {
                        return iPlayerDataProvider.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IPlayerDataProvider> capability, IPlayerDataProvider iPlayerDataProvider, EnumFacing enumFacing, NBTBase nbtBase)
                    {
                        if(nbtBase instanceof NBTTagCompound)
                            iPlayerDataProvider.deserializeNBT((NBTTagCompound) nbtBase);
                    }
                },DefaultImpl.class);
    }

    private static class DefaultImpl implements IPlayerDataProvider
    {

        private EntityPlayer player;
        private float redHearts;//half heart increments
        private float soulHearts;

        private int coins;
        private int keys;
        private int bombs;

        List<TimedAttributeModifier> timedModifiers;


        private static final String TAG_NAME_RED_HEARTS = "hearts_red";
        private static final String TAG_NAME_SOUL_HEARTS= "hearts_soul";
        private static final String TAG_NAME_COINS = "coins";
        private static final String TAG_NAME_KEYS = "keys";
        private static final String TAG_NAME_BOMBS = "bombs";
        private static final String TAG_NAME_TIMED_MODIFIERS = "timed_modifiers";

        public DefaultImpl()
        {
            this(null);
        }

        public DefaultImpl(EntityPlayer player)
        {
            this.player = player;

            redHearts = 20;
            soulHearts = 0;
            coins = 0;
            keys = 0;
            bombs = 0;

            timedModifiers = new ArrayList<TimedAttributeModifier>();
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();

            tag.setFloat(TAG_NAME_RED_HEARTS,redHearts);
            tag.setFloat(TAG_NAME_SOUL_HEARTS,soulHearts);
            tag.setInteger(TAG_NAME_COINS, coins);
            tag.setInteger(TAG_NAME_KEYS, keys);
            tag.setInteger(TAG_NAME_BOMBS, bombs);
            NBTTagList modList = new NBTTagList();
            for (TimedAttributeModifier modifier : timedModifiers)
            {
                NBTTagCompound modNBT = modifier.serializeNBT();
                modList.appendTag(modNBT);
            }
            tag.setTag(TAG_NAME_TIMED_MODIFIERS,modList);

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
            if(nbt.hasKey(TAG_NAME_RED_HEARTS))
                redHearts = nbt.getInteger(TAG_NAME_RED_HEARTS);
            if(nbt.hasKey(TAG_NAME_SOUL_HEARTS))
                soulHearts = nbt.getInteger(TAG_NAME_SOUL_HEARTS);

            if(nbt.hasKey(TAG_NAME_COINS))
                coins = nbt.getInteger(TAG_NAME_COINS);
            if(nbt.hasKey(TAG_NAME_KEYS))
                keys = nbt.getInteger(TAG_NAME_KEYS);
            if(nbt.hasKey(TAG_NAME_BOMBS))
                bombs = nbt.getInteger(TAG_NAME_BOMBS);

            if (nbt.hasKey(TAG_NAME_TIMED_MODIFIERS))
            {
                NBTTagList tagList = nbt.getTagList(TAG_NAME_TIMED_MODIFIERS, 10);
                for(int i = 0;i<tagList.tagCount();i++)
                {
                    if (tagList.getCompoundTagAt(i) != null)
                    {
                        TimedAttributeModifier timedAttributeModifier = new TimedAttributeModifier(null, null,0, this.player);
                        timedAttributeModifier.deserializeNBT(tagList.getCompoundTagAt(i));
                        timedModifiers.add(timedAttributeModifier);
                    }
                }
            }
        }

        @Override
        public float getRedHearts()
        {
            return redHearts;
        }

        @Override
        public void changeRedHearts(float changeBy)
        {
            redHearts+=changeBy;
        }

        @Override
        public float getSoulHearts()
        {
            return soulHearts;
        }

        @Override
        public void changeSoulHearts(float changeBy)
        {
            soulHearts+=changeBy;
        }

        @Override
        public void addCoins(int coins)
        {
            this.coins+=coins;
        }

        @Override
        public int getCoins()
        {
            return coins;
        }

        @Override
        public void addKeys(int keys)
        {
            this.keys += keys;
        }

        @Override
        public int getKeys()
        {
            return keys;
        }

        @Override
        public void addBombs(int bombs)
        {
            this.bombs += bombs;
        }

        @Override
        public int getBombs()
        {
            return bombs;
        }

        @Override
        public void sync(EntityPlayerMP player)
        {
            NetworkHandler.sendTo(new PacketSyncPlayerData(writeNBT(),this.player),player);
        }

        @Override
        public List<TimedAttributeModifier> getTimedModifiers()
        {
            return this.timedModifiers;
        }

        @Override
        public void addTimedModifier(TimedAttributeModifier timedAttributeModifier)
        {
            this.timedModifiers.add(timedAttributeModifier);
        }

        @Override
        public void removeTimedModifier(TimedAttributeModifier timedAttributeModifier)
        {
            if (this.timedModifiers.contains(timedAttributeModifier))
            {
                this.timedModifiers.remove(timedAttributeModifier);
            }
        }

        @Override
        public void tickTimedAttributeModifiers()
        {
            for (Object mod : timedModifiers.toArray())
            {
                TimedAttributeModifier timedMod =  (TimedAttributeModifier)mod;
                timedMod.tickAttribute();
                if(timedMod.ticksRemaining<0)
                    timedModifiers.remove(timedMod);
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("isaacsitems","player_data");
        private final IPlayerDataProvider cap;

        public Provider()
        {
            cap = new DefaultImpl();
        }

        public Provider(EntityPlayer player)
        {
            cap = new DefaultImpl(player);
        }


        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing enumFacing)
        {
            return capability == IsaacAPI.PLAYER_DATA_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing enumFacing)
        {
            if(capability == IsaacAPI.PLAYER_DATA_CAPABILITY)
                return IsaacAPI.PLAYER_DATA_CAPABILITY.cast(cap);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound tagCompound)
        {
            cap.deserializeNBT(tagCompound);
        }
    }
    private PlayerDataImpl(){}
}
