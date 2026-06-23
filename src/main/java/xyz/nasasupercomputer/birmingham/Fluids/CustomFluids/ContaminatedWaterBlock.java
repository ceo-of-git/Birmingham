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
import xyz.nasasupercomputer.birmingham.radiation.PlayerRadiationProvider;

import java.util.function.Supplier;

public class ContaminatedWaterBlock  extends LiquidBlock {
    public ContaminatedWaterBlock(Supplier<? extends FlowingFluid> fluid, Properties props) {
        super(fluid, props);
    }

    double intensity = 25; // better not touch contaminated water


    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        entity.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
            playerRadiation.addRadiation((intensity / 20));
//			player.sendSystemMessage(Component.literal(String.valueOf(playerRadiation.getRadiation()))); // t3esting purposes
        });

    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.scheduleTick(pos, this, 1); // reschedule FIRST — survives an exception below
        radiate(level, pos);              // if this throws, the loop is already queued
    }

    public void radiate(ServerLevel level, BlockPos pos) {

        double radius = 5.0;
        AABB boundingBox = new AABB(
                pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                pos.getX() + 1 + radius, pos.getY() + 1 + radius, pos.getZ() + 1 + radius
        );

        java.util.List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, boundingBox);

// 4. Loop through the entities
        for (LivingEntity entity : entities) {
            // Perform your action (e.g., apply potion, deal damage)
            entity.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
                playerRadiation.addRadiation((intensity / 20));
//			player.sendSystemMessage(Component.literal(String.valueOf(playerRadiation.getRadiation()))); // t3esting purposes
            });

        }

    }


}
