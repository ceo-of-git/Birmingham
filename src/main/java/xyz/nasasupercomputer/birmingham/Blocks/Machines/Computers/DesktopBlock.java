package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

import javax.swing.text.html.BlockView;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkHooks;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.IBigBlockType;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlockEntity;


public class DesktopBlock extends BaseEntityBlock implements IDesktopType {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty RENDER_ACTIVE = BooleanProperty.create("render_active");
    public static final BooleanProperty HAS_TABLE = BooleanProperty.create("has_table");
    public static final DesktopProperties DESKTOP_PROPERTIES = new DesktopProperties(16.0, 0.5, 0.8, false); // (Compute Power #, Speed Mult, Power use Mult, Terminal Style / GUI Style)
    
    private static final VoxelShape AABB_NORTH = Shapes.or(Block.box(3, 0, 0, 13, 13, 16)); // Custom Bounding Box
    private static final VoxelShape AABB_SOUTH = BlockRegistry.rotateShape(Direction.NORTH, Direction.SOUTH, AABB_NORTH);
    private static final VoxelShape AABB_EAST  = BlockRegistry.rotateShape(Direction.NORTH, Direction.EAST, AABB_NORTH);
    private static final VoxelShape AABB_WEST  = BlockRegistry.rotateShape(Direction.NORTH, Direction.WEST, AABB_NORTH);
    private static final VoxelShape AABB_WITH_TABLE = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    
	public DesktopBlock(Properties pProperties) {
		super(pProperties);
		
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

    // Right-Click Use
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    	// Removing Table
        if (player.isShiftKeyDown()) {
    		if (level.getBlockState(pos).getValue(HAS_TABLE) == true) {
    			if (!level.isClientSide()) { level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.TABLE_ITEM.get()))); }
    			level.setBlock(pos, state.setValue(HAS_TABLE, false), Block.UPDATE_ALL);
    		}
    		else {
    			return InteractionResult.PASS;
    		}
        }
        
        // Place table
        if (player.getItemInHand(hand).getItem() == BlockRegistry.TABLE_ITEM.get()) {
        	level.setBlock(pos, state.setValue(HAS_TABLE, true), Block.UPDATE_ALL);
        }
        
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {

		// Checks if the block is actaully being changed/removed
		// Since normally right-clicking with a table would count as "removing" the original desktop
		if (oldState.getBlock() != newState.getBlock()) {
			// Drop Table if tabled
			if (oldState.getValue(HAS_TABLE) == true) {
				oldState.setValue(HAS_TABLE, false);
				if (!level.isClientSide()) { level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.TABLE_ITEM.get()))); }
			}
		}

	    super.onRemove(oldState, level, pos, newState, isMoving);
	}
    
	// Create the block state
    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> builder) {
        builder.add(FACING, HAS_TABLE);
    }
    
	@Override
	public RenderShape getRenderShape(BlockState state) {
	    return RenderShape.MODEL;
	}
	
	// Check which state (Direction) to face the block when placed
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = defaultBlockState().setValue(FACING, facing).setValue(HAS_TABLE, false);

        return state;
    }
    
    // Link this to a Desktop Block-Entity
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	    return new DesktopBlockEntity(pos, state, DESKTOP_PROPERTIES);
	}
	
	
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

    	if (state.getValue(HAS_TABLE)) {
    		return AABB_WITH_TABLE;
    	}
    	else {
            return switch (state.getValue(FACING)) {
	            case NORTH -> AABB_NORTH;
	            case SOUTH -> AABB_SOUTH;
	            case EAST  -> AABB_EAST;
	            case WEST  -> AABB_WEST;
	            default    -> AABB_NORTH;
            };
    	}
    }

	@Override
	public DesktopProperties GetProperties() {
		return DESKTOP_PROPERTIES;
	}
	
	

}
