package com.tayjay.isaacsitems.client.render;

import com.tayjay.isaacsitems.block.tileentity.TileEntityPedestal;
import com.tayjay.isaacsitems.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2017-01-06.
 */
public class RenderTilePedestal extends TileEntitySpecialRenderer<TileEntityPedestal>
{
    @Override
    public void renderTileEntityAt(TileEntityPedestal pedestal, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if(!pedestal.getWorld().isBlockLoaded(pedestal.getPos(), false)
                || pedestal.getWorld().getBlockState(pedestal.getPos()).getBlock() != ModBlocks.blockPedestal)
            return;

        if(pedestal.getItemHandler().getStackInSlot(0)!=null)
        {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            //GlStateManager.color(1F, 1F, 1F, 1F);

            GlStateManager.translate(x + 0.5, y + 0.75, z + 0.5);
            GlStateManager.enableRescaleNormal();



            ItemStack stack = pedestal.getItemHandler().getStackInSlot(0);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
    }
}
