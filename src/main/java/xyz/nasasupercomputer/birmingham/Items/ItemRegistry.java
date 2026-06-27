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
import xyz.nasasupercomputer.birmingham.Items.custom.GeigerCounter;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;
import xyz.nasasupercomputer.birmingham.Items.custom.Gem;
import xyz.nasasupercomputer.birmingham.Items.custom.Gloves;
import xyz.nasasupercomputer.birmingham.Items.custom.Money;
import xyz.nasasupercomputer.birmingham.Sound.SoundRegistry;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MOD_ID);
	
	// =========================
	// ORIGINAL ITEMS
	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> RAW_ADAMANTITE = ITEMS.register("raw_adamantite", () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> HORSING_AROUND = ITEMS.register("horsing_around", () -> new RecordItem(6, SoundRegistry.horsing_around, new Item.Properties().stacksTo(1), 2440));
	public static final RegistryObject<Item> COAL_COKE = ITEMS.register("coal_coke", () -> new Item(new Item.Properties().stacksTo(64)));

	// =========================
	// TOOLS / CURIOS
	public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves", () -> new Gloves(new Item.Properties().stacksTo(1), 1, List.of(HazardRegistry.Hazard_Molten_T1, HazardRegistry.Hazard_Molten_T2), false));
	public static final RegistryObject<Item> CREATIVE_GLOVES = ITEMS.register("creative_gloves", () -> new Gloves(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 69420, null, false));
	public static final RegistryObject<Item> WOODEN_TONGS = ITEMS.register("wooden_tongs", () -> new Gloves(new Item.Properties().stacksTo(1).durability(128), 3, List.of(HazardRegistry.Hazard_Molten_T1), true));
	public static final RegistryObject<Item> STEEL_TONGS = ITEMS.register("steel_tongs", () -> new Gloves(new Item.Properties().stacksTo(1).durability(2048), 3, List.of(HazardRegistry.Hazard_Molten_T1), true));
	
	public static final RegistryObject<Item> ENERGY_DRINK = ITEMS.register("energy_drink", () -> new energyDrink(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1600, 7), 1f).alwaysEat().build()), 1f));
	public static final RegistryObject<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter", () -> new GeigerCounter(new Item.Properties().stacksTo(1), "item.birmingham.geigercounter.description"));

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
	
}

