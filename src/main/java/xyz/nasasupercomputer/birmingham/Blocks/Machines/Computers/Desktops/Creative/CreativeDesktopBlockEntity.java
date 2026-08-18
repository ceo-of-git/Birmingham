package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Creative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopChair;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopProperties;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.DesktopBlockEntityBase;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.IDesktopType;

public class CreativeDesktopBlockEntity extends DesktopBlockEntityBase implements IDesktopType {

	public final DesktopProperties DESKTOP_PROPERTIES;

	public CreativeDesktopBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(BlockRegistry.CREATIVE_DESKTOP_ENTITY.get(), pPos, pBlockState);
		this.DESKTOP_PROPERTIES = new DesktopProperties(0.0, 0.0, 1.0, false);
	}

	public CreativeDesktopBlockEntity(BlockPos pPos, BlockState pBlockState, DesktopProperties desktopProperties) {
		super(BlockRegistry.CREATIVE_DESKTOP_ENTITY.get(), pPos, pBlockState);
		this.DESKTOP_PROPERTIES = desktopProperties;
	}

	@Override
	public DesktopProperties GetProperties() {
		BlockPos pos = this.worldPosition;
		BlockEntity blockEntity = level.getBlockEntity(pos);

		DesktopProperties properties = DESKTOP_PROPERTIES;

		// Check for an account for gaming chairs
		if (blockEntity instanceof DesktopBlockEntityBase desktopBE) {
			Direction direction = desktopBE.getBlockState().getValue(DesktopBlock.FACING);
			BlockPos pleasebeachair = pos.relative(direction, 1);

			if (level.getBlockState(pleasebeachair).getBlock() instanceof DesktopChair itisachair) {
				properties = new DesktopProperties(properties.computePower() * itisachair.getPowerBoostPercent(), properties.computeSpeed() * itisachair.getSpeedBoostPercent(), properties.powerEfficiency() * itisachair.getEfficiencyBoostPercent(), properties.hasGuiSupport());
			}
		}



		return properties;
	}

}
