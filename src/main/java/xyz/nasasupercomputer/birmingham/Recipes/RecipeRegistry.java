package xyz.nasasupercomputer.birmingham.Recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class RecipeRegistry {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MainRegistry.MOD_ID);
	
	public static final RegistryObject<RecipeSerializer<RecipeTypeCokingOven>> COKING_SERIALIZER = SERIALIZERS.register("coking", () -> RecipeTypeCokingOven.Serializer.INSTANCE);
	
	
	public static void register(IEventBus eventBus) {
		SERIALIZERS.register(eventBus);
	}
}
