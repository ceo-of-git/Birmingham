package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;


// Lives to serve the eternal coking oven
public class BigBlockPart extends Block {

	// the second number (1, in this case) is the largest size - 1 that a "bigblock" can be.
	// so right now this means the biggest bigblock is a 2x2x2 one.
	// 
	// KEEP IN MIND: Changing this number can have DIRE results...
	// Minecraft works by "pseudo-generating" every single kind of a block when you're loading the game
	// Any high number (id say above 10 maybe) will make the game try to generate 10x10x10x4 blockstates (x4 for facing)
	// A.K.A, it aint gonna be fun
	//
	// If Possible, keep this # as LOW AS POSSIBLE!!!
	
	public static final IntegerProperty OFFSET_X = IntegerProperty.create("master_x_offset", 0, 1);
	public static final IntegerProperty OFFSET_Y = IntegerProperty.create("master_y_offset", 0, 1);
	public static final IntegerProperty OFFSET_Z = IntegerProperty.create("master_z_offset", 0, 1);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
    public BigBlockPart(Properties properties) {
        super(properties);
        
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OFFSET_X, 0).setValue(OFFSET_Y, 0).setValue(OFFSET_Z, 0));
    }

    // Right-Click Use
    // Redirects all usage to the master block, whatever it may be
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        
        // If the master block exists, pretend you actually just used that block
        BlockPos masterBlockPos = getMasterPos(pos, state, state.getValue(BigBlockPart.FACING));
        BlockEntity masterBlock = level.getBlockEntity(masterBlockPos);
        
        if (masterBlock != null) {
        	masterBlock.getBlockState().use(level, player, hand, hit);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    

    // Setup Blockstates
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OFFSET_X, OFFSET_Y, OFFSET_Z, FACING);
    }

    // Make the block invisible
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
    
    // Remove block destruction overlay
    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return 0.0F;
    }
    
    // Remove destruction particles
    @Override
    protected void spawnDestroyParticles(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState) {
        // pLevel.levelEvent(pPlayer, 2001, pPos, getId(pState));
     }
    
    
    public static BlockPos getMasterPos(BlockPos partPos, BlockState state, Direction facing) {

        int x = state.getValue(OFFSET_X);
        int y = state.getValue(OFFSET_Y);
        int z = state.getValue(OFFSET_Z);

        BlockPos rotated = IBigBlockType.rotateOffset(x, y, z, facing);

        return partPos.subtract(rotated);
    }

    // Destroy the whole if a part is gone.
    @Override
    @SuppressWarnings("unused")
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(level, pos, state, player);

        if (!level.isClientSide) {
            BlockPos master = getMasterPos(pos, state, state.getValue(FACING));

            if (master != null && level.getBlockState(master).getBlock() instanceof IBigBlockType multiblock) {
                level.destroyBlock(master, !player.isCreative());
            }
        }
    }
}
