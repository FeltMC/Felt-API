package net.feltmc.feltapi.api.tool.interactions;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public interface Lighting {
    public default InteractionResult light(UseOnContext context){
        Player playerEntity = context.getPlayer();
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
            if (BaseFireBlock.canBePlacedAt(world, blockPos.relative(context.getClickedFace()), context.getHorizontalDirection())) {
                fire(context, BaseFireBlock.getState(world, blockPos.relative(context.getClickedFace())), blockPos.relative(context.getClickedFace()));
                if (playerEntity instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerEntity, blockPos.relative(context.getClickedFace()), context.getItemInHand());
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            fire(context, (BlockState)blockState.setValue(BlockStateProperties.LIT, true), blockPos);
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
    }

    public default void fire(UseOnContext context, BlockState blockState, BlockPos blockPos){
        Player playerEntity = context.getPlayer();
        Level world = context.getLevel();
        world.playSound(playerEntity, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        world.setBlock(blockPos, blockState, 11);
        world.gameEvent(playerEntity, GameEvent.BLOCK_PLACE, context.getClickedPos());
        if (playerEntity != null) context.getItemInHand().hurtAndBreak(1, playerEntity, (p) -> { p.broadcastBreakEvent(context.getHand()); });
    }
}
