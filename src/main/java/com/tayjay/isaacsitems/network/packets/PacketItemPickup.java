package com.tayjay.isaacsitems.network.packets;

import com.tayjay.isaacsitems.IsaacsItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.util.control.Exception;

/**
 * Created by tayjay on 2016-12-30.
 */
public class PacketItemPickup extends PacketRunnable<PacketItemPickup>
{
    private String name;
    private String desc;
    public PacketItemPickup(){}

    public PacketItemPickup(String name, String descrition)
    {
        this.name = name;
        this.desc = descrition;
    }
    @Override
    public void handleServer(PacketItemPickup message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketItemPickup message, MessageContext ctx)
    {
        if (Minecraft.getMinecraft().theWorld != null)
        {
            IsaacsItems.proxy.getClientEventHandler().setPopupStrings(message.name,message.desc);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.name = ByteBufUtils.readUTF8String(buf);
        this.desc = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf,this.name);
        ByteBufUtils.writeUTF8String(buf,this.desc);
    }
}
