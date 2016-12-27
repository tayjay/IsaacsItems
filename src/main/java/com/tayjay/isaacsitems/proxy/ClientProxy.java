package com.tayjay.isaacsitems.proxy;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * Created by tayjay on 2016-12-26.
 */
public class ClientProxy extends CommonProxy
{
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
}
