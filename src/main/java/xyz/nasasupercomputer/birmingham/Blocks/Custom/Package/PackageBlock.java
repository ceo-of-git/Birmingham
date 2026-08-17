package xyz.nasasupercomputer.birmingham.Blocks.Custom.Package;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PackageBlock extends FallingBlock implements EntityBlock {
    public PackageBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isShiftKeyDown()) {
//            // TEST CODE
//            if (pLevel.getBlockEntity(pPos) instanceof PackageBlockEntity packageEntity) {
//                List<ItemStack> items = new ArrayList<>();
//
//                items.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("birmingham:capacitor")), 978));
//                items.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("birmingham:transistor")), 200));
//                items.add(new ItemStack(Items.IRON_INGOT, 10));
//                items.add(new ItemStack(Items.APPLE, 3));
//
//                packageEntity.setPackageItems(items);
//                pPlayer.sendSystemMessage(Component.literal("Set Items"));
//            }

            return InteractionResult.PASS;
        }

        if (!pLevel.isClientSide()){
            if (pLevel.getBlockEntity(pPos) instanceof PackageBlockEntity packageBE) {

                // Drop Package Items
                for (ItemStack item : packageBE.getPackageItems()) {
                    pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, item));
                }

                // Kill Package Block
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 0);
            }
        }
        pLevel.playSound(pPlayer, pPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0f, 1.0f);
        Random rng = new Random();
        for (int i = 0; i < 16; i++) {
            pLevel.addParticle(ParticleTypes.CLOUD, true, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 0.05 * (rng.nextFloat(3) - 2), 0.05 * (rng.nextFloat(3) - 2), 0.05 * (rng.nextFloat(3) - 2));
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PackageBlockEntity(pPos, pState);
    }
}
