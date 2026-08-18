package xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class Printer3DBlockRenderer extends GeoBlockRenderer<Printer3DBlockEntity> {

    public Printer3DBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new Printer3DBlockModel());
    }
}
