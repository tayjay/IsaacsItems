package com.tayjay.isaacsitems.block;

import com.tayjay.isaacsitems.api.capabilities.IPlayerItemsProvider;
import com.tayjay.isaacsitems.block.tileentity.TileEntityPedestal;
import com.tayjay.isaacsitems.init.ModItems;
import com.tayjay.isaacsitems.util.CapHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by tayjay on 2017-01-05.
 */
public class BlockPedestal extends BlockBase
{
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625,0,0.0625, 0.0625*15,0.0625*6,0.0625*15);
    public BlockPedestal(String name)
    {
        super(Material.ROCK, name);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntityPedestal te = (TileEntityPedestal) worldIn.getTileEntity(pos);
        te.getItemHandler().setStackInSlot(0,new ItemStack(ModItems.activeItems.get(placer.getRNG().nextInt(ModItems.activeItems.size()))));
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if(entityIn instanceof EntityPlayer)
        {
            swapItems(worldIn, ((EntityPlayer) entityIn),pos);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        swapItems(worldIn,playerIn,pos);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    public void swapItems(World worldIn, EntityPlayer playerIn, BlockPos pos)
    {
        if(worldIn.getTileEntity(pos) instanceof TileEntityPedestal && ((TileEntityPedestal) worldIn.getTileEntity(pos)).getItemHandler().getStackInSlot(0)!=null)
        {
            TileEntityPedestal te = ((TileEntityPedestal) worldIn.getTileEntity(pos));
            IPlayerItemsProvider playerItems = CapHelper.getPlayerItemsCap(playerIn);
            if(playerIn.isSneaking())
            {
                playerIn.addChatMessage(new TextComponentString("Current Item: " + te.getItemHandler().getStackInSlot(0)));
                return;
            }
            if(!worldIn.isRemote)
            {
                if(worldIn.getTotalWorldTime()-te.getLastClickTime()>20)
                {

                    EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), te.getItemHandler().extractItem(0, 1, false));
                    item.setNoPickupDelay();

                    if (playerItems.getActiveItem() != null)
                        te.getItemHandler().insertItem(0, playerItems.getActiveInv().extractItem(0, 1, false), false);

                    worldIn.spawnEntityInWorld(item);
                    te.setLastClickTime(worldIn.getTotalWorldTime());


                }
            }
            else
            {
                if(worldIn.getTotalWorldTime()-te.getLastClickTime()>20)
                {
                    te.getItemHandler().extractItem(0,1,false);

                    if (playerItems.getActiveItem() != null)
                        te.getItemHandler().insertItem(0, playerItems.getActiveInv().extractItem(0, 1, false), false);
                    te.setLastClickTime(worldIn.getTotalWorldTime());
                }
            }

        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPedestal();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        super.addCollisionBoxToList(pos,entityBox,collidingBoxes,BOUNDING_BOX);
    }
}
