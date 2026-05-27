package xyz.nasasupercomputer.birmingham.ItemGroups;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

public class ItemGroupRegistry {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MOD_ID);
	
	// =========================
	// ITEM GROUPS
	
    // Machines Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_MACHINES = CREATIVE_MODE_TABS.register("creative_tab_machines", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TEST_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_machines"))
            .build());
    
    // Resources Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_RESOURCES = CREATIVE_MODE_TABS.register("creative_tab_resources", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TEST_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_resources"))
            .build());
    
    //GEM ALARM
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_MODULES = CREATIVE_MODE_TABS.register("creative_tab_modules", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TEST_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_modules"))
            .build());
    
    // Building Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_BUILDING = CREATIVE_MODE_TABS.register("creative_tab_building", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TEST_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_building"))
            .build());
    
    // Combat Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_TOOLS = CREATIVE_MODE_TABS.register("creative_tab_tools", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TEST_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_tools"))
            .build());
    
	// =========================
	// ADDING ITEMS TO ITEM GROUPS
    public static void AddItemToTab(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CREATIVE_TAB_RESOURCES.getKey() || 
        		event.getTabKey() == ItemGroupRegistry.CREATIVE_TAB_MACHINES.getKey() || 
        		event.getTabKey() == ItemGroupRegistry.CREATIVE_TAB_BUILDING.getKey()) {
        	event.accept(ItemRegistry.TEST_ITEM);
        
        }
        
        // ADDING TO TOOLS TAB
        if (event.getTabKey() == CREATIVE_TAB_TOOLS.getKey()) {
            event.accept(ItemRegistry.LEATHER_GLOVES);
            event.accept(ItemRegistry.CREATIVE_GLOVES);
            event.accept(ItemRegistry.ENERGY_DRINK);
        }
        
        // ADDING TO MACHINES TAB
        if (event.getTabKey() == CREATIVE_TAB_MACHINES.getKey()) {
        	event.accept(BlockRegistry.COKING_OVEN_ITEM);
        }
        
     // ADDING TO MODULES TAB
        if (event.getTabKey() == CREATIVE_TAB_MODULES.getKey()) {
        	event.accept(ItemRegistry.FLAME_GEM);
        	event.accept(Items.AMETHYST_SHARD);
        	event.accept(ItemRegistry.VAMPIRE_GEM);
        }
    }
}
