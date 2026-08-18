package xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class Printer3DBlockItemModel extends GeoModel<Printer3DBlockItem> {
    @Override
    public ResourceLocation getModelResource(Printer3DBlockItem animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "geo/3d_printer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Printer3DBlockItem animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "textures/block/3d_printer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Printer3DBlockItem animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "animations/3d_printer.animation.json");
    }
}
