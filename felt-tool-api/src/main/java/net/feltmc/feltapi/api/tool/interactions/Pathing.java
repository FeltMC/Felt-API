package net.feltmc.feltapi.api.tool.interactions;

import net.feltmc.feltapi.mixin.tool.ShovelMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;

public interface Pathing {
    public default InteractionResult path(UseOnContext context){
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (context.getClickedFace() != Direction.DOWN) {
            BlockState blockState2 = getPathedState(context);
            BlockState blockState3 = null;
            if (blockState2 != null && world.getBlockState(blockPos.above()).isAir()) {
                world.playSound(context.getPlayer(), blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                blockState3 = blockState2;
            } else if (blockState.getBlock() instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT).booleanValue()) {
                if (!world.isClientSide()) world.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, blockPos, 0);
                CampfireBlock.dowse(context.getPlayer(), world, blockPos, blockState);
                blockState3 = (BlockState)blockState.setValue(CampfireBlock.LIT, false);
            }
            if (blockState3 != null) {
                if (!world.isClientSide) {
                    world.setBlock(blockPos, blockState3, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
                    if (context.getPlayer() != null) context.getItemInHand().hurtAndBreak(1, context.getPlayer(), p -> p.broadcastBreakEvent(context.getHand()));
                }
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    public default BlockState getPathedState(UseOnContext context){
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        return ShovelMixin.getPathed().get(blockState.getBlock());
    }
}