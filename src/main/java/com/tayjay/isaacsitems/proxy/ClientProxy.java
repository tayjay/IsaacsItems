package com.tayjay.isaacsitems.proxy;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.block.tileentity.TileEntityPedestal;
import com.tayjay.isaacsitems.client.handler.KeyInputHandler;
import com.tayjay.isaacsitems.client.render.RenderTilePedestal;
import com.tayjay.isaacsitems.client.settings.Keybindings;
import com.tayjay.isaacsitems.event.ClientEventHandler;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ClientProxy extends CommonProxy
{
    private ClientEventHandler clientEventHandler;
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item,meta, new ModelResourceLocation(IsaacsItems.modId+":"+id,"inventory"));//inventory

    }

    @Override
    public IPlayerDataProvider getClientPlayerData()
    {
        return CapHelper.getPlayerDataCap(FMLClientHandler.instance().getClientPlayerEntity());
    }

    @Override
    public void preInit()
    {
        registerKeyBindings();
        registerRenderers();
    }

    @Override
    public void registerKeyBindings()
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        for (Keybindings key : Keybindings.values())
        {
            ClientRegistry.registerKeyBinding(key.getKeybind());
        }
    }

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class,new RenderTilePedestal());
    }

    @Override
    public void init()
    {
        super.init();
        MinecraftForge.EVENT_BUS.register(clientEventHandler = new ClientEventHandler());
        //GuiIngameForge.renderHealth = false;
    }

    @Override
    public ClientEventHandler getClientEventHandler()
    {
        return clientEventHandler;
    }
}
