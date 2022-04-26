package io.github.feltmc.feltapi.api.tool.interactions;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public interface Lighting {
    public default ActionResult light(ItemUsageContext context){
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
            if (AbstractFireBlock.canPlaceAt(world, blockPos.offset(context.getSide()), context.getPlayerFacing())) {
                fire(context, AbstractFireBlock.getState(world, blockPos.offset(context.getSide())), blockPos.offset(context.getSide()));
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos.offset(context.getSide()), context.getStack());
                }
                return ActionResult.success(world.isClient());
            } else {
                return ActionResult.FAIL;
            }
        } else {
            fire(context, (BlockState)blockState.with(Properties.LIT, true), blockPos);
            return ActionResult.success(world.isClient());
        }
    }

    public default void fire(ItemUsageContext context, BlockState blockState, BlockPos blockPos){
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        world.setBlockState(blockPos, blockState, 11);
        world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, context.getBlockPos());
        if (playerEntity != null) context.getStack().damage(1, playerEntity, (p) -> { p.sendToolBreakStatus(context.getHand()); });
    }
}
