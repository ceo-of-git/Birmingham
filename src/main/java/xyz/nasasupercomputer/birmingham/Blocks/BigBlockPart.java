package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;


// Lives to serve the eternal coking oven
public class BigBlockPart extends Block {

	public static final IntegerProperty OFFSET_X = IntegerProperty.create("master_x_offset", 0, 1);
	public static final IntegerProperty OFFSET_Y = IntegerProperty.create("master_y_offset", 0, 1);
	public static final IntegerProperty OFFSET_Z = IntegerProperty.create("master_z_offset", 0, 1);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
    public BigBlockPart(Properties properties) {
        super(properties);
        
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OFFSET_X, 0).setValue(OFFSET_Y, 0).setValue(OFFSET_Z, 0));
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
