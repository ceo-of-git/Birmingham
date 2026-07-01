package xyz.nasasupercomputer.birmingham.Blocks.Machines.FuelGenerator;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenMenu;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenScreen;
import xyz.nasasupercomputer.birmingham.Inventories.FuelGeneratorMenu;
import xyz.nasasupercomputer.birmingham.Inventories.FuelGeneratorScreen;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

public class FuelGeneratorBlockEntity extends BlockEntity implements Container  {

	private final SimpleContainer items = new SimpleContainer(2);
	public int progress = 0;
	public int maxProgress = 1200;
	// private float progressMultiplier = 1.0f; TODO: Overhaul Configs & make this configurable.
    
	public FuelGeneratorBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.FUEL_GENERATOR_ENTITY.get(), pos, state);
	}

	// Runs every tick, handles recipe progression and stuff.
    public static void tick(Level level, BlockPos pos, BlockState state, CokingOvenBlockEntity blockEntity) {

//        Optional<RecipeTypeCokingOven> recipe = blockEntity.getCurrentRecipe();
//
//        if (recipe.isPresent() && blockEntity.canCraft(recipe.get())) {
//            blockEntity.progress += 1;
//
//            if (blockEntity.progress >= blockEntity.maxProgress) {
//                blockEntity.craftItem(recipe.get());
//                blockEntity.progress = 0;
//            }
//            
//        } else {
//            blockEntity.progress = 0;
//        }

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
}
