package xyz.nasasupercomputer.birmingham.Sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class SoundRegistry {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MainRegistry.MOD_ID);
	
	public static final RegistryObject<SoundEvent> HORSING_AROUND_TEST = registerSoundEvents("horsing_around_test");
	
	public static void Register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}

	@SuppressWarnings("removal") // shut up
	private static RegistryObject<SoundEvent> registerSoundEvents(String nameOfSound) {
		return SOUND_EVENTS.register(nameOfSound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MainRegistry.MOD_ID, nameOfSound)));
	}
}
