package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.model.DynamicFluidContainerModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.Fluids.FluidRegistryContainer;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
//import xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace.AlloyBlastFurnaceBlockEntityRenderer;

// WHY on EARTH would you have to separate these ??????
// i hate this game bro

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandlerMOD {
	
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        var colors = new DynamicFluidContainerModel.Colors();
        for (FluidRegistryContainer c : FluidRegistryContainer.ALL) {
            event.register(colors, c.bucket.get());
        }
    }


//	@SubscribeEvent
//	public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
//	    event.registerBlockEntityRenderer(BlockRegistry.ALLOY_BLAST_FURNACE_ENTITY.get(), AlloyBlastFurnaceBlockEntityRenderer::new);
//	}
}
