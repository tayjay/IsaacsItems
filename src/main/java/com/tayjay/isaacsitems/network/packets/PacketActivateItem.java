package com.tayjay.isaacsitems.network.packets;

import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-12-27.
 */
public class PacketActivateItem extends PacketRunnable<PacketActivateItem>
{
    private int playerId;

    public PacketActivateItem(){}

    public PacketActivateItem(EntityPlayer player)
    {

        this.playerId = player.getEntityId();
    }
    @Override
    public void handleServer(PacketActivateItem message, MessageContext ctx)
    {
        ItemStack stack = CapHelper.getPlayerItemsCap((EntityPlayer) ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.playerId)).getActiveItem();
        if (stack != null && stack.getItem() instanceof IActive)
        {
            ((IActive) stack.getItem()).onActivate(stack, (EntityPlayer) ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.playerId));

        }
    }

    @Override
    public void handleClient(PacketActivateItem message, MessageContext ctx)
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(playerId);
    }
}
