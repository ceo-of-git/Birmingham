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
	
	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {

	    BlockEntity be = level.getBlockEntity(pos);

	    if (be instanceof BigBlockPartBlockEntity partBlockEntity) {
	        BlockPos masterPos = partBlockEntity.getMasterPos();

	        BlockState masterState = level.getBlockState(masterPos);
	        masterState.getBlock().playerWillDestroy(level, masterPos, masterState, player );
	    }

	    super.playerWillDestroy(level, pos, state, player);
	}

}
