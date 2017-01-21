package com.tayjay.isaacsitems.item;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.util.CapHelper;
import com.tayjay.isaacsitems.util.ItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by tayjay on 2016-12-27.
 */
public abstract class ItemActive extends ItemPickup implements IActive
{
    protected final int CHARGES;

    public ItemActive(String name, int charges)
    {
        super(name);
        this.CHARGES = charges;
        this.setMaxDamage(charges);
        this.setMaxStackSize(1);
        this.setCreativeTab(IsaacsItems.activeItemsTab);
    }

    @Override
    public boolean isDamageable()
    {
        return true;
    }

    @Override
    public int getMetadata(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    /*@Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new ActiveDataImpl.Provider(getMaxCharge(stack));
    }*/

    @Override
    public boolean onPickup(ItemStack stack, EntityPlayer player)
    {
        if(player.worldObj.isRemote)
            return false;
        return onPickupActive(stack,player);
    }

    @Override
    public boolean onPickupActive(ItemStack stack, EntityPlayer player)
    {
        return ItemHelper.tryAddItemToItemHandlerPlayer(stack,CapHelper.getPlayerItemsCap(player).getActiveInv(),player,true);
        /*IPlayerItemsProvider playerItemsCap = CapHelper.getPlayerItemsCap(player);
        if(playerItemsCap.getActiveItem()==null)
        {
            playerItemsCap.getActiveInv().insertItem(0, stack, false);
        }
        else
        {
            player.dropItem(playerItemsCap.getActiveInv().extractItem(0,1,false),false,true);
            playerItemsCap.getActiveInv().insertItem(0, stack, false);
        }
        playerItemsCap.syncActiveItem((EntityPlayerMP) player);
        //playerItemsCap.syncActiveItem((EntityPlayerMP) player);
        //NetworkHandler.sendTo(new PacketSyncActiveItem(player.getEntityId(),playerItemsCap.serializeNBT()), (EntityPlayerMP) player);*/
    }

    @Override
    public void onActivate(ItemStack stack, EntityPlayer player)
    {
        if(drainCharge(stack) && !player.worldObj.isRemote)
            onActivateDoAction(stack,player);
    }

    protected abstract void onActivateDoAction(ItemStack stack, EntityPlayer player);

    @Override
    public void tickActive(ItemStack stack, EntityPlayer player)
    {
        /*if (player.worldObj.isRemote)
        {
            return;
        }
        if (player.worldObj.getTotalWorldTime() % 40 == 0)
        {
            this.addCharge(stack);
        }*/
    }

    @Override
    public int getMaxCharge(ItemStack stack)
    {
        return this.CHARGES;
    }

    @Override
    public int getCurrentCharge(ItemStack stack)
    {
        return stack.getMetadata();
        //return CapHelper.getActiveItemDataCap(stack).getCurrentCharge();
    }

    @Override
    public void addCharge(ItemStack stack)
    {
        if(stack.getMetadata()<stack.getMaxDamage())
        {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
        //CapHelper.getActiveItemDataCap(stack).addCharge(1);
    }

    @Override
    public boolean drainCharge(ItemStack stack)
    {
        if(stack.getMetadata()==stack.getMaxDamage())
        {
            stack.setItemDamage(stack.getMaxDamage());
            return true;
        }
        return false;
        //return CapHelper.getActiveItemDataCap(stack).drainCharge();
    }

    @Override
    public void renderInGUI(ItemStack stack)
    {
        Minecraft mc = Minecraft.getMinecraft();
        //Draw charge bar
        if(stack!=null)
        {
            IActive activeItem = (IActive) stack.getItem();
            if(activeItem!=null)
            {
                double currentCharge = activeItem.getCurrentCharge(stack);
                double maxCharge = activeItem.getMaxCharge(stack);

                GL11.glPushMatrix();
                Gui.drawRect(7,7,34,34,new Color(1,1,1,0.2f).getRGB());

                mc.fontRendererObj.drawString("Current Charge:" + currentCharge, 28, 78, Color.WHITE.getRGB());
                mc.fontRendererObj.drawString("Max Charge: "+maxCharge,28,86,Color.WHITE.getRGB());

                GL11.glPopMatrix();

                GL11.glPushMatrix();

                //GL11.glRotatef(180,0,0,1);
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(IsaacsItems.modId.toLowerCase(), "textures/gui/itemChargeBarBlank.png"));
                Gui.drawModalRectWithCustomSizedTexture(10, 5, 0, 0, 32, 32, 32, 32);

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(IsaacsItems.modId.toLowerCase(), "textures/gui/itemChargeBarFill.png"));
                Gui.drawModalRectWithCustomSizedTexture(10, (37 - (int) Math.floor(32 * (currentCharge / maxCharge))), 0, 0, 32, (int) Math.floor(32 * (currentCharge / maxCharge)), 32, 32);

                GL11.glPopMatrix();


                GL11.glPushMatrix();

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(IsaacsItems.modId.toLowerCase(), "textures/gui/itemChargeBarEmpty.png"));
                Gui.drawModalRectWithCustomSizedTexture(10, 5, 0, 0, 32, 32, 32, 32);


                GL11.glPopMatrix();

                GL11.glPushMatrix();
                for (int i = 1; i < maxCharge ; i++)
                {
                    Gui.drawRect(37,  5+(int) Math.floor(32 * (i / maxCharge)), 41, 5+ (int) Math.floor(32 * (i / maxCharge)) + 1, Color.BLACK.getRGB());
                }
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glScaled(1.6, 1.6, 0);
                RenderHelper.enableGUIStandardItemLighting();
                mc.getRenderItem().renderItemIntoGUI(stack, 5, 5);
                GL11.glPopMatrix();
            }
        }
    }
}
