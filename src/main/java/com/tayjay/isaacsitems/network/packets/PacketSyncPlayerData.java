package com.tayjay.isaacsitems.network.packets;

import com.tayjay.isaacsitems.IsaacsItems;
import com.tayjay.isaacsitems.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-12-26.
 */
public class PacketSyncPlayerData extends PacketRunnable<PacketSyncPlayerData>
{
    private int playerId;
    private NBTTagCompound nbt;

    public PacketSyncPlayerData(){}

    public PacketSyncPlayerData(NBTTagCompound tag, EntityPlayer player)
    {
        this.playerId = player.getEntityId();
        this.nbt = tag;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf)
    {
        this.playerId = byteBuf.readInt();
        this.nbt = ByteBufUtils.readTag(byteBuf);
    }

    @Override
    public void toBytes(ByteBuf byteBuf)
    {
        byteBuf.writeInt(this.playerId);
        ByteBufUtils.writeTag(byteBuf,this.nbt);
    }

    @Override
    public void handleServer(PacketSyncPlayerData message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketSyncPlayerData message, MessageContext ctx)
    {
        if(message.playerId == Minecraft.getMinecraft().thePlayer.getEntityId())
        {
            IsaacsItems.proxy.getClientPlayerData().deserializeNBT(message.nbt);
        }
        else
        {
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(playerId);
            CapHelper.getPlayerDataCap(player).deserializeNBT(message.nbt);
        }

    }
}
