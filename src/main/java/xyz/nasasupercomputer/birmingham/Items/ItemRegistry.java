package xyz.nasasupercomputer.birmingham.Items;

import java.util.List;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.Items.curios.energyDrink;
import xyz.nasasupercomputer.birmingham.Items.custom.EnergyDebugger;
import xyz.nasasupercomputer.birmingham.Items.custom.Fuel;
import xyz.nasasupercomputer.birmingham.Items.custom.GeigerCounter;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;
import xyz.nasasupercomputer.birmingham.Items.custom.Gem;
import xyz.nasasupercomputer.birmingham.Items.custom.Gloves;
import xyz.nasasupercomputer.birmingham.Items.custom.Money;
import xyz.nasasupercomputer.birmingham.Items.custom.PillItem;
import xyz.nasasupercomputer.birmingham.Sound.SoundRegistry;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MOD_ID);
	
	// =========================
	// ORIGINAL / CRAFTING ITEMS
	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> RAW_ADAMANTITE = ITEMS.register("raw_adamantite", () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> HORSING_AROUND = ITEMS.register("horsing_around", () -> new RecordItem(6, SoundRegistry.horsing_around, new Item.Properties().stacksTo(1), 2440));
	public static final RegistryObject<Item> COAL_COKE = ITEMS.register("coal_coke", () -> new Fuel(new Item.Properties().stacksTo(64), 3200));
	public static final RegistryObject<Item> EMPTY_CIRCUIT_BOARD = ITEMS.register("empty_circuit_board", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> ETCHED_CIRCUIT_BOARD = ITEMS.register("etched_circuit_board", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> TRANSISTOR = ITEMS.register("transistor", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CAPACITOR = ITEMS.register("capacitor", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> PCB_CIRCUIT = ITEMS.register("pcb_circuit", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CIRCUIT_T1 = ITEMS.register("circuit_t1", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CIRCUIT_T2 = ITEMS.register("circuit_t2", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CIRCUIT_T3 = ITEMS.register("circuit_t3", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CIRCUIT_T4 = ITEMS.register("circuit_t4", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CIRCUIT_T5 = ITEMS.register("circuit_t5", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> MYSTERIOUS_PILL = ITEMS.register("mysterious_pill", () -> new PillItem(new Item.Properties()));
	public static final RegistryObject<Item> BLACK_PLASTIC_FILAMENT = ITEMS.register("black_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BLUE_PLASTIC_FILAMENT = ITEMS.register("blue_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BROWN_PLASTIC_FILAMENT = ITEMS.register("brown_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> CYAN_PLASTIC_FILAMENT = ITEMS.register("cyan_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> GREEN_PLASTIC_FILAMENT = ITEMS.register("green_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> LIGHT_BLUE_PLASTIC_FILAMENT = ITEMS.register("light_blue_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> LIGHT_GRAY_PLASTIC_FILAMENT = ITEMS.register("light_gray_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> LIME_PLASTIC_FILAMENT = ITEMS.register("lime_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> MAGENTA_PLASTIC_FILAMENT = ITEMS.register("magenta_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> ORANGE_PLASTIC_FILAMENT = ITEMS.register("orange_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> PINK_PLASTIC_FILAMENT = ITEMS.register("pink_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> RED_PLASTIC_FILAMENT = ITEMS.register("red_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> WHITE_PLASTIC_FILAMENT = ITEMS.register("white_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> YELLOW_PLASTIC_FILAMENT = ITEMS.register("yellow_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> GRAY_PLASTIC_FILAMENT = ITEMS.register("gray_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> PURPLE_PLASTIC_FILAMENT = ITEMS.register("purple_plastic_filament", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> COOLING_FAN_T1 = ITEMS.register("cooling_fan_t1", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> COOLING_FAN_T2 = ITEMS.register("cooling_fan_t2", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> COOLING_FAN_T3 = ITEMS.register("cooling_fan_t3", () -> new Item(new Item.Properties().stacksTo(64)));


	// =========================
	// TOOLS / CURIOS
	public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves", () -> new Gloves(new Item.Properties().stacksTo(1), 1, List.of(HazardRegistry.Hazard_Molten_T1, HazardRegistry.Hazard_Molten_T2), false));
	public static final RegistryObject<Item> CREATIVE_GLOVES = ITEMS.register("creative_gloves", () -> new Gloves(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 69420, null, false));
	public static final RegistryObject<Item> WOODEN_TONGS = ITEMS.register("wooden_tongs", () -> new Gloves(new Item.Properties().stacksTo(1).durability(128), 3, List.of(HazardRegistry.Hazard_Molten_T2), true));
	public static final RegistryObject<Item> STEEL_TONGS = ITEMS.register("steel_tongs", () -> new Gloves(new Item.Properties().stacksTo(1).durability(2048), 3, null, true));
	
	public static final RegistryObject<Item> ENERGY_DRINK = ITEMS.register("energy_drink", () -> new energyDrink(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1600, 7), 1f).alwaysEat().build()), 1f));
	public static final RegistryObject<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter", () -> new GeigerCounter(new Item.Properties().stacksTo(1), "item.birmingham.geigercounter.description"));
	public static final RegistryObject<Item> ENERGY_DEBUGGER = ITEMS.register("energy_debugger", () -> new EnergyDebugger(new Item.Properties().stacksTo(1)));

	// =========================
	// MATERIALS / ORES
	public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(new Item.Properties().stacksTo(64)));
//	public static final RegistryObject<Item> STEEL_BLOCK = ITEMS.register("steel_block", () -> new BlockItem(BlockRegistry.STEEL_BLOCK.get(), new Item.Properties()));
//	public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().stacksTo(64)));
//	public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().stacksTo(64)));
//	public static final RegistryObject<Item> STEEL_SLAG = ITEMS.register("steel_slag", () -> new Item(new Item.Properties().stacksTo(64)));

	// =========================
	// MONEY
	public static final RegistryObject<Item> CENT_1 = ITEMS.register("cent_1", () -> new Money(new Item.Properties().stacksTo(64), 0.01, true));
	public static final RegistryObject<Item> CENT_10 = ITEMS.register("cent_10", () -> new Money(new Item.Properties().stacksTo(64), 0.10, true));
	public static final RegistryObject<Item> CENT_25 = ITEMS.register("cent_25", () -> new Money(new Item.Properties().stacksTo(64), 0.25, true));
	public static final RegistryObject<Item> DOLLAR_1 = ITEMS.register("dollar_1", () -> new Money(new Item.Properties().stacksTo(64), 1.00, true));
	public static final RegistryObject<Item> DOLLAR_5 = ITEMS.register("dollar_5", () -> new Money(new Item.Properties().stacksTo(64), 5.00, true));
	public static final RegistryObject<Item> DOLLAR_10 = ITEMS.register("dollar_10", () -> new Money(new Item.Properties().stacksTo(64), 10.00, true));
	public static final RegistryObject<Item> DOLLAR_20 = ITEMS.register("dollar_20", () -> new Money(new Item.Properties().stacksTo(64), 20.00, true));
	public static final RegistryObject<Item> DOLLAR_50 = ITEMS.register("dollar_50", () -> new Money(new Item.Properties().stacksTo(64), 50.00, true));
	public static final RegistryObject<Item> DOLLAR_100 = ITEMS.register("dollar_100", () -> new Money(new Item.Properties().stacksTo(64), 100.00, true));
	
	// =========================
	// GEMS
	public static final RegistryObject<Item> FLAME_GEM = ITEMS.register("flame_gem", () -> new Gem(new Item.Properties().stacksTo(64), "Flame", "gems.birmingham.flame.title", "gems.birmingham.flame.description"));
	public static final RegistryObject<Item> VAMPIRE_GEM = ITEMS.register("vampire_gem", () -> new Gem(new Item.Properties().stacksTo(64), "Vampiric", "gems.birmingham.vampiric.title", "gems.birmingham.vampiric.description"));
	public static final RegistryObject<Item> RADIOACTIVE_GEM = ITEMS.register("radioactive_gem", () -> new Gem(new Item.Properties().stacksTo(64), "Radioactive", "gems.birmingham.radioactive.title", "gems.birmingham.radioactive.description"));
	public static final RegistryObject<Item> ANCIENT_GOLD_SHARD = ITEMS.register("ancient_gold_shard", () -> new Gem(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON), "Greed", "gems.birmingham.greed.title", "gems.birmingham.greed.description"));

}

