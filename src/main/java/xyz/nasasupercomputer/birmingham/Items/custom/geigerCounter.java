package xyz.nasasupercomputer.birmingham.Items.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.radiation.PlayerRadiationProvider;

import javax.annotation.Nullable;
import java.util.List;

public class geigerCounter extends Item {
    String translatableGeigerTitle;
    String translatableShiftGeigerDescription;


    public geigerCounter(Properties pProperties, String translatableShiftGeigerDescription) {
        super(pProperties);

        this.translatableShiftGeigerDescription = translatableShiftGeigerDescription;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        pPlayer.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
            if (!pLevel.isClientSide()) {
                pPlayer.sendSystemMessage(Component.literal(String.format("%.2f", playerRadiation.getRadiation()))); // t3esting purposes
            }
            //playerRadiation.getRadiation()
        });

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable(this.translatableShiftGeigerDescription));
        }
    }


