package xyz.nasasupercomputer.birmingham.Capabilities;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID)
public class PlayerMoneyData {
    private static final String KEY = "Money";

    public static void setValue(ServerPlayer player, long value){
        player.getPersistentData().putLong(KEY, value);
    }

    public static long getValue(ServerPlayer player){
        CompoundTag data = player.getPersistentData();
        return data.getLong(KEY);
    }

    // Transfers money post-death
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CompoundTag oldData = event.getOriginal().getPersistentData();

        if (oldData.contains(KEY)) {

            long oldValue = oldData.getLong(KEY);
            long newMoneyValue = 0;
            if (oldValue != 0) { newMoneyValue = oldValue / 2L; }

            event.getEntity().getPersistentData().putLong(KEY, newMoneyValue);
            event.getEntity().sendSystemMessage(Component.translatable("birmingham.death.money_loss").withStyle(ChatFormatting.RED).append(Component.literal(newMoneyValue + "$)").withStyle(ChatFormatting.RED)));
        }
    }
}
