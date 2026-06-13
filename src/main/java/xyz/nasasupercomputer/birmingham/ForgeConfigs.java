package xyz.nasasupercomputer.birmingham;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeConfigs
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    
	// Main Setting: Enable Tech Route
	public static boolean enableTechRoute;
    private static final ForgeConfigSpec.BooleanValue ENABLED_TECH_ROUTE = BUILDER
            .comment("Whether or not to enable the Technology progression path in this mod.")
            .define("enableTechRoute", true);

    // Main Setting: Enable Exploration Route
	public static boolean enableExplorationRoute;
    private static final ForgeConfigSpec.BooleanValue ENABLED_EXPLORATION_ROUTE = BUILDER
            .comment("Whether or not to enable the Exploration / Boss-Fighting Terraria-Like progression path in this mod.")
            .define("enableExplorationPath", true);
    
    // Main Setting: Enable Item Hazards
	public static boolean enableItemHazards;
    private static final ForgeConfigSpec.BooleanValue ENABLED_ITEM_HAZARDS = BUILDER
            .comment("Whether or not to enable hazardous items")
            .define("enableItemHazards", true);
    
    // Main Setting: Enable Gems
	public static boolean enableItemGems;
    private static final ForgeConfigSpec.BooleanValue ENABLED_ITEM_GEMS = BUILDER
            .comment("Whether or not to enable hazardous items")
            .define("enableItemGems", true);


//    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
//            .comment("A magic number")
//            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
//
//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//
//    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);


//    public static boolean logDirtBlock;
//    public static int magicNumber;
//    public static String magicNumberIntroduction;
//    public static Set<Item> items;
    
    // This must be last because this finalizes the Config.
    static final ForgeConfigSpec SPEC = BUILDER.build();

    
	private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
    	enableTechRoute = ENABLED_TECH_ROUTE.get();
    	enableExplorationRoute = ENABLED_EXPLORATION_ROUTE.get();
    	enableItemHazards = ENABLED_ITEM_HAZARDS.get();
    	enableItemGems = ENABLED_ITEM_GEMS.get();
//        magicNumber = MAGIC_NUMBER.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }
}
