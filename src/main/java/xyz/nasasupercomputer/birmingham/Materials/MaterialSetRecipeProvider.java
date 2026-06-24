package xyz.nasasupercomputer.birmingham.Materials;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class MaterialSetRecipeProvider extends RecipeProvider {

	public MaterialSetRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> writer) {

	    MainRegistry.LOGGER.info("Generating recipes for {} material sets", MaterialSetRegistry.ALL_SETS.size() );

	    for (MaterialSetRecord set : MaterialSetRegistry.ALL_SETS) {
	        MainRegistry.LOGGER.info("Recipe gen: {}", set.name());
	        MaterialSetRegistry.createRecipes(writer, set);
	    }
	}

}
