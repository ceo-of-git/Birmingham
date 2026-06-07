package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import xyz.nasasupercomputer.birmingham.Blocks.BigBlockPart;
import xyz.nasasupercomputer.birmingham.Blocks.BigBlockPartBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.IBigBlockType;


public class CokingOvenBlock extends BaseEntityBlock implements IBigBlockType {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty RENDER_ACTIVE = BooleanProperty.create("render_active");
    
    
	public CokingOvenBlock(Properties pProperties) {
		super(pProperties);
	}

	public boolean canPlaceAt(BlockState state, Level level, BlockPos pos) {
		return canPlace(state, level, pos);
	}
	
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
	    if (level.isClientSide()) { return; }
	    if (movedByPiston) { return; }
	    
	    for (int x = 0; x < GetSizeX(); x++) {
	        for (int y = 0; y < GetSizeY(); y++) {
	            for (int z = 0; z < GetSizeZ(); z++) {

	            	// Don't replace main block
	                if (x == 0 && y == 0 && z == 0)
	                    continue;

	                // Set every block to a bigblock part
	                Direction facing = state.getValue(FACING);
	                BlockPos offset = rotateOffset(x, y, z, facing);
	                
	                BlockPos partPos = pos.offset(offset);

	                level.setBlock(partPos, BlockRegistry.BIGBLOCK_PART.get().defaultBlockState(), 3);

	                // Set the part to lead back here.
	                BlockEntity partEntity = level.getBlockEntity(partPos);
	                if (partEntity instanceof BigBlockPartBlockEntity partBlockEntity) {
	                	partBlockEntity.setMasterPos(pos);
	                }
	            }
	        }
	    }
	}

	
	
	@SuppressWarnings("unused") // not a big fan of these warnings I must say
	private void removeAllParts(Level level, BlockPos origin) {
	    for (int x = 0; x < GetSizeX(); x++) {
	        for (int y = 0; y < GetSizeY(); y++) {
	            for (int z = 0; z < GetSizeZ(); z++) {

	                BlockPos pos = origin.offset(x, y, z);

	                level.destroyBlock(pos, false);
	            }
	        }
	    }
	}


	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int GetSizeX() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public int GetSizeY() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public int GetSizeZ() {
		// TODO Auto-generated method stub
		return 2;
	}
}
