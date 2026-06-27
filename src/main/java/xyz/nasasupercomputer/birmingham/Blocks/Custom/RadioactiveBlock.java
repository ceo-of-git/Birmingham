package xyz.nasasupercomputer.birmingham.Blocks.Custom;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xyz.nasasupercomputer.birmingham.Radiation.PlayerRadiationProvider;

public class RadioactiveBlock extends Block {

    int radius = 1;
    double intensity = 0.0;


    public RadioactiveBlock(Properties pProperties, int radius, double intensity) {
        super(pProperties);
        this.radius = radius;
        this.intensity = intensity;

    }



    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide) {
                level.scheduleTick(pos, this, 1);

        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.scheduleTick(pos, this, 4);
        radiate(level, pos);
    }

    public void radiate(ServerLevel level, BlockPos pos) {

    	boolean overrideIrradiation = true;
    	
        AABB boundingBox = new AABB(
                pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                pos.getX() + 1 + radius, pos.getY() + 1 + radius, pos.getZ() + 1 + radius
        );

        java.util.List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, boundingBox);

// 4. Loop through the entities
        for (LivingEntity entity : entities) {
        	if (entity instanceof ServerPlayer playuh){
    			if (playuh.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
    				// Player is in creative mode, so we dont give them radiation
    				overrideIrradiation = false;
    			}
        	}
        	
        	if (overrideIrradiation) {
                entity.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
                    double distanceSqr = entity.distanceToSqr(pos.getCenter());
                    playerRadiation.addRadiation(((intensity * (1.0/Math.max(distanceSqr, 1.0) / 5/* divided by 4 because we do every 5 ticks (so 4 times per sec) */))));
//    				player.sendSystemMessage(Component.literal(String.valueOf(playerRadiation.getRadiation()))); // t3esting purposes
                });	
        	}

        }

    }

}
