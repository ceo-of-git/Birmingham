package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class InventoryRegistry {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MainRegistry.MOD_ID);
}
