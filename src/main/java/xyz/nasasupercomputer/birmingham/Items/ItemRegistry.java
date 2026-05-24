package xyz.nasasupercomputer.birmingham.Items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.Items.custom.attributeCurios;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Items.custom.Gloves;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MOD_ID);
	
	// =========================
	// ORIGINAL ITEMS
	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> RAW_ADAMANTITE = ITEMS.register("raw_adamantite", () -> new Item(new Item.Properties().stacksTo(64)));
	
	// =========================
	// TOOLS / CURIOS
	public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("gloves_leather", () -> new Gloves(new Item.Properties().stacksTo(1), 1, null));
	public static final RegistryObject<Item> CREATIVE_GLOVES = ITEMS.register("gloves_creative", () -> new Gloves(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 69420, null));

	public static final RegistryObject<Item> ENERGY_DRINK = ITEMS.register("energy_drink", () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1600, 4), 1f).alwaysEat().build())));

}
