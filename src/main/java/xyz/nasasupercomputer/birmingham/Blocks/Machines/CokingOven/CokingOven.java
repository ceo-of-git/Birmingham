package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.IBigBlockType;

// TODO: Make multiblocks expand their render bound thingies when they can.
public class CokingOven extends BaseEntityBlock implements IBigBlockType {

	public CokingOven(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CokingOvenEntity(pPos, pState);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@SuppressWarnings("deprecation") // booooring! dont care.
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
	    super.onPlace(state, level, pos, oldState, movedByPiston);

	    if (!level.isClientSide) {
	        if (CanPlace(level, pos)) {
	        	// cancel event ?
	        	// no clue where to go from here tbh.
	        }
	    }
	}
	
	@Override
	public int GetModelExtensionX() {
		return 1;
	}

	@Override
	public int GetModelExtensionY() {
		return 1;
	}

	@Override
	public int GetModelExtensionZ() {
		return 1;
	}
	

}
