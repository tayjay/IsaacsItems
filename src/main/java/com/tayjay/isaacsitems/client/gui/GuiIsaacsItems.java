package com.tayjay.isaacsitems.client.gui;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.inventory.ContainerIsaacsItems;
import com.tayjay.isaacsitems.inventory.InventoryActive;
import com.tayjay.isaacsitems.inventory.InventoryPassive;
import com.tayjay.isaacsitems.inventory.InventoryTrinket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by tayjay on 2016-12-29.
 */
public class GuiIsaacsItems extends GuiContainer
{
    private EntityPlayer player;
    private static final ResourceLocation texture = new ResourceLocation(IsaacsItems.modId.toLowerCase(),"textures/gui/isaacsItemsInv.png");

    public GuiIsaacsItems(InventoryPlayer invPlayer, InventoryPassive inventoryPassive, InventoryActive inventoryActive, InventoryTrinket inventoryTrinket)
    {
        super(new ContainerIsaacsItems(invPlayer,inventoryPassive,inventoryActive,inventoryTrinket));
        this.xSize = 255;
        this.ySize = 230;
        this.player = Minecraft.getMinecraft().thePlayer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        //this.drawDefaultBackground();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.drawTexturedModalRect((width-xSize)/2,(height-ySize)/2,0,0,xSize,ySize);

        GlStateManager.pushMatrix();
        GlStateManager.color(1,1,1,1);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(IsaacsItems.modId,"textures/gui/slotEmpty.png"));
        GlStateManager.translate(guiLeft,guiTop,0);
        for(int i=0;i<this.inventorySlots.inventorySlots.size();i++)
        {
            //if(inventorySlots.inventorySlots.get(i)!=null)
                drawModalRectWithCustomSizedTexture(inventorySlots.inventorySlots.get(i).xDisplayPosition,inventorySlots.inventorySlots.get(i).yDisplayPosition,0,0,16,16,16,16);
        }
        GlStateManager.popMatrix();
    }
}
