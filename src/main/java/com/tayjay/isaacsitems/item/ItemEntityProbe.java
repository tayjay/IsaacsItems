package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.lib.Buffs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2017-01-05.
 */
public class ItemEntityProbe extends ItemBase
{
    public ItemEntityProbe(String name)
    {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(worldIn.isRemote)
        {
            try
            {


                Entity e = Minecraft.getMinecraft().objectMouseOver.entityHit;
                if (e != null && e instanceof EntityLiving && e.getDataManager().get(Buffs.ENTITY_CHAMPION_TYPE) != null)
                {
                    playerIn.addChatMessage(new TextComponentString("Champion Type: " + e.getDataManager().get(Buffs.ENTITY_CHAMPION_TYPE)));
                    playerIn.addChatMessage(new TextComponentString("Entity Health: " + ((EntityLiving) e).getHealth()+"/"+((EntityLiving) e).getMaxHealth()));

                }
            } catch (NullPointerException e)
            {

            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
