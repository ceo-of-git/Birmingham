package xyz.nasasupercomputer.birmingham.EventHandlers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Datagen.SavedData.PillData;

// Please someone tell me the difference between Bus.FORGE and bus.MOD
@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
    	Player joiningPlayer = event.getEntity();

    	// joiningPlayer.sendSystemMessage(Component.literal("Tetsing!").withStyle(ChatFormatting.ITALIC));
    	// Randomize Pills
        if (event.getEntity().level() instanceof ServerLevel level) {
            PillData pillData = level.getDataStorage().computeIfAbsent(PillData::load, () -> PillData.createData(level), "pill_data");
        }
    }
}
