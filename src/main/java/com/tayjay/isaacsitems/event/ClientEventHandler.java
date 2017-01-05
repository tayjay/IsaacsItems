package com.tayjay.isaacsitems.event;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.capabilities.IActiveDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.api.item.ITrinket;
import com.tayjay.isaacsitems.init.ModItems;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
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
        //Draw consumable counts***
        mc.fontRendererObj.drawString(playerData.getCoins()+"",28,80, Color.WHITE.getRGB());
        mc.fontRendererObj.drawString(playerData.getKeys()+"",28,98, Color.WHITE.getRGB());
        mc.fontRendererObj.drawString(playerData.getBombs()+"",28,120, Color.WHITE.getRGB());
        mc.fontRendererObj.drawString(playerData.getSoulHearts() + "", 28, 138, Color.WHITE.getRGB());


        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.penny,1,0),10,80);
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.key,1,0),10,98);
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.bomb,1,0),10,120);
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(ModItems.soulHeart, 1, 0), 10, 138);
        GL11.glPopMatrix();
        //End Draw consumable counts***


        //Draw charge bar
        if(playerItems.getActiveItem()!=null)
        {
            IActive activeItem = (IActive) playerItems.getActiveItem().getItem();
            if(activeItem!=null)
            {
                double currentCharge = activeItem.getCurrentCharge(playerItems.getActiveItem());
                double maxCharge = activeItem.getMaxCharge(playerItems.getActiveItem());

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
                mc.getRenderItem().renderItemIntoGUI(playerItems.getActiveItem(), 5, 5);
                GL11.glPopMatrix();
            }
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

    @SubscribeEvent
    public void renderPlayerHealth(RenderGameOverlayEvent.Pre event)
    {
        /*Minecraft mc = Minecraft.getMinecraft();
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
        {
            event.setResult(Event.Result.ALLOW);

            GlStateManager.pushMatrix();
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int scaledWidth = scaledResolution.getScaledWidth();
            int scaledHeight = scaledResolution.getScaledHeight();
            mc.fontRendererObj.drawString("Health is working", scaledWidth / 2 - mc.fontRendererObj.getStringWidth(this.popupDescription) / 2, 26, Color.BLACK.getRGB());
            GlStateManager.popMatrix();
        }*/
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


    //todo: Remember this, here is setting colour of mobs
    @SubscribeEvent
    public void renderEntities(RenderLivingEvent.Pre event)
    {
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
    }
}
