package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.capabilities.IActiveDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.init.ModItems;
import com.tayjay.isaacsitems.lib.Buffs;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.animation.Animation;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opencl.CL;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by tayjay on 2016-12-27.
 */
public class ClientEventHandler
{

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderScreenElements(RenderGameOverlayEvent.Pre event)
    {
        if(event.getType()!= RenderGameOverlayEvent.ElementType.TEXT)
            return;
        Minecraft mc = Minecraft.getMinecraft();
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(mc.thePlayer);
        IPlayerItemsProvider playerItems = CapHelper.getPlayerItemsCap(mc.thePlayer);

        GlStateManager.pushMatrix();
        mc.fontRendererObj.drawString("ATTACK_DAMAGE: " +
                (mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()
                + (mc.thePlayer.getHeldItemMainhand()!=null && mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemSword?((ItemSword) mc.thePlayer.getHeldItemMainhand().getItem()).getItemAttributeModifiers(EntityEquipmentSlot.MAINHAND).get(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName()).iterator().next().getAmount():0)), 28, 60, Color.WHITE.getRGB());
        /*//Draw consumable counts***
        mc.fontRendererObj.drawString(playerData.getCoins()+"",28,80, Color.WHITE.getRGB());
        mc.fontRendererObj.drawString(playerData.getKeys()+"",28,98, Color.WHITE.getRGB());
        mc.fontRendererObj.drawString(playerData.getBombs()+"",28,120, Color.WHITE.getRGB());
        //mc.fontRendererObj.drawString(playerData.getSoulHearts() + "", 28, 138, Color.WHITE.getRGB());


        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.penny,1,0),10,80);
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.key,1,0),10,98);
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.bomb,1,0),10,120);
        //mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.soulHeart, 1, 0), 10, 138);
        GL11.glPopMatrix();
        //End Draw consumable counts****/


        if (playerItems.getActiveItem() != null && playerItems.getActiveItem().getItem() instanceof IActive)
        {
            ((IActive) playerItems.getActiveItem().getItem()).renderInGUI(playerItems.getActiveItem());
        }

        //ITrinket trinket = (ITrinket) playerItems.getTrinketItem().getItem();
        if (playerItems.getTrinketItem()!=null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.5,1.5,1.5);
            mc.getRenderItem().renderItemIntoGUI(playerItems.getTrinketItem(), 5, (int)Math.floor(214.0/1.5));
            GlStateManager.popMatrix();
        }



        GlStateManager.popMatrix();
    }

    //\\
    private String popupName="";
    private String popupDescription="";
    private long lastPopupUpdate=0L;
    @SubscribeEvent
    public void renderPopup(RenderGameOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.getType()== RenderGameOverlayEvent.ElementType.TEXT)
        {

            GlStateManager.pushMatrix();
            if (!"".equals(this.popupName))
            {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                int scaledWidth = scaledResolution.getScaledWidth();
                int scaledHeight = scaledResolution.getScaledHeight();

                mc.fontRendererObj.drawString(this.popupName, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(this.popupName) / 2, 8, Color.BLACK.getRGB());
                mc.fontRendererObj.drawString(this.popupDescription, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(this.popupDescription) / 2, 26, Color.BLACK.getRGB());
            }
            GlStateManager.popMatrix();
        }

    }

    public void setPopupStrings(String name, String description)
    {
        this.lastPopupUpdate = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
        this.popupName = name;
        this.popupDescription = description;
    }

    @SubscribeEvent
    public void handlePopupTimer(TickEvent.ClientTickEvent event)
    {
        if(Minecraft.getMinecraft().theWorld!=null)
        {
            if(Minecraft.getMinecraft().theWorld.getTotalWorldTime()-this.lastPopupUpdate>80)
            {
                this.popupName="";
                this.popupDescription="";
            }
        }
    }
    //\\

    @SubscribeEvent
    public void renderEntities(RenderLivingEvent.Pre event)
    {
        if (event.getEntity() instanceof EntityLiving)
        {
            byte entityType = Buffs.getChampionType((EntityLiving) event.getEntity());
            if (entityType == 1)
            {
                GlStateManager.color(0, 0, 1);
            } else if (entityType == 2)
            {
                GlStateManager.color(1, 0, 0);
            } else if (entityType == 3)
            {
                GlStateManager.color(0, 1, 0);
            } else if (entityType == 4)
            {
                GlStateManager.color(0, 1, 1);
            } else if (entityType == 5)
            {
                GlStateManager.color(1, 1, 0);
            }
        }
        /*
        if(event.getEntity() instanceof EntitySlime && event.getEntity().getEntityId()%2==0)
        {
            GlStateManager.color(0,0,1);
        }

        if(event.getEntity() instanceof EntitySlime && event.getEntity().getEntityId()%3==0)
        {
            GlStateManager.color(1,0,0);
        }

        if(event.getEntity() instanceof EntityWither)
        {
            GlStateManager.color(1,0,0);
        }
        */
    }
}
