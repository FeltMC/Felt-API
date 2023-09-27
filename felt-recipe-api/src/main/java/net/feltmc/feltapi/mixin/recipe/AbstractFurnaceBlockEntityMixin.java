package net.feltmc.feltapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    
    @Unique private static final String INT_BURN_TIME_KEY = "IntBurnTime";
    @Unique private static final String INT_COOK_TIME_KEY = "IntCookTime";
    @Unique private static final String INT_COOK_TIME_TOTAL_KEY = "IntCookTimeTotal";
    
    @Shadow int litTime;
    @Shadow int cookingProgress;
    @Shadow int cookingTotalTime;
    
    @Redirect(method = "burn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;grow(I)V"))
    private static void injectAmount(ItemStack instance, int increment, Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        instance.grow(recipe.getResultItem().getCount());
    }

    @ModifyReturnValue(method = "canBurn", at = @At(value = "RETURN", ordinal = 5))
    private static boolean canFit(boolean original, @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getResultItem().getCount() <= nonNullList.get(2).getMaxStackSize();
    }

    @ModifyReturnValue(method = "canBurn", at = @At(value = "RETURN", ordinal = 4))
    private static boolean canFit2(boolean original, @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getResultItem().getCount() <= i;
    }
    
    @Inject(method = "load", at = @At("TAIL"))
    public void load$TAIL(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(INT_BURN_TIME_KEY)) {
            this.litTime = tag.getInt(INT_BURN_TIME_KEY);
        }
        if (tag.contains(INT_COOK_TIME_KEY)) {
            this.cookingProgress = tag.getInt(INT_COOK_TIME_KEY);
        }
        if (tag.contains(INT_COOK_TIME_TOTAL_KEY)) {
            this.cookingTotalTime = tag.getInt(INT_COOK_TIME_TOTAL_KEY);
        }
    }
    
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void saveAdditional$TAIL(CompoundTag tag, CallbackInfo ci) {
        tag.putInt(INT_BURN_TIME_KEY, this.litTime);
        tag.putInt(INT_COOK_TIME_KEY, this.cookingProgress);
        tag.putInt(INT_COOK_TIME_TOTAL_KEY, this.cookingTotalTime);
    }
    
}
