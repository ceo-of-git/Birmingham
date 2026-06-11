package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

// Multiblocks implement one "Main Block"
// and every other block as a dummy loser block.

// I'm calling these 'Big Blocks' as I may in the future
// want to add some proper multiblock-like structures.
// Who knows.
// (I dont)
public interface IBigBlockType {
	// How many blocks your multiblock goes past the origin block position
	// So for the Coking oven its, 1, 1, 1 for a 2x2 block
	// MAKE SURE THAT WHEN YOU MODEL THE BLOCKS IN BLOCKBENCH, THE ORIGIN IS THE BOTTOM RIGHT.
    int GetSizeX();
    int GetSizeY();
    int GetSizeZ();
    

    default boolean canPlace(BlockState state, Level level, BlockPos origin, Direction facing) {
    	
        // Check all blocks in volume
        for (int x = 0; x < GetSizeX(); x++) {
            for (int y = 0; y < GetSizeY(); y++) {
                for (int z = 0; z < GetSizeZ(); z++) {

                    BlockPos checkPos = origin.offset(rotateOffset(x, y, z, facing));
                    
                    // Don't fuck up the master block just incase
                    if (x == 0 && y == 0 && z == 0)
                        continue;

                    // If this is a solid block or outside the world, don't let em do that.
                    if (!level.getBlockState(checkPos).canBeReplaced() || !level.isInWorldBounds(checkPos)) {
                        return false;
                    }
                }
            }
        }

        // Passed all checks, return true.
        return true;
    }
    
    // Helper method that i 100% stole
    default BlockPos rotateOffset(int x, int y, int z, Direction facing) {

        return switch (facing) {
            case NORTH -> new BlockPos(x, y, z);
            case SOUTH -> new BlockPos(-x, y, -z);
            case EAST  -> new BlockPos(-z, y, x);
            case WEST  -> new BlockPos(z, y, -x);
            default -> new BlockPos(x, y, z);
        };
    }
    
    default BlockPos getPartPosition(BlockPos origin, int x, int y, int z, Direction facing) {
        return origin.offset(rotateOffset(x, y, z, facing));
    }
}