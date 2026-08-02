package xyz.nasasupercomputer.birmingham.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

/// if this works i will be the best coder that ever existed "i stole it"

public class crab_model<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart bone;
	public crab_model(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(-15.0F, -9.0F, 4.0F, 14.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(1, 1).mirror().addBox(-7.0F, -13.0F, 12.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(1, 1).mirror().addBox(-12.0F, -13.0F, 12.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(1, 1).mirror().addBox(-9.0F, -11.0F, 12.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(1, 1).mirror().addBox(-8.0F, -11.0F, 12.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-4.0F, -6.0F, 0.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(8.0F, -6.0F, 0.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -6.0F, 8.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(2, 2).mirror().addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-17.0F, -1.0F, 10.0F, 0.0873F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(2, 2).mirror().addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-17.0F, -1.0F, 7.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(2, 2).mirror().addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-17.0F, -1.0F, 4.0F, -0.0873F, 0.0F, 0.7854F));

		PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(2, 2).mirror().addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.0F, 10.0F, 0.0873F, 0.0F, -0.7854F));

		PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(2, 2).mirror().addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.0F, 7.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(2, 2).mirror().addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.0F, 4.0F, -0.0873F, 0.0F, -0.7854F));

		PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(3, 3).mirror().addBox(-6.0F, -4.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(3, 3).mirror().addBox(-2.0F, -4.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -5.0F, 10.0F, 0.3054F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return bone;
	}
}