package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.item.ItemActive;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ItemActiveNightVision extends ItemActive
{
    private final PotionEffect nightVisionEffect = new PotionEffect(Potion.getPotionById(16),1200,0);
    public ItemActiveNightVision(String name, String description, int charges)
    {
        super(name,description, charges);
    }

    @Override
    public void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        player.addPotionEffect(nightVisionEffect);
    }


}
