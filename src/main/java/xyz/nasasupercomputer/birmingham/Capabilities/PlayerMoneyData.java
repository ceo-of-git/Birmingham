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

    public static void setValue(ServerPlayer player, float value){
        player.getPersistentData().putFloat(KEY, value);
    }

    public static float getValue(ServerPlayer player){
        CompoundTag data = player.getPersistentData();
        return data.getFloat(KEY);
    }

    // Transfers money post-death
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CompoundTag oldData = event.getOriginal().getPersistentData();

        if (oldData.contains(KEY)) {

            float oldValue = oldData.getFloat(KEY);
            float newMoneyValue = 0;
            if (oldValue != 0) { newMoneyValue = oldValue / 2L; }

            event.getEntity().getPersistentData().putFloat(KEY, newMoneyValue);
            event.getEntity().sendSystemMessage(Component.translatable("birmingham.death.money_loss").withStyle(ChatFormatting.RED).append(Component.literal(newMoneyValue + "$)").withStyle(ChatFormatting.RED)));
        }
    }
}
