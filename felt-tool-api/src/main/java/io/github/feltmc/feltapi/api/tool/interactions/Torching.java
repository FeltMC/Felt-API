package io.github.feltmc.feltapi.api.tool.interactions;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface Torching {
    public default ActionResult torch(ItemUsageContext context){
        PlayerEntity player = context.getPlayer();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        World world = context.getWorld();
        BlockPos pos = itemPlacementContext.getBlockPos();
        BlockState state = getTorchedState(itemPlacementContext);
        if (canTorch(context)){
            if (context.getWorld().setBlockState(itemPlacementContext.getBlockPos(), state, 11)) {
                BlockSoundGroup blockSoundGroup = state.getSoundGroup();
                world.playSound(player, pos, blockSoundGroup.getPlaceSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                if (player == null || !player.getAbilities().creativeMode) {
                    removeTorch(itemPlacementContext);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public default BlockState getTorchedState(ItemPlacementContext context) {
        BlockState state = null;
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction[] directions = context.getPlacementDirections();

        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[i];
            if (direction != Direction.UP) {
                BlockState face = direction == Direction.DOWN ? Blocks.TORCH.getPlacementState(context): Blocks.WALL_TORCH.getPlacementState(context);
                if (face != null && face.canPlaceAt(world, pos)) {
                    state = face;
                    break;
                }
            }
        }
        return state != null && world.canPlace(state, pos, ShapeContext.absent()) ? state : null;
    }

    public default boolean canTorch(ItemUsageContext context){
        PlayerEntity player = context.getPlayer();
        if (getTorchedState(new ItemPlacementContext(context)) != null){
            for (int i = 0; i < player.getInventory().size(); i++) {
                if (player.getInventory().getStack(i).getItem() == Items.TORCH) {
                    return true;
                }
            }
        }
        return false;
    }

    public default void removeTorch(ItemPlacementContext context){
        PlayerEntity player = context.getPlayer();
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i).getItem() == Items.TORCH) {
                player.getInventory().getStack(i).decrement(1);
                break;
            }
        }
    }
}