package xyz.nasasupercomputer.birmingham.ItemGems;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID)
public class GemEventHandler {


	

	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {

	}
}
