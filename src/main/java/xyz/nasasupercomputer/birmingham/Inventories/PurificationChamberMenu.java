package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.PurificationChamber.PurificationChamberBlockEntity;

// Menus are the BACKEND of guis, run server-side and whatnot.
public class PurificationChamberMenu extends AbstractContainerMenu {

	private static final int GUI_SLOTCOUNT = 4;
    private final ContainerLevelAccess access;
    private final Container container;

    private final BlockPos pos;
    private final Level level;
    private final ContainerData data;

    // "Menu Constructor"
    public PurificationChamberMenu(int containerId, Inventory inventory, BlockPos pos, Container container, ContainerData data) {
        super(InventoryRegistry.PURIFICATION_CHAMBER_MENU.get(), containerId);

        this.container = container;
        this.access = ContainerLevelAccess.create(inventory.player.level(), pos);

        this.pos = pos;
        this.level = inventory.player.level();

        addSlot(new FluidContainerSlot(this.container, 0, 26, 52)); // input
        addSlot(new FluidContainerSlot(this.container, 1, 134, 52)); // output
		this.data = data;

		addDataSlots(data);
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    // "Normal Constructor" -- A simple redirect to the menu constructor
    public PurificationChamberMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, pos, getContainer(inventory, pos), getData(inventory, pos));
    }

    // "Network Constructor" -- Called upon registry.
    public PurificationChamberMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readBlockPos());
    }

    private void addPlayerInventory(Inventory playerInventory) {
    	int offsetX = 9;
    	int offsetY = 103;
    	
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + offsetX, 8 + l * 18, offsetY + i * 18));
            }
        }
    }
    
    private void addPlayerHotbar(Inventory playerInventory) {
    	int offsetX = 8;
    	int offsetY = 161;
    	
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, offsetX + i * 18, offsetY));
        }
    }


    // Returns how far done the recipe is (mostly for progress bar)
    public int getScaledProgress() {

        int progress = data.get(0);
        int maxProgress = data.get(1);

        int progressBarSize = 86;

        return maxProgress > 0 ? progress * progressBarSize / maxProgress : 0;
    }

    private static Container getContainer(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof PurificationChamberBlockEntity be) {
            return be;
        }

        return new SimpleContainer(2);
    }

    private static ContainerData getData(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof PurificationChamberBlockEntity be) {
            return be.getData();
        }

        return new SimpleContainerData(2);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < 2) {
            if (!this.moveItemStackTo(stack, 2, this.slots.size(), true)) return ItemStack.EMPTY;
        } else {
            if (!this.moveItemStackTo(stack, 0, 2, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }

    public PurificationChamberBlockEntity getBlockEntity() {
        BlockEntity be = this.level.getBlockEntity(this.pos); // copy pasted these two lines i have NO idea how they work
        return be instanceof PurificationChamberBlockEntity custom ? custom : null;
    }
    @Override
    public boolean stillValid(Player player) {
    	return true;
    }

}