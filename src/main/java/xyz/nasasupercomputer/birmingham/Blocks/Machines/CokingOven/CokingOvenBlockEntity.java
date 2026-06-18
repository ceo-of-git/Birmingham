package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;

public class CokingOvenBlockEntity extends BlockEntity {

    private final ItemStackHandler Inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    
	public CokingOvenBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.COKING_OVEN_ENTITY.get(), pos, state);
	}
	
    public ItemStackHandler getInventory() {
        return Inventory;
    }
}