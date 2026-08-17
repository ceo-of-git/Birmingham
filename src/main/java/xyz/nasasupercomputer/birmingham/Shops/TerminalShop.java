package xyz.nasasupercomputer.birmingham.Shops;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TerminalShop implements IShop {

    public static List<TerminalShopEntry> availableShopItems = new ArrayList<>();

    // Called upon in ForgeEventBusEvents (Genius file naming scheme)
    public static void addShopEntries(){
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CAPACITOR.get(), 3), 1.00f, 8));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T5.get(), 1), 9999.00f, 50));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.CIRCUIT_T3.get(), 1), 67.00f, 20));
        addShopEntry(new TerminalShopEntry(new ItemStack(ItemRegistry.TRANSISTOR.get(), 6), 2.00f, 8));

        // Sort by power requirement descending so that when you
        // upgrade your computer the new items appear first
        Collections.sort(availableShopItems, Collections.reverseOrder(Comparator.comparingInt(TerminalShopEntry::powerRequirementToView)));
    }

    public static List<TerminalShopEntry> getAvailableShopItems() { return availableShopItems; }

    private static void addShopEntry(TerminalShopEntry newEntry){
        availableShopItems.add(newEntry);
    }

}
