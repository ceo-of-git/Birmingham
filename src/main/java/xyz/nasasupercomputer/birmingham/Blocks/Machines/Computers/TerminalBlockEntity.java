package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;

public class TerminalBlockEntity extends BlockEntity {
	
	public Boolean isToggled = true; // Whether the monitor will turn on if conditions are met (Manually controlled)
	private static int timer;
	IDesktopType supportingDesktop;

	public TerminalBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(BlockRegistry.TERMINAL_ENTITY.get(), pPos, pBlockState);
	}

    // Runs every tick, handles recipe progression and stuff.
    public static void tick(Level level, BlockPos pos, BlockState state, TerminalBlockEntity blockEntity) {
    	timer++;
    	
    	if (timer >= 20) {
    		BlockEntity blockBelow = level.getBlockEntity(pos.below());
    		
    		if (blockBelow instanceof IDesktopType desktopBE) {
    			// TODO: Add power check for Desktop
    			
    			if (blockEntity.isToggled) {
    				level.setBlock(pos, state.setValue(TerminalBlock.ACTIVE, true), Block.UPDATE_ALL);
    				blockEntity.supportingDesktop = desktopBE;
    			}
    			
    		}
    		else {
    			level.setBlock(pos, state.setValue(TerminalBlock.ACTIVE, false), Block.UPDATE_ALL);
    			blockEntity.supportingDesktop = null;
    		}
    		
    		
    		timer = 0;
    	}
        blockEntity.setChanged();
    }
    
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch(index) {
                case 0 -> 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
            	// case 0 -> progress = value;
            	// case 1 -> maxProgress = value;
            };
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ContainerData getData() {
        return data;
    }
    
}
