package xyz.nasasupercomputer.birmingham.ItemGems.Types;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import xyz.nasasupercomputer.birmingham.ItemGems.IGemType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class GemVampiric implements IGemType {
	
	
	// Constructor
	public GemVampiric() {
		
	}
	
	@Override
	public LivingDamageEvent RegisterDamage(LivingEntity attacker, LivingEntity victim, ItemStack stack, LivingDamageEvent event) {
		return event; //Does nothing atm cause hasnt been implemented
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void AddGemTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§6" + I18n.get("gems.birmingham.vampiric.title") + "§r");
		
		if (Screen.hasShiftDown()) {
			description.add(""+I18n.get("gems.birmingham.vampiric.description"));
		}
	}
}
