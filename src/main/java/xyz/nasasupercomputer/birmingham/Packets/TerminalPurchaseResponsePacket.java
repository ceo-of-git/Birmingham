package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;
import xyz.nasasupercomputer.birmingham.Inventories.Computers.TerminalScreen;

import java.util.function.Supplier;

// the server telling the client how much $$$ u got
public class TerminalPurchaseResponsePacket {

    private static boolean purchaseSuccessful = false;
    public TerminalPurchaseResponsePacket(boolean purchaseSuccessful) { this.purchaseSuccessful = purchaseSuccessful; }
    public static void encode(TerminalPurchaseResponsePacket packet, FriendlyByteBuf buf) { buf.writeBoolean(packet.purchaseSuccessful); }
    public static TerminalPurchaseResponsePacket decode(FriendlyByteBuf buf) { return new TerminalPurchaseResponsePacket(buf.readBoolean()); }

    public static void handle(TerminalPurchaseResponsePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().screen instanceof TerminalScreen terminalScreen) {
                // send emssages
                if (purchaseSuccessful){
                    terminalScreen.sendTerminalFeedback(Component.translatable("terminal.command.shop.purchase.successful"));
                }
                else{
                    terminalScreen.sendTerminalFeedback(Component.translatable("terminal.command.shop.purchase.failure"));
                    terminalScreen.sendTerminalFeedback(Component.translatable("terminal.command.shop.purchase.failure.2"));
                }
            }

            // You need a reference to your TerminalScreen here
        });

        ctx.get().setPacketHandled(true);
    }
}