package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TerminalShopPurchase {

    public TerminalShopPurchase() {

    }

    public TerminalShopPurchase(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

        });
        ctx.get().setPacketHandled(true);
    }
}
