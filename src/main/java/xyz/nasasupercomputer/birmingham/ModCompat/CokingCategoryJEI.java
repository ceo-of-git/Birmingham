package xyz.nasasupercomputer.birmingham.ModCompat;

import org.jetbrains.annotations.Nullable;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

public class CokingCategoryJEI implements IRecipeCategory<RecipeTypeCokingOven> {

	public static final ResourceLocation UID = new ResourceLocation(MainRegistry.MOD_ID, "coking");
	public static final ResourceLocation TEXTURE = new ResourceLocation(MainRegistry.MOD_ID, "textures/gui/machines/gui_coking_oven.png");
	
	public static final RecipeType<RecipeTypeCokingOven> COKING_OVEN_TYPE = new RecipeType<>(UID, RecipeTypeCokingOven.class);
	
	private final IDrawable background;
	private final IDrawable icon;
	
	public CokingCategoryJEI(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 60);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.COKING_OVEN.get()));
	}
	
	@Override
	public RecipeType<RecipeTypeCokingOven> getRecipeType() {
		return COKING_OVEN_TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("inventory.birmingham.coking_oven");
	}
	
	@Override
	public IDrawable getBackground() {
	    return background;
	}

	@Override
	public @Nullable IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeTypeCokingOven recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 26, 36).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 36).addItemStack(recipe.getResultItem(null));
	}

}
