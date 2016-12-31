package com.tayjay.isaacsitems.network.packets;

import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-12-27.
 */
public class PacketSyncActiveItem extends PacketRunnable<PacketSyncActiveItem>
{
    private int playerId;
    private NBTTagCompound tag;

    public PacketSyncActiveItem(){}

    public PacketSyncActiveItem(int id,NBTTagCompound tagCompound)
    {
        this.playerId = id;
        this.tag = tagCompound;

    }


    @Override
    public void handleServer(PacketSyncActiveItem message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketSyncActiveItem message, MessageContext ctx)
    {
        IPlayerItemsProvider playerItems = CapHelper.getPlayerItemsCap((EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(message.playerId));
        playerItems.deserializeNBT(message.tag);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();
        this.tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerId);
        ByteBufUtils.writeTag(buf, tag);
    }
}
