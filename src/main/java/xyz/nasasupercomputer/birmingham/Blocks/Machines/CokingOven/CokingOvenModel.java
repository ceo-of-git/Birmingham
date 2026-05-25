package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class CokingOvenModel extends GeoModel<CokingOvenEntity> {
	
    @Override
    public ResourceLocation getModelResource(CokingOvenEntity animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "geo/coking_oven.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CokingOvenEntity animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "textures/block/coking_oven.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CokingOvenEntity animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "animations/coking_oven.animation.json");
    }
}