package xyz.nasasupercomputer.birmingham.Inventories;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

// Menus are the BACKEND of guis, run server-side and whatnot.
public class CokingOvenMenu extends AbstractContainerMenu {

    private final BlockPos pos;
    private final Level level;
    
    // "Normal Constructor"
    public CokingOvenMenu(int containerId, Inventory inventory, BlockPos pos) {
        super(InventoryRegistry.COKING_OVEN_MENU.get(), containerId);
        
        this.pos = pos;
        this.level = inventory.player.level();
    }
    
    // "Network Constructor"
    public CokingOvenMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
		super(InventoryRegistry.COKING_OVEN_MENU.get(), id);

        this.pos = buf.readBlockPos();
        this.level = inventory.player.level();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}