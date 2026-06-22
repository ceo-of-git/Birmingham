package xyz.nasasupercomputer.birmingham.ModCompat;

import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Inventories.AlloyBlastFurnaceScreen;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenScreen;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeAlloyBlasting;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

@JeiPlugin
public class JEI implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(MainRegistry.MOD_ID, "jei_plugin");
	}
	
	// Making the recipe Categories
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
	    registration.addRecipeCategories(
	    		new CokingCategoryJEI(registration.getJeiHelpers().getGuiHelper()),
	    		new AlloyBlastingCategoryJEI(registration.getJeiHelpers().getGuiHelper())
	        );
	}
	
	// Linking Coking recipies to the Coking Category
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		
		List<RecipeTypeCokingOven> cokingRecipes = recipeManager.getAllRecipesFor(RecipeTypeCokingOven.Type.INSTANCE);
		List<RecipeTypeAlloyBlasting> blastingRecipes = recipeManager.getAllRecipesFor(RecipeTypeAlloyBlasting.Type.INSTANCE);
		
		registration.addRecipes(CokingCategoryJEI.COKING_OVEN_TYPE, cokingRecipes);
		registration.addRecipes(AlloyBlastingCategoryJEI.ALLOY_BLASTING_TYPE, blastingRecipes);
	}
	
	// Making it so you can click on the "arrow" to go into JEI.
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(CokingOvenScreen.class, 45, 41, 86, 7, CokingCategoryJEI.COKING_OVEN_TYPE);
		registration.addRecipeClickArea(AlloyBlastFurnaceScreen.class, 45, 63, 86, 7, AlloyBlastingCategoryJEI.ALLOY_BLASTING_TYPE);
	}

}
