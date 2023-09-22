package net.feltmc.feltapi.api.tool.interactions;

import net.feltmc.feltapi.mixin.tool.AxeMixin;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Optional;

public interface Stripping {
    public default InteractionResult strip(UseOnContext context){
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player playerEntity = context.getPlayer();
        BlockState blockState = world.getBlockState(blockPos);
        Optional<BlockState> strippedState = this.getStrippedState(blockState);
        Optional<BlockState> waxedBlocks = Optional.ofNullable((Block) HoneycombItem.WAX_OFF_BY_BLOCK.get().get(blockState.getBlock())).map(block -> block.withPropertiesOf(blockState));
        if (strippedState.isPresent()) {
            return doBlockstate(context, world, playerEntity, strippedState, SoundEvents.AXE_STRIP);
        } else if (WeatheringCopper.getPrevious(blockState).isPresent()) {
            world.levelEvent(playerEntity, LevelEvent.PARTICLES_SCRAPE, blockPos, 0);
            return doBlockstate(context, world, playerEntity, strippedState, SoundEvents.AXE_SCRAPE);
        } else if (waxedBlocks.isPresent()) {
            world.levelEvent(playerEntity, LevelEvent.PARTICLES_WAX_OFF, blockPos, 0);
            return doBlockstate(context, world, playerEntity, strippedState, SoundEvents.AXE_WAX_OFF);
        }
        return InteractionResult.SUCCESS;
    }

    public default boolean canStrip(UseOnContext context){
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        Optional<BlockState> waxedBlocks = Optional.ofNullable((Block) HoneycombItem.WAX_OFF_BY_BLOCK.get().get(blockState.getBlock())).map(block -> block.withPropertiesOf(blockState));
        return this.getStrippedState(blockState).isPresent() || WeatheringCopper.getPrevious(blockState).isPresent() || waxedBlocks.isPresent();
    }

    public default InteractionResult doBlockstate(UseOnContext context, Level world, Player playerEntity, Optional<BlockState> blockState, SoundEvent sound){
        world.playSound(playerEntity, context.getClickedPos(), sound, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (playerEntity instanceof ServerPlayer) CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)playerEntity, context.getClickedPos(), context.getItemInHand());
        world.setBlock(context.getClickedPos(), (BlockState)blockState.get(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
        if (playerEntity != null) context.getItemInHand().hurtAndBreak(1, playerEntity, p -> p.broadcastBreakEvent(context.getHand()));
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    public default Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable(AxeMixin.getStripped().get(state.getBlock())).map(block -> (BlockState)block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
    }
}