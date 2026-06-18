package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

public class CokingOvenBlockEntity extends BlockEntity implements Container {

	private final SimpleContainer items = new SimpleContainer(2);
    
	public CokingOvenBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.COKING_OVEN_ENTITY.get(), pos, state);
	}

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", items.createTag());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.fromTag(tag.getList("inventory", 10));
    }

	// bunch of balony
    @Override public int getContainerSize() { return items.getContainerSize(); }
    @Override public boolean isEmpty() { return items.isEmpty(); }
    @Override public ItemStack getItem(int slot) { return items.getItem(slot); }
    @Override public ItemStack removeItem(int slot, int amount) { return items.removeItem(slot, amount); }
    @Override public ItemStack removeItemNoUpdate(int slot) { return items.removeItemNoUpdate(slot); }
    @Override public void setItem(int slot, ItemStack stack) { items.setItem(slot, stack); }
    @Override public boolean stillValid(Player player) { return true; }
    @Override public void clearContent() { items.clearContent(); }
}