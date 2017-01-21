package com.tayjay.isaacsitems.capability;

import com.tayjay.isaacsitems.api.IsaacAPI;
import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.events.*;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.IPassive;
import com.tayjay.isaacsitems.api.item.IStatModifier;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.lib.AttributeModifierPair;
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
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
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
        public ArrayList<AttributeModifierPair> getActiveAttributeModifiers()
        {
            ArrayList<AttributeModifierPair> mods = new ArrayList<AttributeModifierPair>();
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

        @Override
        public void activateHurtItems(LivingHurtEvent event)
        {
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IHurtItem)
                {
                    ((IHurtItem) passiveItems.getStackInSlot(i).getItem()).onHurtEvent(event);
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if(trinketInv.getStackInSlot(j)!=null && trinketInv.getStackInSlot(j).getItem() instanceof IHurtItem)
                    ((IHurtItem) trinketInv.getStackInSlot(j).getItem()).onHurtEvent(event);
            }

            for(int k = 0;k<activeInv.getSlots();k++)
            {
                if (activeInv.getStackInSlot(k) != null && activeInv.getStackInSlot(k).getItem() instanceof IHurtItem)
                {
                    ((IHurtItem) activeInv.getStackInSlot(k).getItem()).onHurtEvent(event);
                }
            }
        }

        @Override
        public void activateAttackItems(LivingAttackEvent event)
        {
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IAttackItem)
                {
                    ((IAttackItem) passiveItems.getStackInSlot(i).getItem()).onAttackEvent(event);
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if(trinketInv.getStackInSlot(j)!=null && trinketInv.getStackInSlot(j).getItem() instanceof IAttackItem)
                    ((IAttackItem) trinketInv.getStackInSlot(j).getItem()).onAttackEvent(event);
            }

            for(int k = 0;k<activeInv.getSlots();k++)
            {
                if (activeInv.getStackInSlot(k) != null && activeInv.getStackInSlot(k).getItem() instanceof IAttackItem)
                {
                    ((IAttackItem) activeInv.getStackInSlot(k).getItem()).onAttackEvent(event);
                }
            }
        }

        @Override
        public void activateContainerChangeItems(PlayerContainerEvent event)
        {
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IChangeContainerItem)
                {
                    if(event instanceof PlayerContainerEvent.Open)
                        ((IChangeContainerItem) passiveItems.getStackInSlot(i).getItem()).onOpenContainer((PlayerContainerEvent.Open) event);
                    else if(event instanceof PlayerContainerEvent.Close)
                        ((IChangeContainerItem) passiveItems.getStackInSlot(i).getItem()).onCloseContainer((PlayerContainerEvent.Close) event);
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if (trinketInv.getStackInSlot(j) != null && trinketInv.getStackInSlot(j).getItem() instanceof IChangeContainerItem)
                {
                    if (event instanceof PlayerContainerEvent.Open)
                        ((IChangeContainerItem) trinketInv.getStackInSlot(j).getItem()).onOpenContainer((PlayerContainerEvent.Open) event);
                    else if (event instanceof PlayerContainerEvent.Close)
                        ((IChangeContainerItem) trinketInv.getStackInSlot(j).getItem()).onCloseContainer((PlayerContainerEvent.Close) event);

                }
            }

            for(int k = 0;k<activeInv.getSlots();k++)
            {
                if (activeInv.getStackInSlot(k) != null && activeInv.getStackInSlot(k).getItem() instanceof IChangeContainerItem)
                {
                    if (event instanceof PlayerContainerEvent.Open)
                        ((IChangeContainerItem) activeInv.getStackInSlot(k).getItem()).onOpenContainer((PlayerContainerEvent.Open) event);
                    else if (event instanceof PlayerContainerEvent.Close)
                        ((IChangeContainerItem) activeInv.getStackInSlot(k).getItem()).onCloseContainer((PlayerContainerEvent.Close) event);
                }
            }
        }

        @Override
        public void activateBreakSpeedItems(PlayerEvent.BreakSpeed event)
        {
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IBreakSpeedItem)
                {
                    ((IBreakSpeedItem) passiveItems.getStackInSlot(i).getItem()).checkBreakSpeed(event);
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if(trinketInv.getStackInSlot(j)!=null && trinketInv.getStackInSlot(j).getItem() instanceof IBreakSpeedItem)
                    ((IBreakSpeedItem) trinketInv.getStackInSlot(j).getItem()).checkBreakSpeed(event);
            }

            for(int k = 0;k<activeInv.getSlots();k++)
            {
                if (activeInv.getStackInSlot(k) != null && activeInv.getStackInSlot(k).getItem() instanceof IBreakSpeedItem)
                {
                    ((IBreakSpeedItem) activeInv.getStackInSlot(k).getItem()).checkBreakSpeed(event);
                }
            }
        }

        @Override
        public void activateBlockHarvestItems(BlockEvent.HarvestDropsEvent event)
        {
            for(int i = 0;i<passiveItems.getSlots();i++)
            {
                if (passiveItems.getStackInSlot(i) != null && passiveItems.getStackInSlot(i).getItem() instanceof IBlockHarvestItem)
                {
                    ((IBlockHarvestItem) passiveItems.getStackInSlot(i).getItem()).onBlockHarvest(event);
                }
            }

            for(int j = 0;j<trinketInv.getSlots();j++)
            {
                if(trinketInv.getStackInSlot(j)!=null && trinketInv.getStackInSlot(j).getItem() instanceof IBlockHarvestItem)
                    ((IBlockHarvestItem) trinketInv.getStackInSlot(j).getItem()).onBlockHarvest(event);
            }

            for(int k = 0;k<activeInv.getSlots();k++)
            {
                if (activeInv.getStackInSlot(k) != null && activeInv.getStackInSlot(k).getItem() instanceof IBlockHarvestItem)
                {
                    ((IBlockHarvestItem) activeInv.getStackInSlot(k).getItem()).onBlockHarvest(event);
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
