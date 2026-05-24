package xyz.nasasupercomputer.birmingham.ItemHazards;

import java.util.List;

import org.spongepowered.asm.mixin.MixinEnvironment.Side;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// For those who don't know that much java (DIDCOOLSTAR) !!!
// an interface is basically just a template with empty methods :)
public interface IHazardType {

	// Applys whatever you want every single tick
	// Whether that be a poison effect or something like it.
	// Is called by HazardSystem.ApplyHazard
	public void PerTickUpdate(ServerPlayer player, ItemStack stack);
	
	// Adds the Hazard Tooltip to the actual item.
	@OnlyIn(Dist.CLIENT)
	void AddHazardTooltip(Player player, List<String> description, ItemStack stack);
	
	// Getter Methods
    double GetIntensity();
    String GetTranslatableTitle();
    String GetTranslatableDescription();
}
