package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;

public class CokingOvenBlockEntity extends BlockEntity {

	public CokingOvenBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.COKING_OVEN_ENTITY.get(), pos, state);
	}
}