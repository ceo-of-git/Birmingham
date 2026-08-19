package xyz.nasasupercomputer.birmingham.Shops;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class TerminalShop {

    public static List<TerminalShopEntry> availableShopItems = new ArrayList<>();

    // Called upon in ForgeEventBusEvents (Genius file naming scheme)
    public static void addShopEntries(){
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CAPACITOR.get(), 3), 1.00f, 8));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.TRANSISTOR.get(), 6), 2.00f, 8));
        addShopEntry(new TerminalShopEntry(new ItemStack(BlockRegistry.PRINTER_3D_ITEM.get(), 1), 32.00f, 8));
        addShopEntry(new TerminalShopEntry(new ItemStack(BlockRegistry.TERMINAL_ITEM.get(), 1), 60.00f, 8));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.EMPTY_CIRCUIT_BOARD.get(), 4), 4.00f, 8));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.STEEL_TONGS.get(), 1), 0.75f, 8));

        addShopEntry(new TerminalShopEntry(new ItemStack(Items.COAL, 8), 3.25f, 16));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.IRON_INGOT, 8), 4.00f, 16));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.GOLD_INGOT, 2), 4.00f, 16));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.EMERALD, 8), 2.25f, 16));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.AMETHYST_SHARD, 8), 2.25f, 16));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.DIAMOND, 1), 10.00f, 16));

        addShopEntry(new TerminalShopEntry(new ItemStack(Items.ENDER_PEARL, 4), 4.00f, 24));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.OCHRE_FROGLIGHT, 16), 4.00f, 24));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.VERDANT_FROGLIGHT, 16), 4.00f, 24));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.PEARLESCENT_FROGLIGHT, 16), 4.00f, 24));
        addShopEntry(new TerminalShopEntry(new ItemStack(Items.PEARLESCENT_FROGLIGHT, 16), 4.00f, 24));

        addShopEntry(new TerminalShopEntry(new ItemStack(BlockRegistry.DESKTOP_ITEM.get(), 1), 100.00f, 32));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.GEIGER_COUNTER.get(), 1), 100.00f, 32));

        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T1.get(), 1), 30.00f, 16));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T2.get(), 1), 90.00f, 48));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T3.get(), 1), 200.00f, 128));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T4.get(), 1), 3000.00f, 512));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T5.get(), 1), 10000.00f, 1024));

        addShopEntry(new TerminalShopEntry(new ItemStack(Items.DRAGON_EGG, 1), 16000.00f, 1024));

        // Sort by power requirement descending so that when you
        // upgrade your computer the new items appear first
        Collections.sort(availableShopItems, Collections.reverseOrder(Comparator.comparingInt(TerminalShopEntry::powerRequirementToView)));
    }

    public static List<TerminalShopEntry> getAvailableShopItems() { return new ArrayList<>(availableShopItems); }
    public static List<TerminalShopEntry> getAvailableShopItems(double desktopPower) {
        List<TerminalShopEntry> toReturn = new ArrayList<>(getAvailableShopItems());
        toReturn.removeIf(entry -> entry.powerRequirementToView() > desktopPower);
        Collections.sort(toReturn, Comparator.comparingInt(TerminalShopEntry::powerRequirementToView));

        return toReturn;
    }

    private static void addShopEntry(TerminalShopEntry newEntry){
        availableShopItems.add(newEntry);
    }

}
