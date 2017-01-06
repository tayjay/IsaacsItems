package com.tayjay.isaacsitems.network.packets;

import com.tayjay.isaacsitems.lib.Buffs;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2017-01-05.
 */
public class PacketRegisterChampionType extends PacketRunnable<PacketRegisterChampionType>
{
    private int entityID;
    private byte type;

    public PacketRegisterChampionType(){}

    public PacketRegisterChampionType(Entity entity, byte type)
    {
        this.entityID = entity.getEntityId();
        this.type = type;
    }

    @Override
    public void handleServer(PacketRegisterChampionType message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketRegisterChampionType message, MessageContext ctx)
    {
        try
        {
            World world = Minecraft.getMinecraft().theWorld;
            Entity entity = world.getEntityByID(message.entityID);
            entity.getDataManager().register(Buffs.ENTITY_CHAMPION_TYPE, message.type);
        } catch (NullPointerException e)
        {

        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.type = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeByte(this.type);
    }
}
