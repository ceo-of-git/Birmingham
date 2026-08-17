package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

// The client asking the server to see how much $$$ you have
// to prevent nay sayers from larping being elon musk
public class TerminalBalancePacket {

    public TerminalBalancePacket() { }
    public TerminalBalancePacket(FriendlyByteBuf buf) { }
    public void toBytes(FriendlyByteBuf buf) { }
    public void encode(FriendlyByteBuf friendlyByteBuf) {}
    public static TerminalBalancePacket decode(FriendlyByteBuf friendlyByteBuf) {return new TerminalBalancePacket(friendlyByteBuf); }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) { return; }
            float balance = PlayerMoneyData.getValue(player);

            NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new TerminalBalanceResponsePacket(balance)
            );
        });
        ctx.get().setPacketHandled(true);
    }

}
