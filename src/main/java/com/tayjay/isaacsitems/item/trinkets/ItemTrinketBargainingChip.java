package com.tayjay.isaacsitems.item.trinkets;

import com.tayjay.isaacsitems.api.events.IChangeContainerItem;
import com.tayjay.isaacsitems.item.ItemTrinket;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Created by tayjay on 2017-01-08.
 */
public class ItemTrinketBargainingChip extends ItemTrinket implements IChangeContainerItem
{
    public ItemTrinketBargainingChip(String name)
    {
        super(name);
    }

    @Override
    public void onOpenContainer(PlayerContainerEvent.Open event)
    {
        if (event.getContainer() instanceof ContainerMerchant)
        {
            EntityVillager villager = ReflectionHelper.getPrivateValue(ContainerMerchant.class, ((ContainerMerchant) event.getContainer()), "theMerchant");
            MerchantRecipeList recipes = villager.getRecipes(event.getEntityPlayer());
            if(recipes!=null)
            {
                for(int i = 0;i<recipes.size();i++)
                {
                    if(recipes.get(i).getItemToBuy().stackSize-2>2)
                        recipes.get(i).getItemToBuy().stackSize-=2;
                }

            }

        }
    }

    @Override
    public void onCloseContainer(PlayerContainerEvent.Close event)
    {
        if (event.getContainer() instanceof ContainerMerchant)
        {
            EntityVillager villager = ReflectionHelper.getPrivateValue(ContainerMerchant.class, ((ContainerMerchant) event.getContainer()), "theMerchant");
            MerchantRecipeList recipes = villager.getRecipes(event.getEntityPlayer());
            if(recipes!=null)
            {
                for(int i = 0;i<recipes.size();i++)
                {
                    if(recipes.get(i).getItemToBuy().stackSize>2)
                        recipes.get(i).getItemToBuy().stackSize+=2;
                }
            }

        }
    }
}
