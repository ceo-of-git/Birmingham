package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;


// Lives to serve the eternal coking oven
public class BigBlockPart extends Block {

	public static final IntegerProperty OFFSET_X = IntegerProperty.create("master_x_offset", 0, 1);
	public static final IntegerProperty OFFSET_Y = IntegerProperty.create("master_y_offset", 0, 1);
	public static final IntegerProperty OFFSET_Z = IntegerProperty.create("master_z_offset", 0, 1);
	
    public BigBlockPart(Properties properties) {
        super(properties);
        
        registerDefaultState(this.stateDefinition.any().setValue(OFFSET_X, 0).setValue(OFFSET_Y, 0).setValue(OFFSET_Z, 0));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OFFSET_X, OFFSET_Y, OFFSET_Z);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
    
    // getmasterpos but it actually respecets directions..... .. .. . . i think
    public static BlockPos getMaster(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof BigBlockPart)) return null;

        for (Direction dir : Direction.values()) {
            BlockState adjacent = level.getBlockState(pos.relative(dir));

            if (adjacent.getBlock() instanceof CokingOvenBlock oven) {

                Direction facing = adjacent.getValue(CokingOvenBlock.FACING);

                return BigBlockPart.getMasterPos(pos, state, facing, oven);
            }
        }

        return null;
    }
    
    public static BlockPos getMasterPos(BlockPos partPos, BlockState state, Direction facing, CokingOvenBlock oven) {

        int x = state.getValue(OFFSET_X);
        int y = state.getValue(OFFSET_Y);
        int z = state.getValue(OFFSET_Z);

        BlockPos rotated = oven.rotateOffset(x, y, z, facing);

        return partPos.subtract(rotated);
    }

    // Destroy the whole if a part is gone.
//    @Override
//    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
//        super.playerWillDestroy(level, pos, state, player);
//
//        if (!level.isClientSide) {
//            BlockPos master = findMaster(level, pos);
//
//            if (master != null && level.getBlockState(master).getBlock() instanceof CokingOvenBlock oven) {
//                level.destroyBlock(master, !player.isCreative());
//            }
//        }
//    }
}
