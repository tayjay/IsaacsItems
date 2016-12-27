package com.tayjay.isaacsitems.api;

import com.tayjay.isaacsitems.api.capabilities.IPlayerDataProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * Created by tayjay on 2016-12-26.
 */
public class IsaacAPI
{
    private IsaacAPI(){}

    //Declare Capabilites and such here
    /*
    @CapabilityInject(IAugDataProvider.class)
    public static final Capability<IAugDataProvider> AUGMENT_DATA_CAPABILITY = null;
     */
    @CapabilityInject(IPlayerDataProvider.class)
    public static final Capability<IPlayerDataProvider> PLAYER_DATA_CAPABILITY = null;
}
