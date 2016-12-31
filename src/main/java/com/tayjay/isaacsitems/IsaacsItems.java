package com.tayjay.isaacsitems;

import com.tayjay.isaacsitems.capability.ActiveDataImpl;
import com.tayjay.isaacsitems.capability.PlayerDataImpl;
import com.tayjay.isaacsitems.capability.PlayerItemsImpl;
import com.tayjay.isaacsitems.event.IsaacEventHandler;
import com.tayjay.isaacsitems.handler.GuiHandler;
import com.tayjay.isaacsitems.init.ModBlocks;
import com.tayjay.isaacsitems.init.ModItems;
import com.tayjay.isaacsitems.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.tayjay.isaacsitems.IsaacsItems.modId;

/**
 * Created by tayjay on 2016-12-26.
 */
@Mod(modid = IsaacsItems.modId,name = IsaacsItems.name, version = IsaacsItems.version,guiFactory = IsaacsItems.guiFactory)
public class IsaacsItems
{
    public static final String modId = "isaacsitems";
    public static final String name = "Isaac's Items";
    public static final String version = "0.0.0.1";
    public static final String guiFactory = "com.tayjay.isaacsitems.client.GuiFactory";

    @Mod.Instance(modId)
    public static IsaacsItems instance;

    @SidedProxy(serverSide = "com.tayjay.isaacsitems.proxy.CommonProxy",clientSide = "com.tayjay.isaacsitems.proxy.ClientProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("Loading Isaac's Items!");
        ModBlocks.init();
        ModItems.init();
        PlayerDataImpl.init();
        PlayerItemsImpl.init();
        ActiveDataImpl.init();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new IsaacEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance,new GuiHandler());
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
