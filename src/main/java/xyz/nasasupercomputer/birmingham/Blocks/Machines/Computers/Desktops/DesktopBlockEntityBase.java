package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopChair;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopProperties;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlockEntity;

public abstract class DesktopBlockEntityBase extends BlockEntity {

    public DesktopProperties DESKTOP_PROPERTIES;

    public DesktopBlockEntityBase(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public DesktopProperties GetProperties() {
        return DESKTOP_PROPERTIES;
    }
}
