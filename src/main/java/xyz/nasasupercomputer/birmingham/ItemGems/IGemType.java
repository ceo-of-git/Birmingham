package xyz.nasasupercomputer.birmingham.ItemGems;

import java.util.List;

import org.spongepowered.asm.mixin.MixinEnvironment.Side;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

// For those who don't know that much java (DIDCOOLSTAR) !!!
// an interface is basically just a template with empty methods :)
public interface IGemType {


	public LivingDamageEvent RegisterDamage(LivingEntity attacker, LivingEntity victim, ItemStack stack, LivingDamageEvent event);
	
	// Adds the GEM Tooltip to the actual item.
	@OnlyIn(Dist.CLIENT)
	void AddGemTooltip(Player player, List<String> description, ItemStack stack);
}
