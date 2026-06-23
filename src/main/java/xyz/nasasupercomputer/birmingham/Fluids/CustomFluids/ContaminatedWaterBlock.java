package xyz.nasasupercomputer.birmingham.Fluids.CustomFluids;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class ContaminatedWaterBlock  extends LiquidBlock {
    public ContaminatedWaterBlock(Supplier<? extends FlowingFluid> fluid, Properties props) {
        super(fluid, props);
    }
}
