package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Custom.IrradiationProperties;

public class DesktopBlockEntity extends BlockEntity implements IDesktopType {
	
	public final DesktopProperties DESKTOP_PROPERTIES;

	public DesktopBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(BlockRegistry.DESKTOP_ENTITY.get(), pPos, pBlockState);
		this.DESKTOP_PROPERTIES = new DesktopProperties(0.0, 0.0, 1.0, false);
	}
	
	public DesktopBlockEntity(BlockPos pPos, BlockState pBlockState, DesktopProperties desktopProperties) {
		super(BlockRegistry.DESKTOP_ENTITY.get(), pPos, pBlockState);
		this.DESKTOP_PROPERTIES = desktopProperties;
	}

	@Override
	public DesktopProperties GetProperties() {
		BlockPos pos = this.worldPosition;
		BlockEntity blockEntity = level.getBlockEntity(pos);

		DesktopProperties properties = DESKTOP_PROPERTIES;

		// Check for an account for gaming chairs
		if (blockEntity instanceof DesktopBlockEntity desktopBE) {
			Direction direction = desktopBE.getBlockState().getValue(DesktopBlock.FACING);
			BlockPos pleasebeachair = pos.relative(direction, 1);

			if (level.getBlockState(pleasebeachair).getBlock() instanceof DesktopChair itisachair) {
				properties = new DesktopProperties(properties.computePower() * itisachair.getPowerBoostPercent(), properties.computeSpeed() * itisachair.getSpeedBoostPercent(), properties.powerEfficiency() * itisachair.getEfficiencyBoostPercent(), properties.hasGuiSupport());
			}
		}



		return properties;
	}

}
