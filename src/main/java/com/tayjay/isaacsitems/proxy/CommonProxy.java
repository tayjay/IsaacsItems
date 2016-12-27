package com.tayjay.isaacsitems.proxy;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import net.minecraft.item.Item;

/**
 * Created by tayjay on 2016-12-26.
 */
public class CommonProxy
{
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }

    public void preInit(){}

    public void init(){}

    public void registerKeyBindings(){}

    public IPlayerDataProvider getClientPlayerData(){return null;}

}
