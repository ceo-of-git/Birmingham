package xyz.nasasupercomputer.birmingham.ItemHazards.Types;

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
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;

public class HazardToxic implements IHazardType {

	public String translatableTitle;
	public double intensity;
	
	
	// Constructor
	public HazardToxic(double intensity, String translatableTitle) {
		this.intensity = intensity;
		this.translatableTitle = translatableTitle;
	}
	
	@Override
	public void PerTickUpdate(ServerPlayer player, ItemStack stack) {
		// Remember that this could be running many times EVERY TICK,
		// if you're not careful when optimizing these the lag will be INSANE!!!!
		MobEffectInstance effect = player.getEffect(MobEffects.POISON);
		
		// When holding the item, if you're not in creative mode. do stuff
		if (player.gameMode.getGameModeForPlayer() != GameType.CREATIVE){
			if (effect == null || effect.getDuration() < 30) {
			    player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 2));
			    if (intensity > 1) {
			    	player.addEffect(new MobEffectInstance(MobEffects.WITHER, 160, (int)Math.ceil(intensity)));
			    	player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, (int)Math.ceil(intensity)));
			    }
			}
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void AddHazardTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§2[ " + I18n.get(this.translatableTitle) + " ]§r");
	}

	@Override
	public double GetIntensity() {
		return this.intensity;
	}

	@Override
	public String GetTranslatableTitle() {
		return this.translatableTitle;
	}
}
