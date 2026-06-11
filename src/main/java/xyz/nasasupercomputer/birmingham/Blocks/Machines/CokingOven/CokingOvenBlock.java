package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import xyz.nasasupercomputer.birmingham.Blocks.BigBlockPart;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.IBigBlockType;


public class CokingOvenBlock extends BaseEntityBlock implements IBigBlockType {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty RENDER_ACTIVE = BooleanProperty.create("render_active");
    
	public CokingOvenBlock(Properties pProperties) {
		super(pProperties);
		
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
	    return RenderShape.MODEL;
	}

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction facing = context.getHorizontalDirection().getOpposite();

        BlockState state = defaultBlockState().setValue(FACING, facing);

        if (!canPlace( state, context.getLevel(), context.getClickedPos(), facing)) {
            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }
    
    // google tells me to do this i am SO confused bro.
    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> builder) {
        builder.add(FACING);
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

	                level.setBlock(partPos, BlockRegistry.BIGBLOCK_PART.get().defaultBlockState().setValue(BigBlockPart.OFFSET_X, x).setValue(BigBlockPart.OFFSET_Y, y).setValue(BigBlockPart.OFFSET_Z, z), 3);
	            }
	        }
	    }
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {

	    if (!state.is(newState.getBlock())) {

	        Direction facing = state.getValue(FACING);

	        for (int x = 0; x < GetSizeX(); x++) {
	            for (int y = 0; y < GetSizeY(); y++) {
	                for (int z = 0; z < GetSizeZ(); z++) {

	                    if (x == 0 && y == 0 && z == 0)
	                        continue;

	                    BlockPos offset = rotateOffset(x, y, z, facing);
	                    BlockPos partPos = pos.offset(offset);

	                    if (level.getBlockState(partPos).is(BlockRegistry.BIGBLOCK_PART.get())) {
	                        level.destroyBlock(partPos, false);
	                    }
	                }
	            }
	        }
	    }

	    super.onRemove(state, level, pos, newState, isMoving);
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
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	    return new CokingOvenBlockEntity(pos, state);
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
