package com.tayjay.isaacsitems.capability;

import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketSyncPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
        private static int MAX_ITEM_COUNT = 20;
        private EntityPlayer player;
        private IItemHandler activeInv;
        private IItemHandler trinketInv;
        private IItemHandler usableInv;
        private int usableIndex;
        private IItemHandler passiveItems;

        private float redHearts;//half heart increments
        private float soulHearts;

        private int coins;
        private int keys;
        private int bombs;

        private static final String TAG_NAME_ACTIVE = "active_items";;
        private static final String TAG_NAME_TRINKET = "trinket_items";
        private static final String TAG_NAME_USABLE = "usable_items";
        private static final String TAG_NAME_PASSIVE = "passive_items";
        private static final String TAG_NAME_USABLE_INDEX = "usable_index";
        private static final String TAG_NAME_RED_HEARTS = "hearts_red";
        private static final String TAG_NAME_SOUL_HEARTS= "hearts_soul";
        private static final String TAG_NAME_COINS = "coins";
        private static final String TAG_NAME_KEYS = "keys";
        private static final String TAG_NAME_BOMBS = "bombs";

        public DefaultImpl()
        {
            this(null);
        }

        public DefaultImpl(EntityPlayer player)
        {
            this.player = player;
            activeInv = new ItemStackHandler(1);
            trinketInv = new ItemStackHandler(2);
            usableInv = new ItemStackHandler(2);
            usableIndex = 0;
            passiveItems = new ItemStackHandler(MAX_ITEM_COUNT);
            redHearts = -1;
            soulHearts = 0;
            coins = 0;
            keys = 0;
            bombs = 0;
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            NBTBase actives = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, activeInv,null);
            NBTBase passives = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, passiveItems,null);
            NBTBase trinkets = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, trinketInv,null);
            NBTBase usables = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, usableInv,null);
            tag.setTag(TAG_NAME_ACTIVE,actives);
            tag.setTag(TAG_NAME_PASSIVE, passives);
            tag.setTag(TAG_NAME_TRINKET, trinkets);
            tag.setTag(TAG_NAME_USABLE,usables);
            tag.setInteger(TAG_NAME_USABLE_INDEX,usableIndex);
            tag.setFloat(TAG_NAME_RED_HEARTS,redHearts);
            tag.setFloat(TAG_NAME_SOUL_HEARTS,soulHearts);
            tag.setInteger(TAG_NAME_COINS, coins);
            tag.setInteger(TAG_NAME_KEYS, keys);
            tag.setInteger(TAG_NAME_BOMBS, bombs);

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
            if(nbt.hasKey(TAG_NAME_ACTIVE))
            {
                IItemHandler actives = new ItemStackHandler(1);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, actives, null, nbt.getTag(TAG_NAME_ACTIVE));
                activeInv = actives;
            }

            if(nbt.hasKey(TAG_NAME_TRINKET))
            {
                IItemHandler trinkets = new ItemStackHandler(1);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, trinkets, null, nbt.getTag(TAG_NAME_TRINKET));
                trinketInv = trinkets;
            }

            if(nbt.hasKey(TAG_NAME_USABLE))
            {
                IItemHandler usables = new ItemStackHandler(1);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, usables, null, nbt.getTag(TAG_NAME_USABLE));
                usableInv = usables;
            }

            if(nbt.hasKey(TAG_NAME_PASSIVE))
            {
                IItemHandler passives = new ItemStackHandler(1);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, passives, null, nbt.getTag(TAG_NAME_PASSIVE));
                passiveItems = passives;
            }

            if(nbt.hasKey(TAG_NAME_USABLE_INDEX))
                usableIndex = nbt.getInteger(TAG_NAME_USABLE_INDEX);

            if(nbt.hasKey(TAG_NAME_RED_HEARTS))
            {
                redHearts = nbt.getInteger(TAG_NAME_RED_HEARTS);
                if(redHearts==-1)
                    redHearts = player.getMaxHealth();
            }

            if(nbt.hasKey(TAG_NAME_SOUL_HEARTS))
                soulHearts = nbt.getInteger(TAG_NAME_SOUL_HEARTS);

            if(nbt.hasKey(TAG_NAME_COINS))
                coins = nbt.getInteger(TAG_NAME_COINS);
            if(nbt.hasKey(TAG_NAME_KEYS))
                keys = nbt.getInteger(TAG_NAME_KEYS);
            if(nbt.hasKey(TAG_NAME_BOMBS))
                bombs = nbt.getInteger(TAG_NAME_BOMBS);
        }

        @Override
        public ItemStack getActiveItem()
        {
            return activeInv.getStackInSlot(0);
        }

        @Override
        public IItemHandler getActiveInv()
        {
            return activeInv;
        }

        @Override
        public IItemHandler getPassiveItems()
        {
            return passiveItems;
        }

        @Override
        public ItemStack getUsableItem()
        {
            return usableInv.getStackInSlot(usableIndex);
        }

        @Override
        public IItemHandler getUsableInv()
        {
            return usableInv;
        }

        @Override
        public ItemStack getTrinketItem()
        {
            return trinketInv.getStackInSlot(0);
        }

        @Override
        public IItemHandler getTrinketInv()
        {
            return trinketInv;
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

        }

        @Override
        public int getCoins()
        {
            return 0;
        }

        @Override
        public void addKeys(int keys)
        {

        }

        @Override
        public int getKeys()
        {
            return 0;
        }

        @Override
        public void addBombs(int bombs)
        {

        }

        @Override
        public int getBombs()
        {
            return 0;
        }

        @Override
        public void sync(EntityPlayerMP player)
        {
            NetworkHandler.sendTo(new PacketSyncPlayerData(writeNBT(),this.player),player);
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
