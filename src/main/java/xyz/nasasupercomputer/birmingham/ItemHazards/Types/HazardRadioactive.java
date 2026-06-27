package xyz.nasasupercomputer.birmingham.ItemHazards.Types;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.telemetry.TelemetryProperty.GameMode;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;
import xyz.nasasupercomputer.birmingham.Radiation.PlayerRadiationProvider;

import java.util.List;
import java.util.logging.Logger;

public class HazardRadioactive implements IHazardType {

	private String translatableTitle = "hazard.birmingham.radioactive.title";
	private double intensity;
	private String translatableDescription = "nothing lmfao";


	// Constructor
	public HazardRadioactive(double intensity) {
		this.intensity = intensity;
	}
	
	@Override
	public void PerTickUpdate(ServerPlayer player, ItemStack stack) {
		
		if (player.gameMode.getGameModeForPlayer() != GameType.CREATIVE){
			player.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
				playerRadiation.addRadiation((intensity / 20) * stack.getCount());
			});
		}

		// Ignite player when held.
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void AddHazardTooltip(Player player, List<String> description, ItemStack stack) {
		description.add("§a[ " + I18n.get(translatableTitle, (Math.round(intensity * 100.0) / 100.0) * stack.getCount()) + " ]§r");
		
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
		return translatableDescription;
	}
}
