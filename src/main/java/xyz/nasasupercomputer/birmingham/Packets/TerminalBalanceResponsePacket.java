package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import xyz.nasasupercomputer.birmingham.Inventories.Computers.TerminalScreen;

import java.util.function.Supplier;

// the server telling the client how much $$$ u got
public class TerminalBalanceResponsePacket {

    private final float balance;

    public TerminalBalanceResponsePacket(float balance) { this.balance = balance; }
    public static void encode(TerminalBalanceResponsePacket packet, FriendlyByteBuf buf) { buf.writeFloat(packet.balance); }
    public static TerminalBalanceResponsePacket decode(FriendlyByteBuf buf) { return new TerminalBalanceResponsePacket(buf.readFloat()); }

    public static void handle(TerminalBalanceResponsePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().screen instanceof TerminalScreen terminalScreen) {
                terminalScreen.sendTerminalFeedback(Component.translatable("terminal.command.bal").append(Component.literal(String.format("$%.2f", packet.balance))));
            }

            // You need a reference to your TerminalScreen here
        });

        ctx.get().setPacketHandled(true);
    }
}