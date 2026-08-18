package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.model.DynamicFluidContainerModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D.Printer3DBlockRenderer;
import xyz.nasasupercomputer.birmingham.Fluids.FluidRegistryContainer;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.entity.ModEntities;
import xyz.nasasupercomputer.birmingham.entity.client.CrabRenderer;
import xyz.nasasupercomputer.birmingham.entity.client.ModModelLayers;
import xyz.nasasupercomputer.birmingham.entity.client.crab_model;
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
    
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.CRAB_LAYER, crab_model::createBodyLayer);
	}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    	EntityRenderers.register(ModEntities.crab.get(), CrabRenderer::new);
        // Some client setup code
        // LOGGER.info("HELLO FROM CLIENT SETUP");
        // LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    	
    	// ADD CUSTOM ITEM TAGS HERE
    	// PILL DATA & TEXTURES!
    	ItemProperties.register(
    		    ItemRegistry.MYSTERIOUS_PILL.get(),
    		    new ResourceLocation("birmingham", "pill_texture"),
    		    (stack, level, entity, seed) -> {
    		        if (!stack.hasTag()) { return 0; }
    		        
    		        return stack.getTag().getInt("pill_texture");
    		    }
    		);

		// Hooks up the Animated Block Entities to their Animators
		BlockEntityRenderers.register(BlockRegistry.PRINTER_3D_ENTITY.get(), Printer3DBlockRenderer::new);

    }

//	@SubscribeEvent
//	public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
//	    event.registerBlockEntityRenderer(BlockRegistry.ALLOY_BLAST_FURNACE_ENTITY.get(), AlloyBlastFurnaceBlockEntityRenderer::new);
//	}
}
