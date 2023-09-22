package net.feltmc.feltapi.api.tool.interactions;

import net.feltmc.feltapi.mixin.tool.HoeMixin;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Tilling {
    public default InteractionResult till(UseOnContext context){
        Level world = context.getLevel();
        Player playerEntity = context.getPlayer();
        if (getTilledPair(context) == null) return InteractionResult.PASS;
        if (getTilledPair(context).getFirst().test(context)) {
            world.playSound(playerEntity, context.getClickedPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!world.isClientSide) {
                getTilledPair(context).getSecond().accept(context);
                if (playerEntity != null) context.getItemInHand().hurtAndBreak(1, playerEntity, p -> p.broadcastBreakEvent(context.getHand()));
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public default Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getTilledPair(UseOnContext context){
        return HoeMixin.getTilled().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
    }
}
