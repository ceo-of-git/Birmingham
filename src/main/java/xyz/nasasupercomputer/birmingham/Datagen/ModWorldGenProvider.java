package xyz.nasasupercomputer.birmingham.Datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Worldgen.BiomeModifiers;
import xyz.nasasupercomputer.birmingham.Worldgen.ConfiguredFeatures;
import xyz.nasasupercomputer.birmingham.Worldgen.PlacedFeatures;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
	public ModWorldGenProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries, BUILDER, Set.of(MainRegistry.MOD_ID));
	}

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
			.add(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);
	

}
