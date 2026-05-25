package data.birmingham.recipes;
import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

//public class BirminghamRecipeProvider extends RecipeProvider {
	//protected final DataGenerator gen;
	//private final CompletableFuture<HolderLookup.Provider> reg;
	//@SuppressWarnings("removal")
	//private ResourceLocation tagLocation = new ResourceLocation("minecraft", "#forge:tools/swords");
	//psuedo vibecoding but i don't want to talk about it java sucks
	//private TagKey<Item> tagKey = ForgeRegistries.ITEMS.tags().createTagKey(tagLocation);
	//using a tutorial by Yusuf.I
	//DISCLAIMER: DO NOT USE HIS TYUTORIAL ITS 1.21
	//public BirminghamRecipeProvider(DataGenerator outPut,CompletableFuture<HolderLookup.Provider> registry) {
		//super(outPut.getPackOutput());
		//this.gen=outPut;
		//this.reg=registry;
	//}

	//@Override
	//protected void buildRecipes(Consumer<FinishedRecipe> recipes) {
		//ForgeRegistries.ITEMS.tags().getTag(tagKey).stream().forEach(holder -> {
			//Item i=holder;
			//if (i!=Items.AIR) {
				//SmithingTransformRecipeBuilder.smithing(
					//	null, //template
						//null, //item
						//null, //item
						//null, //category
						//holder); //output
			//}
		//});
	//}

//}
