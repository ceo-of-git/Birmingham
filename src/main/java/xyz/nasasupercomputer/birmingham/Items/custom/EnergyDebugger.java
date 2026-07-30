package xyz.nasasupercomputer.birmingham.Items.custom;

import net.minecraft.ChatFormatting;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.FuelGenerator.FuelGeneratorBlockEntity;
import xyz.nasasupercomputer.birmingham.Radiation.PlayerRadiationProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EnergyDebugger extends Item {
	
    public EnergyDebugger(Properties pProperties) {
        super(pProperties);
    }

    
    @Override public InteractionResult useOn(UseOnContext pContext) {
    	BlockPos debugPos = pContext.getClickedPos();
    	Player pPlayer = pContext.getPlayer();
    	BlockEntity debugBlockEntity = pContext.getLevel().getBlockEntity(debugPos);
    	ItemStack itemstack = pContext.getPlayer().getItemInHand(pContext.getHand());

    	pPlayer.sendSystemMessage(Component.literal(String.format("-----------------------------------")));
    	pPlayer.sendSystemMessage(Component.literal(String.format("Energy Debugger-O-Tron 9000:")));
    	
    	if (debugBlockEntity instanceof FuelGeneratorBlockEntity fuelGen) {
    		pPlayer.sendSystemMessage(Component.literal(String.format("- Detected Fuel Generator Block")));
    		pPlayer.sendSystemMessage(Component.literal(String.format("Current Energy - " + fuelGen.getEnergyStored())));
    		pPlayer.sendSystemMessage(Component.literal(String.format("Maximum Energy - " + fuelGen.getMaxEnergyStored())));
    		pPlayer.sendSystemMessage(Component.literal(String.format("Remaining Burn - " + fuelGen.remainingBurnTime)));
    	}
    	
    	pPlayer.sendSystemMessage(Component.literal(String.format("-----------------------------------")));
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(Component.literal("Creative Only").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		}
    }
}


