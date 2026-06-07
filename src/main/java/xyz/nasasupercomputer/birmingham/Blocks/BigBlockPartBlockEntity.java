package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

// maybe bigblock wasn't the way to go when naming this...
public class BigBlockPartBlockEntity extends BlockEntity {
	
	private BlockPos masterPos;
	
	
    public BigBlockPartBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

    public void setMasterPos(BlockPos pos) {
        this.masterPos = pos;
    }

    public BlockPos getMasterPos() {
        return masterPos;
    }
}
