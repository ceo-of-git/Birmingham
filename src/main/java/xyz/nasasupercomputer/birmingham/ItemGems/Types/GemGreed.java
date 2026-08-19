package xyz.nasasupercomputer.birmingham.ItemGems.Types;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;
import xyz.nasasupercomputer.birmingham.ItemGems.IGemType;

import java.util.List;

public class GemGreed implements IGemType {


	// Constructor
	public GemGreed() {
		
	}
	
	@Override
	public LivingDamageEvent RegisterDamage(LivingEntity attacker, LivingEntity victim, ItemStack stack, LivingDamageEvent event) {
		return event;
	}
	
	@Override
	public LivingDamageEvent RegisterTaken(LivingEntity victim, ItemStack stack, LivingDamageEvent event) {
		event.setAmount((float) (event.getAmount()*1.3));
		return event;
	}

	@Override
	public LivingDeathEvent OnKill(LivingEntity attacker, LivingEntity victim, ItemStack stack, LivingDeathEvent event) {
		if (victim instanceof ServerPlayer victimPlayer && attacker instanceof ServerPlayer attackerPlayer) {
			float moneyToSteal = PlayerMoneyData.getValue(victimPlayer) * 0.20f;
			PlayerMoneyData.setValue(victimPlayer, PlayerMoneyData.getValue(victimPlayer) - moneyToSteal);
			PlayerMoneyData.setValue(attackerPlayer, PlayerMoneyData.getValue(attackerPlayer) + moneyToSteal);
			attackerPlayer.sendSystemMessage(Component.translatable("gems.birmingham.greed.leech" + moneyToSteal));
		}
		return event;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void AddGemTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§6" + I18n.get("gems.birmingham.greed.title") + "§r");
		
		if (Screen.hasShiftDown()) {
			description.add(I18n.get("gems.birmingham.greed.description"));
		}
	}


}
