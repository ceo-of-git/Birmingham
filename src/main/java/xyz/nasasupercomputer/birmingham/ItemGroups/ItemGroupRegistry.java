package xyz.nasasupercomputer.birmingham.ItemGroups;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.Fluids.FluidRegistry;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Datagen.SavedData.PillData;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.Items.custom.PillItem;
import xyz.nasasupercomputer.birmingham.Materials.MaterialSetRecord;
import xyz.nasasupercomputer.birmingham.Materials.MaterialSetRegistry;

import java.util.List;

public class ItemGroupRegistry {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MOD_ID);
	
	// =========================
	// ITEM GROUPS
	
    // Machines Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_MACHINES = CREATIVE_MODE_TABS.register("creative_tab_machines", () -> CreativeModeTab.builder()
            .icon(() -> BlockRegistry.COKING_OVEN_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_machines"))
            .build());
    
    // Resources Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_RESOURCES = CREATIVE_MODE_TABS.register("creative_tab_resources", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.COAL_COKE.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_resources"))
            .build());
    
    //GEM ALARM
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_MODULES = CREATIVE_MODE_TABS.register("creative_tab_modules", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.FLAME_GEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_modules"))
            .build());
    
    // Building Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_BUILDING = CREATIVE_MODE_TABS.register("creative_tab_building", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TEST_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_building"))
            .build());
    
    // Combat Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_TOOLS = CREATIVE_MODE_TABS.register("creative_tab_tools", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.LEATHER_GLOVES.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_tools"))
            .build());
    
    // Misc Tab
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB_MISC = CREATIVE_MODE_TABS.register("creative_tab_misc", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.MYSTERIOUS_PILL.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.birmingham.creative_tab_misc"))
            .build());
    
	// =========================
	// ADDING ITEMS TO ITEM GROUPS
    public static void AddItemToTab(BuildCreativeModeTabContentsEvent event)
    {
        // ADDING TO MACHINES TAB
        if (event.getTabKey() == CREATIVE_TAB_MACHINES.getKey()) {
        	event.accept(BlockRegistry.COKING_OVEN_ITEM);
        	event.accept(BlockRegistry.ALLOY_BLAST_FURNACE_ITEM);
        	event.accept(BlockRegistry.DESKTOP_ITEM);
        	event.accept(BlockRegistry.TERMINAL_ITEM);
            event.accept(BlockRegistry.ELITE_GAMING_CHAIR_ITEM);
        }
        
        // ADDING TO RESOURCES TAB
        else if (event.getTabKey() == CREATIVE_TAB_RESOURCES.getKey()) {
        	event.accept(ItemRegistry.COAL_COKE);
            event.accept(FluidRegistry.CONTAMINATED_WATER.bucket.get());
            event.accept(FluidRegistry.EXAMPLE_FLUID.bucket.get());
            event.accept(BlockRegistry.TIN_ORE_ITEM);
            event.accept(BlockRegistry.DEEPSLATE_TIN_ORE_ITEM);
            event.accept(BlockRegistry.RAW_TIN_BLOCK_ITEM);
            event.accept(ItemRegistry.RAW_TIN);
            event.accept(ItemRegistry.TRANSISTOR);
            event.accept(ItemRegistry.CAPACITOR);
            event.accept(ItemRegistry.CENT_1);
            event.accept(ItemRegistry.CENT_10);
            event.accept(ItemRegistry.CENT_25);
            event.accept(ItemRegistry.DOLLAR_1);
            event.accept(ItemRegistry.DOLLAR_5);
            event.accept(ItemRegistry.DOLLAR_10);
            event.accept(ItemRegistry.DOLLAR_20);
            event.accept(ItemRegistry.DOLLAR_50);
            event.accept(ItemRegistry.DOLLAR_100);
            event.accept(ItemRegistry.EMPTY_CIRCUIT_BOARD);
            event.accept(ItemRegistry.PCB_CIRCUIT);
            event.accept(ItemRegistry.CIRCUIT_T1);
            event.accept(ItemRegistry.CIRCUIT_T2);
            event.accept(ItemRegistry.CIRCUIT_T3);
            event.accept(ItemRegistry.CIRCUIT_T4);
            event.accept(ItemRegistry.CIRCUIT_T5);
            
            // MaterialRegistry items
            List<MaterialSetRecord> list = MaterialSetRegistry.getSets();
            
            // Order: Block, Ingot, Nugget, Slag
            for (MaterialSetRecord thing : list) {
                if (thing.blockItem() != null) {
                    event.accept(thing.blockItem().get());
                }

                event.accept(thing.ingot().get());
                if (thing.nugget() != null) {
                    event.accept(thing.nugget().get());
                }
                
                if (thing.dust() != null) {
                    event.accept(thing.dust().get());
                }
                
                if (thing.plate() != null) {
                    event.accept(thing.plate().get());
                }
                
                if (thing.slag() != null) {
                    event.accept(thing.slag().get());
                }

            }
        }
        
        // ADDING TO MODULES TAB
        else if (event.getTabKey() == CREATIVE_TAB_MODULES.getKey()) {
        	event.accept(ItemRegistry.FLAME_GEM);
        	event.accept(ItemRegistry.VAMPIRE_GEM);
        	event.accept(ItemRegistry.RADIOACTIVE_GEM);
        }
        
        // ADDING TO BUILDING TAB
        else if (event.getTabKey() == CREATIVE_TAB_BUILDING.getKey()) {
        	event.accept(BlockRegistry.TABLE_ITEM);
            event.accept(BlockRegistry.PACKAGE_ITEM);
        }

        // ADDING TO TOOLS TAB
        else if (event.getTabKey() == CREATIVE_TAB_TOOLS.getKey()) {
        	event.accept(ItemRegistry.WOODEN_TONGS);
        	event.accept(ItemRegistry.STEEL_TONGS);
            event.accept(ItemRegistry.LEATHER_GLOVES);
            event.accept(ItemRegistry.CREATIVE_GLOVES);
            event.accept(ItemRegistry.ENERGY_DRINK);
            event.accept(ItemRegistry.GEIGER_COUNTER);
        }
        
        else if (event.getTabKey() == CREATIVE_TAB_MISC.getKey()) {
        	
        	// Add all Pills in texture order
        	for (int i = 0; i < PillData.EXISTING_PILL_EFFECTS; i++) {
        	    event.accept(addPill(i));
        	}
        	
        }
    }
    
    private static ItemStack addPill(int effectID) {

        ItemStack stack = new ItemStack(ItemRegistry.MYSTERIOUS_PILL.get());
        CompoundTag tag = stack.getOrCreateTag();

        tag.putInt("pill_data", effectID);
        tag.putInt("pill_texture", effectID);

        return stack;
    }

}
