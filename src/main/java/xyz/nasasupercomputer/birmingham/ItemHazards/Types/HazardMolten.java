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
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;

public class HazardMolten implements IHazardType {

	private String translatableTitle;
	private double intensity;
	private String translatableDescription;
	
	
	// Constructor
	public HazardMolten(double intensity, String translatableTitle, String translatableDescription) {
		this.intensity = intensity;
		this.translatableTitle = translatableTitle;
		this.translatableDescription = translatableDescription;
	}
	
	@Override
	public void PerTickUpdate(ServerPlayer player, ItemStack stack) {
		// Ignite player when held.
		if (!player.fireImmune() && !player.isOnFire()){
			player.setSecondsOnFire((int)(intensity * 3));
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void AddHazardTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§6[ " + I18n.get(this.translatableTitle) + " ]§r");
		
		if (Screen.hasShiftDown()) {
			description.add("§8- " + I18n.get(this.translatableDescription));
		}
	}

	@Override
	public double GetIntensity() {
		return this.intensity;
	}

	@Override
	public String GetTranslatableTitle() {
		return this.translatableTitle;
	}
	
	@Override
	public String GetTranslatableDescription() {
		return this.translatableDescription;
	}
}
