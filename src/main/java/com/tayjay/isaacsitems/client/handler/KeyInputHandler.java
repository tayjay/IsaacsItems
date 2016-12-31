package com.tayjay.isaacsitems.client.handler;

import com.tayjay.isaacsitems.api.item.IActive;
import com.tayjay.isaacsitems.client.settings.Keybindings;
import com.tayjay.isaacsitems.handler.GuiHandler;
import com.tayjay.isaacsitems.network.NetworkHandler;
import com.tayjay.isaacsitems.network.packets.PacketActivateItem;
import com.tayjay.isaacsitems.network.packets.PacketOpenGui;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by tayjay on 2016-12-27.
 */
public class KeyInputHandler
{
    private Keybindings getPressedKey()
    {
        for (Keybindings key : Keybindings.values())
        {
            if(key.isPressed()) return key;
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Keybindings key = getPressedKey();

        if (key != null)
        {
            switch (key)
            {
                case ACTIVATE:
                    ItemStack stack = CapHelper.getPlayerItemsCap(Minecraft.getMinecraft().thePlayer).getActiveItem();
                    if(stack!=null && stack.getItem() instanceof IActive)
                    {
                        if(((IActive)stack.getItem()).drainCharge(stack))
                            NetworkHandler.sendToServer(new PacketActivateItem(Minecraft.getMinecraft().thePlayer));
                    }
                    break;
                case OPEN_ITEMS_GUI:
                    NetworkHandler.sendToServer(new PacketOpenGui(GuiHandler.GuiIDs.ISAACS_ITEMS.ordinal()));
            }
        }
    }
}
