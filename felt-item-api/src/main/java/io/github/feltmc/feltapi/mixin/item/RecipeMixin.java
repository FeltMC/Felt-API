package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.ContainerItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Recipe.class)
public interface RecipeMixin<C extends Inventory> {

    /**
     * @author trinsdar
     * @reason cause I can't see a better way to do this, requires too many changes.
     */
    @Overwrite
    default DefaultedList<ItemStack> getRemainder(C inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack item = inventory.getStack(i);
            if (item.getItem() instanceof ContainerItem container){
                if (container.hasContainerItem(item)){
                    defaultedList.set(i, container.getContainerItem(item));
                    continue;
                }
            }
            if (item.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(item.getItem().getRecipeRemainder()));
            }
        }

        return defaultedList;
    }
}
