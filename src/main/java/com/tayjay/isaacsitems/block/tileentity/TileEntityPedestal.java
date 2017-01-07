package com.tayjay.isaacsitems.block.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by tayjay on 2017-01-05.
 */
public class TileEntityPedestal extends TileSimpleInventory
{
    private long lastClickTime=0;
    private boolean needsUpdate = false;

    public long getLastClickTime()
    {
        return lastClickTime;
    }

    public void setLastClickTime(long lastClickTime)
    {
        this.lastClickTime = lastClickTime;
        needsUpdate = true;
    }


    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState)
    {
        return oldState != newState;
    }

    @Override
    public void update()
    {
        if(needsUpdate)
        {
            if(worldObj.isRemote)
                this.worldObj.markBlockRangeForRenderUpdate(this.getPos(),this.getPos());
            else
            {
                this.markDirty();

            }
            needsUpdate = false;
        }

    }


}
