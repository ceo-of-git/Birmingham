package xyz.nasasupercomputer.birmingham.ItemGems.Types;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.resources.language.I18n;
import xyz.nasasupercomputer.birmingham.ItemGems.IGemType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class GemFlame implements IGemType {
	
	
	// Constructor
	public GemFlame() {
		
	}
	
	//@Override
	public float RegisterDamage(LivingEntity attacker, LivingEntity victim, ItemStack stack, float damage) {
		// Remember that this could be running many times EVERY TICK,
		// if you're not careful when optimizing these the lag will be INSANE!!!!
		return damage;
	}
	
	//@Override
	@OnlyIn(Dist.CLIENT)
	public void AddGemTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§2[ " + I18n.get("gem.flame.title") + " ]§r");
	}
}
