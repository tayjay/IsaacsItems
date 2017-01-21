package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
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
    public ItemActiveNightVision(String name, int charges)
    {
        super(name, charges);
    }

    @Override
    public void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        player.addPotionEffect(nightVisionEffect);
    }


}
