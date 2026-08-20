package xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import software.bernie.geckolib.model.GeoModel;
import xyz.nasasupercomputer.birmingham.MainRegistry;

import static xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D.Printer3DBlock.ACTIVE;

public class Printer3DBlockModel extends GeoModel<Printer3DBlockEntity> {
    @Override
    public ResourceLocation getModelResource(Printer3DBlockEntity animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "geo/3d_printer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Printer3DBlockEntity animatable) {
        if (animatable.getBlockState().getValue(ACTIVE)) {
            return new ResourceLocation(MainRegistry.MOD_ID, "textures/block/3d_printer_on.png");
        }
        else {
            return new ResourceLocation(MainRegistry.MOD_ID, "textures/block/3d_printer_off.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(Printer3DBlockEntity animatable) {
        return new ResourceLocation(MainRegistry.MOD_ID, "animations/3d_printer.animation.json");
    }
}
