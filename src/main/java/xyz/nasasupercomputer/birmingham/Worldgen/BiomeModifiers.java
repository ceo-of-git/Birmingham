package xyz.nasasupercomputer.birmingham.Worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class BiomeModifiers {
	public static final ResourceKey<BiomeModifier> ADD_TIN_ORE = registerKey("add_tin_ore");
	
	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		var biomes = context.lookup(Registries.BIOME);
		
		context.register(ADD_TIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(BiomeTags.IS_OVERWORLD), // Spawn anywhere in Overworld
				HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.TIN_ORE_PLACED_KEY)), // Get the TIN KEY
				GenerationStep.Decoration.UNDERGROUND_ORES)); // Spawn during the under-ground ore phase of world generation
		
	}
	
	
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(MainRegistry.MOD_ID, name));
    }
}
