package xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CokingOvenRenderer extends GeoBlockRenderer<CokingOvenEntity>{
    public CokingOvenRenderer(BlockEntityRendererProvider.Context context) {
        super(new CokingOvenModel());
    }
}
