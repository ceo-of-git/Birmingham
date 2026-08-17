package xyz.nasasupercomputer.birmingham.Items.custom;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.world.level.Level;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;

public class Money extends Item {

	private double worth;
	public boolean isAuthentic = true;
	
	public Money(Properties pProperties, double worth, boolean isAuthentic) {
		super(pProperties);
		
		this.worth = worth;
		this.isAuthentic = isAuthentic;
	}

	public double getWorth() {
		return worth;
	}
	
	public boolean getAuthenticity() {
		return isAuthentic;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

		if (pPlayer instanceof ServerPlayer serverPlayer){
			if (getAuthenticity()) {
				// Authentic
				PlayerMoneyData.setValue(serverPlayer, (long)(PlayerMoneyData.getValue(serverPlayer) + this.getWorth()));
				pPlayer.sendSystemMessage(Component.translatable("birmingham.money.redeem").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC).append(Component.literal("(" + PlayerMoneyData.getValue(serverPlayer) + "$)")));
				pPlayer.getItemInHand(pUsedHand).shrink(1);
			}
			else{
				// Fraudulent (Pretend that you claimed it) (but it doesn't do anything)
				pPlayer.sendSystemMessage(Component.translatable("birmingham.money.redeem").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC).append(Component.literal("(" + PlayerMoneyData.getValue(serverPlayer) + "$)")));
				pPlayer.getItemInHand(pUsedHand).shrink(1);
			}
		}

		return super.use(pLevel, pPlayer, pUsedHand);
	}

	@Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag ) {
    	tooltip.add(Component.literal(String.format("$%.2f", this.getWorth())).withStyle(ChatFormatting.GREEN));
    	if (Screen.hasShiftDown()) {
    		if (isAuthentic) {
    			tooltip.add(Component.translatable("tooltip.birmingham.money.authentic").withStyle(ChatFormatting.GREEN));
    		}
    		else {
    			tooltip.add(Component.translatable("tooltip.birmingham.money.fraudulent").withStyle(ChatFormatting.RED));
    		}
    	}

        super.appendHoverText(stack, level, tooltip, flag);
    }

}
