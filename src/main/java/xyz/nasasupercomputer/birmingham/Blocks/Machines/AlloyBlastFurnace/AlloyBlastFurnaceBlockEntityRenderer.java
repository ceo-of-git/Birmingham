//package xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.client.resources.model.BakedModel;
//import net.minecraft.client.resources.model.ModelResourceLocation;
//import net.minecraft.core.Direction.Axis;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.event.ModelEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import xyz.nasasupercomputer.birmingham.MainRegistry;
//
//// NOTE FOR FUTURE ME:
//// To add another .obj machine:
//// 1 - Model
//// 2 - Setup the Bigblock
//// 3 - Make the Renderer class like this
//// 4 - Go to birmingham/EventHandlers/ClientEventHandlerMOD.java and link it to the entity
//// 5 - Enjoy your machine-slop
//
//@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//public class AlloyBlastFurnaceBlockEntityRenderer implements BlockEntityRenderer<AlloyBlastFurnaceBlockEntity> {
//
//    private static final ModelResourceLocation id = new ModelResourceLocation(MainRegistry.MOD_ID, "alloy_blast_furnace", "facing=north");
//    private static BakedModel blastModel;
//
//    public AlloyBlastFurnaceBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
//        // super();
//    }
//    
//    @Override
//    public void render(AlloyBlastFurnaceBlockEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
//
//        pPoseStack.pushPose();
//
////        pPoseStack.translate(-0.5, 0.0, -0.5);
//
////        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
////    		pPoseStack.last(),
////    		pBuffer.getBuffer(RenderType.cutout()),
////            entity.getBlockState(),
////            blastModel,
////            1f, 1f, 1f,
////            pPackedLight,
////            pPackedOverlay);
//
//        pPoseStack.popPose();
//    }
//
//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
//    	blastModel = event.getModels().get(id);
//    	
//    	// Debug code that prints the default state of every block when loading (used to find the second part of the id declaration "#facing=north")
////        event.getModels().keySet().stream()
////        .filter(loc -> loc.getNamespace().equals(MainRegistry.MOD_ID))
////        .forEach(loc -> MainRegistry.LOGGER.info("MODEL KEY: {}", loc));
//    }
//}