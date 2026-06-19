package xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class AlloyBlastFurnaceBlockEntityRenderer implements BlockEntityRenderer<AlloyBlastFurnaceBlockEntity> {

	static ResourceLocation id = new ResourceLocation(MainRegistry.MOD_ID, "blocks/alloy_blast_furnace");
	static BakedModel blastModel = Minecraft.getInstance().getModelManager().getModel(id);
	
	// private static BakedModel blastModel;
	
	@Override
	public void render(AlloyBlastFurnaceBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		pPoseStack.pushPose();

		pPoseStack.translate(0.5, 0.0, 0.5);

        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(pPoseStack.last(), pBuffer.getBuffer(RenderType.solid()),null, blastModel, 1f, 1f, 1f, pPackedLight, pPackedOverlay);

        pPoseStack.popPose();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onModelBake(ModelEvent.BakingCompleted event) {
		blastModel = event.getModelManager().getModel(id);
	}
}
