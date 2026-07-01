package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;

public class FuelGeneratorMenu extends AbstractContainerMenu {

	private static final int GUI_SLOTCOUNT = 1;
    private final ContainerLevelAccess access;
    private final Container container;
    private boolean isToggled = true;
    private boolean isSmelting = false;
    
    private final BlockPos pos;
    private final Level level;
    private final ContainerData data;
    
    // "Menu Constructor"
    public FuelGeneratorMenu(int containerId, Inventory inventory, BlockPos pos, Container container, ContainerData data) {
        super(InventoryRegistry.FUEL_GENERATOR_MENU.get(), containerId);
        
        this.container = container;
        this.access = ContainerLevelAccess.create(inventory.player.level(), pos);
        
        this.pos = pos;
        this.level = inventory.player.level();
        
        isToggled = true;
        isSmelting = false;
        
        addSlot(new Slot(this.container, 0, 62, 54)); // input
		this.data = data;
        
		addDataSlots(data);
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }
    
    // "Normal Constructor" -- A simple redirect to the menu constructor
    public FuelGeneratorMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, pos, getContainer(inventory, pos), getData(inventory, pos));
    }
    
    // "Network Constructor" -- Called upon registry.
    public FuelGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readBlockPos());
    }
    
    public boolean getToggled() {
    	return isToggled;
    }
    
    public boolean getSmelting() {
    	return isSmelting;
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
    
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < 1) {
            if (!this.moveItemStackTo(stack, 2, this.slots.size(), true)) return ItemStack.EMPTY;
        } else {
            if (!this.moveItemStackTo(stack, 0, 2, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }
    
    private static Container getContainer(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof CokingOvenBlockEntity be) {
            return be;
        }

        return new SimpleContainer(1);
    }

    private static ContainerData getData(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof CokingOvenBlockEntity be) {
            return be.getData();
        }

        return new SimpleContainerData(1);
    }

	@Override
	public boolean stillValid(Player pPlayer) {
		return true;
	}
}
