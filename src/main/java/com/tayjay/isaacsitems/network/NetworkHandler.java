package com.tayjay.isaacsitems.network;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.network.packets.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by tayjay on 2016-12-26.
 */
public class NetworkHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(IsaacsItems.modId);

    /**
     * Register packets on load
     */
    static
    {
        int desc = 0;
        //Server packets
        INSTANCE.registerMessage(PacketActivateItem.class, PacketActivateItem.class, desc++, Side.SERVER);
        INSTANCE.registerMessage(PacketOpenGui.class, PacketOpenGui.class, desc++, Side.SERVER);

        //Client Packets
        INSTANCE.registerMessage(PacketSyncPlayerData.class, PacketSyncPlayerData.class, desc++, Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncActiveItem.class, PacketSyncActiveItem.class, desc++, Side.CLIENT);
        INSTANCE.registerMessage(PacketItemPickup.class, PacketItemPickup.class, desc++, Side.CLIENT);
        INSTANCE.registerMessage(PacketRegisterChampionType.class, PacketRegisterChampionType.class, desc++, Side.CLIENT);
    }

    public static void sendToAll(IMessage msg)
    {
        INSTANCE.sendToAll(msg);
    }

    public static void sendTo(IMessage msg, EntityPlayerMP player)
    {
        INSTANCE.sendTo(msg,player);
    }

    public static void sendToAllAround(IMessage msg, EntityPlayerMP player, double range)
    {
        sendToAllAround(msg, player.dimension, player.posX, player.posY, player.posZ, range);
    }

    public static void sendToAllAround(IMessage msg, int dim, double x, double y, double z, double range)
    {
        INSTANCE.sendToAllAround(msg, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public static void sendToServer(IMessage msg)
    {
        INSTANCE.sendToServer(msg);
    }

}
