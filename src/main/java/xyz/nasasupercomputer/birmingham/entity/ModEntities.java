package xyz.nasasupercomputer.birmingham.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.entity.custom.CrabEntities;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MainRegistry.MOD_ID);
	
	public static final RegistryObject<EntityType<CrabEntities>> crab =
			ENTITY_TYPES.register("crab", () -> EntityType.Builder.of(CrabEntities::new, MobCategory.CREATURE)
					.sized(1f, 1f).build("crab"));

	public static void register(IEventBus modEventBus) {
		ENTITY_TYPES.register(modEventBus);
		// TODO Auto-generated method stub
		
	}

}
