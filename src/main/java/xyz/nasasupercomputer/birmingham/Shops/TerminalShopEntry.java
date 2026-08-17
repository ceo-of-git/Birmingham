package xyz.nasasupercomputer.birmingham.Shops;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record TerminalShopEntry(
        ItemStack itemToPurchase,       // the item to buy
        Float dollarCost,               // How much $$$ does it cost
        int powerRequirementToView      // How much Computing power the PC needs to see this item in the shop
){}
