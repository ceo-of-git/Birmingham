package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.entity.ModEntities;
import xyz.nasasupercomputer.birmingham.entity.custom.CrabEntities;


@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event ) {
		event.put(ModEntities.crab.get(), CrabEntities.createAttributes().build());
	}

}
