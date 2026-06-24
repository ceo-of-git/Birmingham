package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Materials.MaterialSetRecipeProvider;
import xyz.nasasupercomputer.birmingham.Materials.MaterialSetRegistry;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

	    DataGenerator generator = event.getGenerator();
	    PackOutput output = generator.getPackOutput();

	    MaterialSetRegistry.createMaterialSets();

	    generator.addProvider(event.includeServer(),
	        new MaterialSetRecipeProvider(output)
	    );
	}
}
