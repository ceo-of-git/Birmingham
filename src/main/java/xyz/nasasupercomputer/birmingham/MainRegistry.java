package xyz.nasasupercomputer.birmingham;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.GeckoLib;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Inventories.InventoryRegistry;
import xyz.nasasupercomputer.birmingham.ItemGems.GemRegistry;
import xyz.nasasupercomputer.birmingham.ItemGroups.ItemGroupRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeRegistry;
import xyz.nasasupercomputer.birmingham.Sound.SoundRegistry;
import xyz.nasasupercomputer.birmingham.entity.ModEntities;
import xyz.nasasupercomputer.birmingham.entity.client.CrabRenderer;

import org.slf4j.Logger;
import xyz.nasasupercomputer.birmingham.loottables.ModLootModifiers;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MainRegistry.MOD_ID)
public class MainRegistry
{
    // Define Mod ID in a common place for everything to reference
    public static final String MOD_ID = "birmingham";
    public static final Logger LOGGER = LogUtils.getLogger();

	// Treat this as the first thing that runs in the entire mod.
    public MainRegistry(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::CommonSetup);

        GeckoLib.initialize();
        
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockRegistry.BLOCK_ENTITIES.register(modEventBus);
        
        ItemRegistry.ITEMS.register(modEventBus);
        SoundRegistry.SOUND_EVENTS.register(modEventBus);
        ItemGroupRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ModEntities.register(modEventBus);
        GemRegistry.register(modEventBus);
    	RecipeRegistry.register(modEventBus);
        
        InventoryRegistry.MENUS.register(modEventBus);
        
        //registering loot tables (Probably)
        ModLootModifiers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(ItemGroupRegistry::AddItemToTab);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, ForgeConfigs.SPEC);
    }

    private void CommonSetup(final FMLCommonSetupEvent event)
    {	
    	
    	LOGGER.info("[Birmingham] Started Mod with Tech Route Enabled: " + String.valueOf(ForgeConfigs.enableTechRoute).toUpperCase());
    	LOGGER.info("[Birmingham] Started Mod with Exploration Route Enabled: " + String.valueOf(ForgeConfigs.enableExplorationRoute).toUpperCase());
    	LOGGER.info("[Birmingham] Started Mod with Hazards Enabled: " + String.valueOf(ForgeConfigs.enableItemHazards).toUpperCase());
    	
        // Register all Item Hazards
        HazardRegistry.RegisterAllHazards();
        
//        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//        if (ForgeConfigs.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        LOGGER.info(ForgeConfigs.magicNumberIntroduction + ForgeConfigs.magicNumber);
//
//        ForgeConfigs.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        	EntityRenderers.register(ModEntities.crab.get(), CrabRenderer::new);
            // Some client setup code
            // LOGGER.info("HELLO FROM CLIENT SETUP");
            // LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
