package xyz.nasasupercomputer.birmingham.Materials;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Custom.IrradiationProperties;
import xyz.nasasupercomputer.birmingham.Blocks.Custom.RadioactiveBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardRadioactive;

// @Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MaterialSetRegistry {

		public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MOD_ID);
		public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainRegistry.MOD_ID);
		public static final ArrayList<MaterialSetRecord> ALL_SETS = new ArrayList<>();
		private static final List<Runnable> PENDING_HAZARDS = new ArrayList<>(); // shit so it doesnt try to get the registryo bject before it actually exist so radiation doesnt crash
		private static boolean IS_INITIALIZED = false;
		
		private static final double BLOCK_RADS_MULT = 9;
		private static final double NUGGET_RADS_DIVIDE = 9;
		private static final double SLAG_RADS_DIVIDE = 4;
		
		public static void createMaterialSets() {
		    if (IS_INITIALIZED) return;
		    IS_INITIALIZED = true;

		    // NOTE: When adding sets, recipes will NOT generate unless you run gradle in the data mode
		    // gradlew runData (instead of runClient) (idk why) (it just does)
		    ALL_SETS.add(createItemSet("steel", true, true, true, MapColor.COLOR_GRAY, 6.0f, 2.0f, 0.0, new IrradiationProperties(false, 0, 0.0)));
		    ALL_SETS.add(createItemSet("radium", true, true, false, MapColor.COLOR_LIGHT_GREEN, 6.0f, 2.0f, 30.0, new IrradiationProperties(true, 30, 100.0)));
		}

		public static void registerEverything(IEventBus bus) {
		    createMaterialSets();

		    BLOCKS.register(bus);
		    ITEMS.register(bus);
		}
	
	public static void bindHazards() {
		// Applies all of the Pending Hazards
		PENDING_HAZARDS.forEach(Runnable::run);
		PENDING_HAZARDS.clear();
	}
	
	public static ArrayList<MaterialSetRecord> getSets(){
		return ALL_SETS;
	}

	// Creates a full set of Ingots, Nuggets, Blocks & Slag if specified
	// Does not create Models or Recipes for you. (yet... (TODO))
	public static MaterialSetRecord createItemSet(
			String name,
			boolean hasNugget,
			boolean hasBlock,
			boolean hasSlagForm,
			MapColor blockColor, // <---- When shown on Maps
			float blockHardness,
			float blockResistance,
			double ingotRADS,
			IrradiationProperties irradiationProperties)
	{
		
		RegistryObject<Item> ingotSetup = ITEMS.register(name + "_ingot", () -> new Item(new Item.Properties()));
		if (ingotRADS > 0) {
			PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(ingotSetup.get(), new HazardRadioactive(ingotRADS)));
		}
		
		// NUGGET SETUP
		
		RegistryObject<Item> nuggetSetup = hasNugget ? ITEMS.register(name + "_nugget", () -> new Item(new Item.Properties())) : null;
		RegistryObject<Item> slagSetup = hasSlagForm ? ITEMS.register(name + "_slag", () -> new Item(new Item.Properties())) : null;
		RegistryObject<Block> blockSetup;

		if (hasBlock) {
		    if (irradiationProperties.shouldIrradiate()) {
		        blockSetup = BLOCKS.register(name + "_block", () -> new RadioactiveBlock(
		            BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
		                .requiresCorrectToolForDrops()
		                .mapColor(blockColor)
		                .destroyTime(blockHardness)
		                .explosionResistance(blockResistance),
		            irradiationProperties.irradiationRange(),
		            irradiationProperties.irradiationPower()
		        ));
		    } else {
		        blockSetup = BLOCKS.register(name + "_block", () -> new Block(
		            BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
		                .requiresCorrectToolForDrops()
		                .mapColor(blockColor)
		                .destroyTime(blockHardness)
		                .explosionResistance(blockResistance)
		        ));
		    }
		} else {
		    blockSetup = null;
		}
		
		RegistryObject<Item> blockItemSetup = hasBlock ? ITEMS.register(name + "_block", () -> new BlockItem(blockSetup.get(), new Item.Properties())) : null;

		// Add Radiation to the Nugget (if applicable)
		if (hasNugget & ingotRADS > 0) {
			PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(nuggetSetup.get(), new HazardRadioactive(ingotRADS / NUGGET_RADS_DIVIDE)));
		}
		
		// Add Molten & Radiation (if applicable) to Slag
		if (hasSlagForm) {
			PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(slagSetup.get(), HazardRegistry.Hazard_Molten_T1));
			
			if (ingotRADS > 0) {
				PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(slagSetup.get(), new HazardRadioactive(ingotRADS / SLAG_RADS_DIVIDE)));	
			}
		}
		
		// Add Radiation to the Block (if applicable)
		if (hasBlock & ingotRADS > 0) {
			PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(blockItemSetup.get(), new HazardRadioactive(ingotRADS * BLOCK_RADS_MULT)));
		}
		
//		String name,
//		RegistryObject<Item> ingot,
//		RegistryObject<Item> nugget,
//		RegistryObject<Item> slag,
//		RegistryObject<Item> blockItem,
//		RegistryObject<Block> block
		return new MaterialSetRecord(name, ingotSetup, nuggetSetup, slagSetup, blockItemSetup, blockSetup);
	}
	

	public static void createRecipes(Consumer<FinishedRecipe> recipeWriter, MaterialSetRecord materialSet)
	{
		
		String prefix = materialSet.name();
		Item ingotItem = materialSet.ingot().get();
		Item blockItem = materialSet.blockItem() != null ? materialSet.blockItem().get() : null;
		Item nuggetItem = materialSet.nugget() != null ? materialSet.nugget().get() : null;
		Item slagItem = materialSet.slag() != null ? materialSet.slag().get() : null;
		
		if (blockItem != null) {
			// Block -> 9x Ingot
			ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingotItem, 9)
			.requires(blockItem)
			.unlockedBy("has" + BuiltInRegistries.ITEM.getKey(blockItem), InventoryChangeTrigger.TriggerInstance.hasItems(blockItem))
			.save(recipeWriter, new ResourceLocation(MainRegistry.MOD_ID, prefix + "_block_to_ingots"));
		
			// 9x Ingot -> Block
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockItem)
		        .pattern("###")
		        .pattern("###")
		        .pattern("###")
		        .define('#', ingotItem)
		        .unlockedBy("has" + BuiltInRegistries.ITEM.getKey(ingotItem), InventoryChangeTrigger.TriggerInstance.hasItems(ingotItem))
		        .save(recipeWriter, new ResourceLocation(MainRegistry.MOD_ID, prefix + "_ingots_to_block"));
		}
		
		
	
	    if (nuggetItem != null) {
	    	// Ingot -> 9 Nuggets
		    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nuggetItem, 9)
		    .requires(ingotItem)
		    .unlockedBy("has" + BuiltInRegistries.ITEM.getKey(ingotItem), InventoryChangeTrigger.TriggerInstance.hasItems(ingotItem))
		    .save(recipeWriter, new ResourceLocation(MainRegistry.MOD_ID, prefix + "_ingot_to_nuggets"));

		 // 9 Nuggets -> 1 Ingot
		    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
	        .pattern("###")
	        .pattern("###")
	        .pattern("###")
	        .define('#', nuggetItem)
	        .unlockedBy("has" + BuiltInRegistries.ITEM.getKey(nuggetItem), InventoryChangeTrigger.TriggerInstance.hasItems(nuggetItem))
	        .save(recipeWriter, new ResourceLocation(MainRegistry.MOD_ID, prefix + "_nuggets_to_ingot"));
	    }
	    
	    if (slagItem != null && nuggetItem != null) {
	    	// Slag -> 3 Nuggets
	    	ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nuggetItem, 3)
	    	.requires(slagItem)
	    	.unlockedBy("has" + BuiltInRegistries.ITEM.getKey(slagItem), InventoryChangeTrigger.TriggerInstance.hasItems(slagItem))
	    	.save(recipeWriter, new ResourceLocation(MainRegistry.MOD_ID, prefix + "_slag_to_nuggets"));
	    }
	   
	    

	}

}
