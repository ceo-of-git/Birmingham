package xyz.nasasupercomputer.birmingham.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.entity.custom.CrabEntities;

public class CrabRenderer extends MobRenderer<CrabEntities, crab_model<CrabEntities>> {
	public CrabRenderer(EntityRendererProvider.Context pContext) {
		super(pContext, new crab_model<>(pContext.bakeLayer(ModModelLayers.CRAB_LAYER)), 2f);
	}

	@Override
	public ResourceLocation getTextureLocation(CrabEntities pEntity) {
		return new ResourceLocation(MainRegistry.MOD_ID, "textures/entity/crab.png");
	}

	@Override
	public void render(CrabEntities pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
			MultiBufferSource pBuffer, int pPackedLight) {
		if(pEntity.isBaby()) {
			pMatrixStack.scale(0.5f, 0.5f, 0.5f);
		}
		
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}
}

