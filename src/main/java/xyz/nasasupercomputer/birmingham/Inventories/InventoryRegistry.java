package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class InventoryRegistry {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MainRegistry.MOD_ID);
	
	public static final RegistryObject<MenuType<CokingOvenMenu>> COKING_OVEN_MENU = MENUS.register("coking_oven", () -> IForgeMenuType.create((id, inv, pos) -> new CokingOvenMenu(id, inv, pos)));
	public static final RegistryObject<MenuType<AlloyBlastFurnaceMenu>> ALLOY_BLAST_MENU = MENUS.register("alloy_blast", () -> IForgeMenuType.create((id, inv, pos) -> new AlloyBlastFurnaceMenu(id, inv, pos)));

	// Link all Menus to their respective Screen
	@SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> MenuScreens.register(COKING_OVEN_MENU.get(), CokingOvenScreen::new));
		event.enqueueWork(() -> MenuScreens.register(ALLOY_BLAST_MENU.get(), AlloyBlastFurnaceScreen::new));
    }
}
