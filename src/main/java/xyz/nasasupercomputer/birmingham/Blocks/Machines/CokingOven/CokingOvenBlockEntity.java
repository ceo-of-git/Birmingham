package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

public class CokingOvenBlockEntity extends BlockEntity implements Container {

	private final SimpleContainer items = new SimpleContainer(2);
	public int progress = 0;
	public int maxProgress = 1200;
	// private float progressMultiplier = 1.0f; TODO: Overhaul Configs & make this configurable.
    
	public CokingOvenBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.COKING_OVEN_ENTITY.get(), pos, state);
	}

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", items.createTag());
        tag.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.fromTag(tag.getList("inventory", 10));
        progress = tag.getInt("progress");
    }
    
    // Runs every tick, handles recipe progression and stuff.
    public static void tick(Level level, BlockPos pos, BlockState state, CokingOvenBlockEntity blockEntity) {

        Optional<RecipeTypeCokingOven> recipe = blockEntity.getCurrentRecipe();

        if (recipe.isPresent() && blockEntity.canCraft(recipe.get())) {
            blockEntity.progress += 1;

            if (blockEntity.progress >= blockEntity.maxProgress) {
                blockEntity.craftItem(recipe.get());
                blockEntity.progress = 0;
            }
            
        } else {
            blockEntity.progress = 0;
        }

        blockEntity.setChanged();
    }
    
    private Optional<RecipeTypeCokingOven> getCurrentRecipe() {
        if (level == null) { return Optional.empty(); }

        SimpleContainer inventory = new SimpleContainer(getContainerSize());

        for (int i = 0; i < getContainerSize(); i++) {
            inventory.setItem(i, getItem(i));
        }

        return level.getRecipeManager().getRecipeFor(RecipeTypeCokingOven.Type.INSTANCE, inventory, level);
    }
    
    private boolean canCraft(RecipeTypeCokingOven recipe) {

        ItemStack outputSlot = getItem(1);
        ItemStack recipeOutput = recipe.getResultItem(null);

        if (outputSlot.isEmpty()) { return true; }
        if (!ItemStack.isSameItem(outputSlot, recipeOutput)) { return false; }

        return outputSlot.getCount() + recipeOutput.getCount() <= outputSlot.getMaxStackSize();
    }
    
    private void craftItem(RecipeTypeCokingOven recipe) {
        removeItem(0, 1);

        ItemStack output = recipe.getResultItem(null);
        ItemStack outputSlot = getItem(1);

        if (outputSlot.isEmpty()) {
            setItem(1, output.copy());
        } else {
            outputSlot.grow(output.getCount());
        }
    }

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch(index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ContainerData getData() {
        return data;
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