package xyz.nasasupercomputer.birmingham.Blocks.Machines.PurificationChamber;

import net.minecraft.core.BlockPos;
import net.minecraft.data.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

import javax.annotation.Nullable;
import java.util.Optional;

public class PurificationChamberBlockEntity extends BlockEntity implements Container {

	private final SimpleContainer items = new SimpleContainer(2);
	public int progress = 0;
	public int maxProgress = 1200;
	// private float progressMultiplier = 1.0f; TODO: Overhaul Configs & make this configurable.

	public PurificationChamberBlockEntity(BlockPos pos, BlockState state) {
	    super(BlockRegistry.PURIFICATION_CHAMBER_ENTITY.get(), pos, state);
	}

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", items.createTag());
        tag.putInt("progress", progress);
        tag.put("FluidTank1", this.fluidTank1.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.fromTag(tag.getList("inventory", 10));
        progress = tag.getInt("progress");
        this.fluidTank1.readFromNBT(tag.getCompound("FluidTank1"));

    }

    private final FluidTank fluidTank1 = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            PurificationChamberBlockEntity.this.sendUpdate();
        }
    };

    private final LazyOptional<FluidTank> fluidOptional = LazyOptional.of(() -> this.fluidTank1);

    private void sendUpdate() {
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }


    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return this.fluidOptional.cast();
        }
        return super.getCapability(cap);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.fluidOptional.invalidate();
    }

    // Runs every tick, handles recipe progression and stuff.
    public static void tick(Level level, BlockPos pos, BlockState state, PurificationChamberBlockEntity blockEntity) {

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
//
//        blockEntity.setChanged();

        if (level == null || level.isClientSide()) {
            return;
        }

        ItemStack stack = blockEntity.items.getItem(0); // first item
        if (stack.isEmpty()) {
            return; // do nothing if theres nothing in the bucket slot. TODO: MAKE IT SO IT CAN TAKE PIPED IN FLUIDS. HOW? NO IDEA.
        }
        if  (blockEntity.fluidTank1.getFluidAmount() >= blockEntity.fluidTank1.getCapacity()) { // dont do stuff if overfill
            return;
        }

        LazyOptional<IFluidHandlerItem> fluidHandler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM); // get the fluid handler capability of the item in the slot

        fluidHandler.ifPresent(iFluidHandlerItem -> { // if its a valid item
                    int amountToDrain = blockEntity.fluidTank1.getCapacity() - blockEntity.fluidTank1.getFluidAmount(); // get the amount to drain (capacity - amount = amount remaining to dfrain)

                    Fluid actualfluid1 = iFluidHandlerItem.drain(amountToDrain, IFluidHandler.FluidAction.SIMULATE).getFluid();
                    int amount = iFluidHandlerItem.drain(amountToDrain, IFluidHandler.FluidAction.SIMULATE).getAmount(); // i dont really understand this but drain removes fluids, i dont understand the simulate tho
                    // according to claude:
            // drain(maxdrain, fluidaction)
            // maxdrain (amounttodrain) is the max to remove. returned fluidstack is the fluid and the amount that was drained. the fluidaction.simulate means we are askingf "how much would be removed if we drained it?"
            // eg, if we ask for 1000mb from a tank but it only has 300mb, it returns 300mb


                    if (!blockEntity.fluidTank1.getFluid().getFluid().equals(actualfluid1) && !blockEntity.fluidTank1.isEmpty()) { // check if its the same fluid to prevent bugs
                        return;
                    }
                    if (amount > 0) { // if its not empty
                        blockEntity.fluidTank1.fill(iFluidHandlerItem.drain(amountToDrain, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE); // this time instead of simulating it, we actually do it

                        if (amount <= amountToDrain) {
                            blockEntity.items.setItem(0, iFluidHandlerItem.getContainer()); // if the amoubt from the container is less than the container size, we kill it
                        }
                    }
                }


                );




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

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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



    public FluidTank getFluidTank1() {
        return this.fluidTank1;
    }
    public LazyOptional<FluidTank> getFluidOptional() {
        return this.fluidOptional;
    }
}