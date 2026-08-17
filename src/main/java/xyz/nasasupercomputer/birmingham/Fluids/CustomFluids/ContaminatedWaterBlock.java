package xyz.nasasupercomputer.birmingham.Fluids.CustomFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.AABB;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerRadiationProvider;

import java.util.function.Supplier;

public class ContaminatedWaterBlock  extends LiquidBlock {
    public ContaminatedWaterBlock(Supplier<? extends FlowingFluid> fluid, Properties props) {
        super(fluid, props);
    }

    double insideintensity = 25; // better not touch contaminated water
    double nearbyintensity = 15; // intensity of radiation (falls off with inverse square law, the value is the maximum it gets up to)

    double radius = 12.0; // radius of the radiation. doesn't change intensity, mostly just used for optimization and so it's not ABYSMAL to base near


    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        entity.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
            playerRadiation.addRadiation((insideintensity / 20));
//			player.sendSystemMessage(Component.literal(String.valueOf(playerRadiation.getRadiation()))); // t3esting purposes
        });

    }
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide) {
            if (getFluidState(state).isSource()) {
                level.scheduleTick(pos, this, 1);

            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.scheduleTick(pos, this, 4);
        radiate(level, pos);
    }

    public void radiate(ServerLevel level, BlockPos pos) {

        AABB boundingBox = new AABB(
                pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                pos.getX() + 1 + radius, pos.getY() + 1 + radius, pos.getZ() + 1 + radius
        );

        java.util.List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, boundingBox);

// 4. Loop through the entities
        for (LivingEntity entity : entities) {
            entity.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
                double distanceSqr = entity.distanceToSqr(pos.getCenter());
                playerRadiation.addRadiation(((nearbyintensity * (1.0/Math.max(distanceSqr, 1.0) / 5 /* divided by 5 because we do every 4 ticks (so 5 times per sec) */))));
//			player.sendSystemMessage(Component.literal(String.valueOf(playerRadiation.getRadiation()))); // t3esting purposes
            });

        }

    }


}
