package com.tayjay.isaacsitems.capability;

import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.IStatModifier;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketSyncActiveItem;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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

/**
 * Created by tayjay on 2016-12-28.
 */
public class PlayerItemsImpl
{

    public static void init()
    {
        CapabilityManager.INSTANCE.register(IPlayerItemsProvider.class,
                new Capability.IStorage<IPlayerItemsProvider>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IPlayerItemsProvider> capability, IPlayerItemsProvider instance, EnumFacing side)
                    {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IPlayerItemsProvider> capability, IPlayerItemsProvider instance, EnumFacing side, NBTBase nbt)
                    {
                        if (nbt instanceof NBTTagCompound)
                        {
                            instance.deserializeNBT((NBTTagCompound) nbt);
                        }
                    }

                },DefaultImpl.class);
    }

    private static class DefaultImpl implements IPlayerItemsProvider
    {
        private static int MAX_ITEM_COUNT = 20;
        private EntityPlayer player;
        private IItemHandler activeInv;
        private IItemHandler trinketInv;
        private IItemHandler passiveItems;

        private static final String TAG_NAME_ACTIVE = "active_items";
        private static final String TAG_NAME_ACTIVE_ITEM_DATA = "active_item_data";
        private static final String TAG_NAME_TRINKET = "trinket_items";
        private static final String TAG_NAME_USABLE = "usable_items";
        private static final String TAG_NAME_PASSIVE = "passive_items";
        private static final String TAG_NAME_USABLE_INDEX = "usable_index";

        public DefaultImpl()
        {

        }

        public DefaultImpl(EntityPlayer player)
        {
            this.player = player;
            this.activeInv = new ItemStackHandler(1);
            this.passiveItems = new ItemStackHandler(MAX_ITEM_COUNT);
            this.trinketInv = new ItemStackHandler(1);
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
        public ArrayList<AttributeModifier> getActiveAttributeModifiers()
        {
            ArrayList<AttributeModifier> mods = new ArrayList<AttributeModifier>();
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IStatModifier)
                {
                    mods.addAll(((IStatModifier) passiveItems.getStackInSlot(i).getItem()).getStatModifiers(passiveItems.getStackInSlot(i)));
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if(trinketInv.getStackInSlot(j)!=null && trinketInv.getStackInSlot(j).getItem() instanceof IStatModifier)
                    mods.addAll(((IStatModifier) trinketInv.getStackInSlot(j).getItem()).getStatModifiers(trinketInv.getStackInSlot(j)));
            }
            return mods;
        }

        @Override
        public void syncAllItems(EntityPlayerMP player)
        {
            NetworkHandler.sendTo(new PacketSyncActiveItem(player.getEntityId(), writeNBT()), player);
        }

        @Override
        public void tickAllItems(EntityPlayerMP player)
        {
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IPassive)
                {
                    ((IPassive) passiveItems.getStackInSlot(i).getItem()).doTickAction(passiveItems.getStackInSlot(i),player);
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if(trinketInv.getStackInSlot(j)!=null && trinketInv.getStackInSlot(j).getItem() instanceof ITrinket)
                    ((ITrinket) trinketInv.getStackInSlot(j).getItem()).tickTrinket(trinketInv.getStackInSlot(j),player);
            }

            for(int k = 0;k<activeInv.getSlots();k++)
            {
                if (activeInv.getStackInSlot(k) != null && activeInv.getStackInSlot(k).getItem() instanceof IActive)
                {
                    ((IActive) activeInv.getStackInSlot(k).getItem()).tickActive(activeInv.getStackInSlot(k), player);
                }
            }
        }

        public NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            NBTBase actives = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, activeInv,null);
            NBTBase passives = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, passiveItems,null);
            NBTBase trinkets = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, trinketInv,null);

            tag.setTag(TAG_NAME_ACTIVE,actives);
            tag.setTag(TAG_NAME_PASSIVE, passives);
            tag.setTag(TAG_NAME_TRINKET, trinkets);


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

            if(nbt.hasKey(TAG_NAME_PASSIVE))
            {
                IItemHandler passives = new ItemStackHandler(MAX_ITEM_COUNT);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, passives, null, nbt.getTag(TAG_NAME_PASSIVE));
                passiveItems = passives;
            }
        }
    }


    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("isaacsitems","player_items");
        private final IPlayerItemsProvider cap;

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
            return capability == IsaacAPI.PLAYER_ITEM_PROVIDER;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing enumFacing)
        {
            if(capability == IsaacAPI.PLAYER_ITEM_PROVIDER)
                return IsaacAPI.PLAYER_ITEM_PROVIDER.cast(cap);
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
}
