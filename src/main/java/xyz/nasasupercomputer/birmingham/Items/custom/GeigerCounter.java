package xyz.nasasupercomputer.birmingham.Items.custom;

import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Radiation.PlayerRadiationProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GeigerCounter extends Item {
    String translatableGeigerTitle;
    String translatableShiftGeigerDescription;


    public GeigerCounter(Properties pProperties, String translatableShiftGeigerDescription) {
        super(pProperties);

        this.translatableShiftGeigerDescription = translatableShiftGeigerDescription;
    }


    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        pPlayer.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
            if (!pLevel.isClientSide()) {
                ArrayList<Double> list = playerRadiation.getList();
                double average = list.get(list.size() - 1) - list.get(0);
                pPlayer.sendSystemMessage(Component.literal(String.format("You are currently gaining %.2f RAD/s", average))); // t3esting purposes

                pPlayer.sendSystemMessage(Component.literal(String.format("You currently have %.2f RADs", playerRadiation.getRadiation()))); // t3esting purposes
            }
            //playerRadiation.getRadiation()
        });

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(Component.translatable("tooltip.birmingham.geiger.description_shift"));
		} else {
			pTooltipComponents.add(Component.translatable("tooltip.birmingham.geiger.description"));
		}
    }

}


