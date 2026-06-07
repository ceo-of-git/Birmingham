package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


// Lives to serve the eternal coking oven
public class BigBlockPart extends Block {

	public BigBlockPart(Properties pProperties) {
		super(pProperties);
	}
	
	// Destroy the whole if a part is gone.
	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
	    if (!level.isClientSide()) {

	        BlockEntity blockEntity = level.getBlockEntity(pos);

	        if (blockEntity instanceof BigBlockPartBlockEntity part) {

	            BlockPos masterPos = part.getMasterPos();

	            level.destroyBlock(masterPos, !player.isCreative());
	        }
	    }

	    super.playerWillDestroy(level, pos, state, player);
	}

}
