package io.github.feltmc.feltapi.api.tool.interactions;

import io.github.feltmc.feltapi.mixin.tool.common.HoeMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Tilling {
    public default ActionResult till(ItemUsageContext context){
        World world = context.getWorld();
        PlayerEntity playerEntity = context.getPlayer();
        if (getTilledPair(context) == null) return ActionResult.PASS;
        if (getTilledPair(context).getFirst().test(context)) {
            world.playSound(playerEntity, context.getBlockPos(), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isClient) {
                getTilledPair(context).getSecond().accept(context);
                if (playerEntity != null) context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public default Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> getTilledPair(ItemUsageContext context){
        return HoeMixin.getTilled().get(context.getWorld().getBlockState(context.getBlockPos()).getBlock());
    }
}
