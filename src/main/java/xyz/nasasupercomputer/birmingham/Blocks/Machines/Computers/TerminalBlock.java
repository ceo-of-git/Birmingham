package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlockEntity;
import xyz.nasasupercomputer.birmingham.Inventories.Computers.TerminalScreen;

public class TerminalBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty RENDER_ACTIVE = BooleanProperty.create("render_active");
    
    private static final VoxelShape AABB_NORTH = Shapes.or(Block.box(0, 0, 8, 16, 15, 16)); // Custom Bounding Box
    private static final VoxelShape AABB_SOUTH = BlockRegistry.rotateShape(Direction.NORTH, Direction.SOUTH, AABB_NORTH);
    private static final VoxelShape AABB_EAST  = BlockRegistry.rotateShape(Direction.NORTH, Direction.EAST, AABB_NORTH);
    private static final VoxelShape AABB_WEST  = BlockRegistry.rotateShape(Direction.NORTH, Direction.WEST, AABB_NORTH);
    
	public TerminalBlock(Properties pProperties) {
		super(pProperties);
		
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

    // Right-Click Use
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    	if (level.getBlockEntity(pos) instanceof TerminalBlockEntity terminalBE) {
    		if (level.getBlockState(pos).getValue(ACTIVE)) {
    			BlockEntity blockBelow = level.getBlockEntity(pos.below());
    			// Open GUI since Active
    			// add power check soon? not sure
        		
        		if (blockBelow instanceof DesktopBlockEntity desktopBE) {
        			// TODO: Add power check for Desktop
        			
        			if (desktopBE.DESKTOP_PROPERTIES.hasGuiSupport()) {
        				// Desktop has GUI Support, open that specific GUI
        				// TODO: Make an actual GUI that isnt the terminal
        			}
        			else {
        				// Desktop DOESNT have GUI support, open Terminal UI.
        		        // Open the screen here (on server only?)
        		        if (level.isClientSide()) {
        		        	Minecraft.getInstance().setScreen(new TerminalScreen(desktopBE));
        		        }
        			}
        			
        		}
    		}
    	}
        
        
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    
//    // Stuff for opening the menu :? (NOT REQUIRED FOR SCREENS)
//    public static MenuProvider createMenuProviderTerminal(BlockPos pos) {
//        return new SimpleMenuProvider((id, inv, player) -> new TerminalMenu(id, inv, pos), TerminalScreen.GUI_TITLE);
//    }

    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, BlockRegistry.TERMINAL_ENTITY.get(), TerminalBlockEntity::tick);
    }
    
	// Create the block state
    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }
    
	@Override
	public RenderShape getRenderShape(BlockState state) {
	    return RenderShape.MODEL;
	}
	
	// Check which state (Direction) to face the block when placed
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = defaultBlockState().setValue(FACING, facing).setValue(ACTIVE, false);

        return state;
    }
    
    // Link this to a Desktop Block-Entity
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	    return new TerminalBlockEntity(pos, state);
	}
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        return switch (state.getValue(FACING)) {
            case NORTH -> AABB_NORTH;
            case SOUTH -> AABB_SOUTH;
            case EAST  -> AABB_EAST;
            case WEST  -> AABB_WEST;
            default    -> AABB_NORTH;
        };
    }

}
