package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkManager {
    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("birmingham", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void register() {
        // Terminal Balance-Check Packet
        INSTANCE.registerMessage(packetId++, TerminalBalancePacket.class, TerminalBalancePacket::encode, TerminalBalancePacket::decode, TerminalBalancePacket::handle);
        INSTANCE.registerMessage(packetId++, TerminalBalanceResponsePacket.class, TerminalBalanceResponsePacket::encode, TerminalBalanceResponsePacket::decode, TerminalBalanceResponsePacket::handle);

        // Terminal Shop Purchase Packet
        INSTANCE.registerMessage(packetId++, TerminalPurchasePacket.class, TerminalPurchasePacket::encode, TerminalPurchasePacket::decode, TerminalPurchasePacket::handle);
        INSTANCE.registerMessage(packetId++, TerminalPurchaseResponsePacket.class, TerminalPurchaseResponsePacket::encode, TerminalPurchaseResponsePacket::decode, TerminalPurchaseResponsePacket::handle);
    }
}