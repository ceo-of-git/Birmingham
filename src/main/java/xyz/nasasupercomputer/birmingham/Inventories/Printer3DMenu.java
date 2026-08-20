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
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D.Printer3DBlockEntity;
import xyz.nasasupercomputer.birmingham.ModTagRegistry;

// Menus are the BACKEND of guis, run server-side and whatnot.
public class Printer3DMenu extends AbstractContainerMenu {

	private static final int GUI_SLOTCOUNT = 2;
    private final ContainerLevelAccess access;
    private final Container container;

    private final BlockPos pos;
    private final Level level;
    private final ContainerData data;

    // "Menu Constructor"
    public Printer3DMenu(int containerId, Inventory inventory, BlockPos pos, Container container, ContainerData data) {
        super(InventoryRegistry.PRINTER_3D_MENU.get(), containerId);

        this.container = container;
        this.access = ContainerLevelAccess.create(inventory.player.level(), pos);

        this.pos = pos;
        this.level = inventory.player.level();

        addSlot(new Slot(this.container, 0, 5, 5)); // input
        addSlot(new Slot(this.container, 1, 5, 23)); // input 2
        addSlot(new Slot(this.container, 2, 5, 41)); // input 3
        addSlot(new Slot(this.container, 3, 146, 35)); // output
        addSlot(new Slot(this.container, 4, 146, 53)); // output 2
		this.data = data;

		addDataSlots(data);
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    // "Normal Constructor" -- A simple redirect to the menu constructor
    public Printer3DMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, pos, getContainer(inventory, pos), getData(inventory, pos));
    }

    // "Network Constructor" -- Called upon registry.
    public Printer3DMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
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
        if (inventory.player.level().getBlockEntity(pos) instanceof Printer3DBlockEntity be) {
            return be;
        }

        return new SimpleContainer(2);
    }

    private static ContainerData getData(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof Printer3DBlockEntity be) {
            return be.getData();
        }

        return new SimpleContainerData(2);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // TODO: Shift clicking items to and from 3d printers
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
    	return true;
    }
}