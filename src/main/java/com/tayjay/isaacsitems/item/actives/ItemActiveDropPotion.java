package com.tayjay.isaacsitems.item.actives;

import com.tayjay.isaacsitems.item.ItemActive;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;

/**
 * Created by tayjay on 2017-01-02.
 */
public class ItemActiveDropPotion extends ItemActive
{
    public ItemActiveDropPotion(String name, String description, int charges)
    {
        super(name, description, charges);
    }

    @Override
    protected void onActivateDoAction(ItemStack stack, EntityPlayer player)
    {
        ItemStack potionStack = new ItemStack(Items.LINGERING_POTION,1);
        PotionEffect potioneffect = new PotionEffect(Potion.getPotionById(8),2000);
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(player.worldObj, player.posX, player.posY, player.posZ);
        entityareaeffectcloud.setOwner(player);
        entityareaeffectcloud.setRadius(8.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        //entityareaeffectcloud.setPotion(potiontype);

        entityareaeffectcloud.addEffect(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration(), potioneffect.getAmplifier()));

        /*for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromItem(itemstack))
        {
            entityareaeffectcloud.addEffect(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration(), potioneffect.getAmplifier()));
        }*/

        player.worldObj.spawnEntityInWorld(entityareaeffectcloud);
    }
}
