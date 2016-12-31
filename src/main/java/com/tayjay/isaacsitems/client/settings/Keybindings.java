package com.tayjay.isaacsitems.client.settings;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by tayjay on 2016-12-27.
 */
public enum Keybindings
{
    ACTIVATE("keys.isaacsitems.activate",Keyboard.KEY_R),OPEN_ITEMS_GUI("keys.isaacsitems.open_items_gui",Keyboard.KEY_I);

    private final KeyBinding keyBinding;

    Keybindings(String keyName, int defaultKeyCode)
    {
        keyBinding = new KeyBinding(keyName,defaultKeyCode,"key.isaacsitems.category");
    }

    public KeyBinding getKeybind(){return keyBinding;}

    public boolean isPressed()
    {
        return keyBinding.isPressed();
    }
}
