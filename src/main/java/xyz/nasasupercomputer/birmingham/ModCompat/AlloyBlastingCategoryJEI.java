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
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeAlloyBlasting;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeCokingOven;

public class AlloyBlastingCategoryJEI implements IRecipeCategory<RecipeTypeAlloyBlasting> {

	public static final ResourceLocation UID = new ResourceLocation(MainRegistry.MOD_ID, "alloy_blasting");
	public static final ResourceLocation TEXTURE = new ResourceLocation(MainRegistry.MOD_ID, "textures/gui/machines/gui_alloy_blast_furnace.png");
	
	public static final RecipeType<RecipeTypeAlloyBlasting> ALLOY_BLASTING_TYPE = new RecipeType<>(UID, RecipeTypeAlloyBlasting.class);
	
	private final IDrawable background;
	private final IDrawable icon;
	
	public AlloyBlastingCategoryJEI(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 60);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.ALLOY_BLAST_FURNACE.get()));
	}
	
	@Override
	public RecipeType<RecipeTypeAlloyBlasting> getRecipeType() {
		return ALLOY_BLASTING_TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("inventory.birmingham.alloy_blast_furnace").withStyle(ChatFormatting.YELLOW);
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
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeTypeAlloyBlasting recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 44, 36).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 116, 36).addIngredients(recipe.getIngredients().get(1));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 36).addItemStack(recipe.getResultItem(null));
	}

}
