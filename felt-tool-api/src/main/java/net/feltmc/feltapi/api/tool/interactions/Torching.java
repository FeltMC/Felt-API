package net.feltmc.feltapi.api.tool.interactions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public interface Torching {
    public default InteractionResult torch(UseOnContext context){
        Player player = context.getPlayer();
        BlockPlaceContext itemPlacementContext = new BlockPlaceContext(context);
        Level world = context.getLevel();
        BlockPos pos = itemPlacementContext.getClickedPos();
        BlockState state = getTorchedState(itemPlacementContext);
        if (canTorch(context)){
            if (context.getLevel().setBlock(itemPlacementContext.getClickedPos(), state, 11)) {
                SoundType blockSoundGroup = state.getSoundType();
                world.playSound(player, pos, blockSoundGroup.getPlaceSound(), SoundSource.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                if (player == null || !player.getAbilities().instabuild) {
                    removeTorch(itemPlacementContext);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public default BlockState getTorchedState(BlockPlaceContext context) {
        BlockState state = null;
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[i];
            if (direction != Direction.UP) {
                BlockState face = direction == Direction.DOWN ? Blocks.TORCH.getStateForPlacement(context): Blocks.WALL_TORCH.getStateForPlacement(context);
                if (face != null && face.canSurvive(world, pos)) {
                    state = face;
                    break;
                }
            }
        }
        return state != null && world.isUnobstructed(state, pos, CollisionContext.empty()) ? state : null;
    }

    public default boolean canTorch(UseOnContext context){
        Player player = context.getPlayer();
        if (getTorchedState(new BlockPlaceContext(context)) != null){
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if (player.getInventory().getItem(i).getItem() == Items.TORCH) {
                    return true;
                }
            }
        }
        return false;
    }

    public default void removeTorch(BlockPlaceContext context){
        Player player = context.getPlayer();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() == Items.TORCH) {
                player.getInventory().getItem(i).shrink(1);
                break;
            }
        }
    }
}