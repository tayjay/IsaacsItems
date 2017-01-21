package com.tayjay.isaacsitems.api.capabilities;

import com.tayjay.isaacsitems.lib.AttributeModifierPair;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-12-28.
 */
public interface IPlayerItemsProvider extends INBTSerializable<NBTTagCompound>
{

    /**
     * These may be saved as separate inventories but these methods will get the selected one from each.
     * @return
     */
    ItemStack getActiveItem();

    IItemHandler getActiveInv();

    IItemHandler getPassiveItems();

    ItemStack getTrinketItem();

    IItemHandler getTrinketInv();

    /**
     * Get all mod attribute modifiers what should be active on the player.
     * Go through all items and check if that modifier is present.
     * If it is not in this list, it should be removed from the player's modifiers.
     * @return List of all attribute modifiers that should be applied to the player.
     */
    ArrayList<AttributeModifierPair> getActiveAttributeModifiers();

    void syncAllItems(EntityPlayerMP player);

    void tickAllItems(EntityPlayerMP player);

    /**
     * Run all IHurtItem items on the player.
     * @return
     * @param event
     */
    void activateHurtItems(LivingHurtEvent event);

    void activateAttackItems(LivingAttackEvent event);

    void activateContainerChangeItems(PlayerContainerEvent event);

    void activateBreakSpeedItems(PlayerEvent.BreakSpeed event);

    void activateBlockHarvestItems(BlockEvent.HarvestDropsEvent event);
}
