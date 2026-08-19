package xyz.nasasupercomputer.birmingham.ItemGems.Types;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import xyz.nasasupercomputer.birmingham.ItemGems.IGemType;
import net.minecraft.world.entity.LivingEntity;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerRadiationProvider;

public class GemRadioactive implements IGemType {
	
	
	// Constructor
	public GemRadioactive() {
		
	}
	
	@Override
	public LivingDamageEvent RegisterDamage(LivingEntity attacker, LivingEntity victim, ItemStack stack, LivingDamageEvent event) {
		return event; //Does nothing
	}
	
	@Override
	public LivingDamageEvent RegisterTaken(LivingEntity victim, ItemStack stack, LivingDamageEvent event) {
        victim.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
            playerRadiation.addRadiation(20);
        });	
		
		return event; // Apply 20 RADS to Victim
	}

	@Override
	public LivingDeathEvent OnKill(LivingEntity attacker, LivingEntity victim, ItemStack stack, LivingDeathEvent event) {
		return event;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void AddGemTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§6" + I18n.get("gems.birmingham.radioactive.title") + "§r");
		
		if (Screen.hasShiftDown()) {
			description.add(""+ I18n.get("gems.birmingham.radioactive.description"));
		}
	}
}
