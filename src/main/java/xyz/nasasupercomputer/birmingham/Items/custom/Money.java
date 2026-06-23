package xyz.nasasupercomputer.birmingham.Items.custom;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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
	
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag ) {
    	tooltip.add(Component.literal("$" + this.getWorth()).withStyle(ChatFormatting.GREEN));
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
