package io.github.feltmc.feltapi.api.item.extensions;

import io.github.fabricators_of_create.porting_lib.extensions.ItemExtensions;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface FeltItem extends FabricItem, ArmorEquipItem, ArmorTickItem, ContainerItem, ElytraFlightItem, EnchantabilityItem, EntityCustomItem, IsDamageableItem, ItemGroupItem, MiscExtension, PiglinItem, ShareTagItem, SneakBypassUseItem, PortingLibInterfaces {

    @Nullable
    @Override
    default FoodComponent getFoodComponenet(ItemStack stack, @Nullable LivingEntity entity) {
        return MiscExtension.super.getFoodComponenet(stack, entity);
    }
}
