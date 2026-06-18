package xyz.nasasupercomputer.birmingham.Inventories;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;

// Menus are the BACKEND of guis, run server-side and whatnot.
public class CokingOvenMenu extends AbstractContainerMenu {

	private static final int GUI_SLOTCOUNT = 2;
    private final ContainerLevelAccess access;
    private final Container container;
    
    private final BlockPos pos;
    private final Level level;
    
    // "Menu Constructor"
    public CokingOvenMenu(int containerId, Inventory inventory, BlockPos pos, Container container) {
        super(InventoryRegistry.COKING_OVEN_MENU.get(), containerId);
        
        this.container = container;
        this.access = ContainerLevelAccess.create(inventory.player.level(), pos);
        
        this.pos = pos;
        this.level = inventory.player.level();
        
        addSlot(new Slot(this.container, 0, 26, 36)); // input
        addSlot(new Slot(this.container, 1, 98, 36)); // output
        
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }
    
    // "Normal Constructor" -- A simple redirect to the menu constructor
    public CokingOvenMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, pos, inventory.player.level().getBlockEntity(pos) instanceof CokingOvenBlockEntity blockEntity ? blockEntity : new SimpleContainer(2));
    }
    
    // "Network Constructor" -- Called upon registry.
    public CokingOvenMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readBlockPos());
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
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

    @Override
    public boolean stillValid(Player player) {
    	return true;
    }
}