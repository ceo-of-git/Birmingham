package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

// Multiblocks implement one "Main Block"
// and every other block as a dummy loser block.
public interface IMultiblockType {

	// How many blocks your multiblock goes past the origin block position
	// So for the Coking oven its, 1, 1, 1 for a 2x2 block
	// MAKE SURE THAT WHEN YOU MODEL THE BLOCKS IN BLOCKBENCH, THE ORIGIN IS THE BOTTOM RIGHT.
    int GetModelExtensionX();
    int GetModelExtensionY();
    int GetModelExtensionZ();

    default boolean CanPlace(Level level, BlockPos origin) {

        // Check all blocks in volume
        for (int x = 0; x < GetModelExtensionX(); x++) {
            for (int y = 0; y < GetModelExtensionY(); y++) {
                for (int z = 0; z < GetModelExtensionZ(); z++) {

                    BlockPos checkPos = origin.offset(x, y, z);

                    if (!level.getBlockState(checkPos).canBeReplaced()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}