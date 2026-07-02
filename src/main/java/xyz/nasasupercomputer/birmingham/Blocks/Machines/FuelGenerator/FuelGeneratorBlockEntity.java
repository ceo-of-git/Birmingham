package xyz.nasasupercomputer.birmingham.Blocks.Machines.FuelGenerator;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.IEnergyStorage;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenMenu;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenScreen;
import xyz.nasasupercomputer.birmingham.Inventories.FuelGeneratorMenu;
import xyz.nasasupercomputer.birmingham.Inventories.FuelGeneratorScreen;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

public class FuelGeneratorBlockEntity extends BlockEntity implements Container, IEnergyStorage  {

	private final SimpleContainer items = new SimpleContainer(1);
	public long remainingBurnTime = 0;
	
	private static final int MAX_ENERGY = 10000;
	public long currentEnergy = 0;
	
	// private float progressMultiplier = 1.0f; TODO: Overhaul Configs & make this configurable.
    
	public FuelGeneratorBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.FUEL_GENERATOR_ENTITY.get(), pos, state);
	}

	// Runs every tick, handles recipe progression and stuff.
    public static void tick(Level level, BlockPos pos, BlockState state, FuelGeneratorBlockEntity blockEntity) {

    	ItemStack fuelItem = blockEntity.getItem(0);
    	if (fuelItem != ItemStack.EMPTY) {
    		int burnTimeToAdd = ForgeHooks.getBurnTime(blockEntity.getItem(0), RecipeType.SMELTING);
    		
    		blockEntity.remainingBurnTime += burnTimeToAdd;
    		blockEntity.removeItem(0, 1);
    	}
    	
    	if (blockEntity.remainingBurnTime > 0) {
    		
			blockEntity.currentEnergy = Math.min(blockEntity.currentEnergy + 400, MAX_ENERGY);
			blockEntity.remainingBurnTime--;
    	}
    	

        blockEntity.setChanged();
    }
    
    // Stuff for opening the menu :?
    public static MenuProvider createMenuProvider(BlockPos pos) {
        return new SimpleMenuProvider((id, inv, player) -> new FuelGeneratorMenu(id, inv, pos), FuelGeneratorScreen.GUI_TITLE);
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
    
    // bunch o' balony energy edition
	@Override public int receiveEnergy(int maxReceive, boolean simulate) { return 0; }
	@Override public int extractEnergy(int maxExtract, boolean simulate) { return 0; }
	@Override public int getEnergyStored() { return (int)currentEnergy; }
	@Override public int getMaxEnergyStored() { return MAX_ENERGY; }
	@Override public boolean canExtract() { return true; }
	@Override public boolean canReceive() { return false; }
}
