package xyz.nasasupercomputer.birmingham.Recipes;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeAlloyBlasting.Serializer;
import xyz.nasasupercomputer.birmingham.Recipes.RecipeTypeAlloyBlasting.Type;

public class RecipeTypeCokingOven implements Recipe<SimpleContainer> {

	private final NonNullList<Ingredient> inputItems;
	private final ItemStack output;
	
	private final ResourceLocation id;
	
	public RecipeTypeCokingOven(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
		this.inputItems = inputItems;
		this.output = output;
		this.id = id;
	}

	@Override
	public boolean matches(SimpleContainer pContainer, Level pLevel) {
		if (pLevel.isClientSide()) { return false; } // No Crafting on a Server (will crash :( )
		
		return inputItems.get(0).test(pContainer.getItem(0));
	}
	
	@Override public NonNullList<Ingredient> getIngredients() { return inputItems; }
	@Override public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) { return output.copy(); }
	@Override public boolean canCraftInDimensions(int pWidth, int pHeight) { return false; }
	@Override public ItemStack getResultItem(RegistryAccess pRegistryAccess) { return output.copy(); }
	@Override public ResourceLocation getId() { return id; }
	@Override public RecipeSerializer<?> getSerializer() { return Serializer.INSTANCE; }
	@Override public RecipeType<?> getType() { return Type.INSTANCE; }
	
	public static class Type implements RecipeType<RecipeTypeCokingOven> {
		public static final Type INSTANCE = new Type();
		public static final String ID = "coking";
	}
	
	public static class Serializer implements RecipeSerializer<RecipeTypeCokingOven> {
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID = new ResourceLocation(MainRegistry.MOD_ID, "coking");
		
		@Override
		public RecipeTypeCokingOven fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
			JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY); // 1 = default input amount, so 1.
			
			// Full up the nonnulllist
			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}
			
			return new RecipeTypeCokingOven(inputs, output, pRecipeId);
		}

		@Override
		public @Nullable RecipeTypeCokingOven fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
			
			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromNetwork(pBuffer));
			}
			
			ItemStack output = pBuffer.readItem();
			
			return new RecipeTypeCokingOven(inputs, output, pRecipeId);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, RecipeTypeCokingOven pRecipe) {
			pBuffer.writeInt(pRecipe.inputItems.size());
			
			for (Ingredient ingredient : pRecipe.getIngredients()) {
				ingredient.toNetwork(pBuffer);
			}
			
			pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
		}
		
	}

}
