package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.entity.ModEntities;
import xyz.nasasupercomputer.birmingham.entity.custom.CrabEntities;
import xyz.nasasupercomputer.birmingham.radiation.PlayerRadiationProvider;


@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event ) {
		event.put(ModEntities.crab.get(), CrabEntities.createAttributes().build());
	}
}
