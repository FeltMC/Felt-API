package io.github.feltmc.feltapi.api.fooditem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface StackFoodItem {
    /**
     * Get the food properties for this item.
     * Use this instead of the {@link Item#getFoodComponent()} method, for ItemStack sensitivity.
     * <p>
     * The @Nullable annotation was only added, due to the default method, also being @Nullable.
     * Use this with a grain of salt, as if you return null here and true at {@link Item#isFood()}, NPEs will occur!
     *
     * @param stack The ItemStack the entity wants to eat.
     * @param entity The entity which wants to eat the food. Be aware that this can be null!
     * @return The current FoodProperties for the item.
     */
    @Nullable // read javadoc to find a potential problem
    default FoodComponent getFoodComponenet(ItemStack stack, @Nullable LivingEntity entity)
    {
        return ((Item)this).getFoodComponent();
    }
}
