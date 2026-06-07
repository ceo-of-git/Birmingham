package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;

@Mod.EventBusSubscriber(
	    modid = MainRegistry.MOD_ID,
	    value = Dist.CLIENT,
	    bus = Mod.EventBusSubscriber.Bus.MOD
	)
public class StartupEventHandler {
	
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    	// event.registerBlockEntityRenderer(BlockRegistry.COKING_OVEN_ENTITY.get(), CokingOvenRenderer::new);
    }
}
