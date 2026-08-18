package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Custom.Package.PackageBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.DesktopBlockEntityBase;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopProperties;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.IDesktopType;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;
import xyz.nasasupercomputer.birmingham.Shops.TerminalShop;
import xyz.nasasupercomputer.birmingham.Shops.TerminalShopEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// The client asking the server to try and buy a thing
// server will be like OK! and drop shit or make fun of you for being broke
public class TerminalPurchasePacket {

    private BlockPos desktopPos;
    private TerminalShopEntry shopEntry;
    private int shopEntryIDToBuy;
    private int X;
    private int Z;

    private static final int DELIVERY_MAX_DISTANCE = 50;

    public TerminalPurchasePacket(int shopEntryID, int X, int Z, BlockPos desktopPos) { this.shopEntryIDToBuy = shopEntryID; this.X = X; this.Z = Z; this.desktopPos = desktopPos; }
    public TerminalPurchasePacket(FriendlyByteBuf buf) { this.desktopPos = buf.readBlockPos(); this.shopEntryIDToBuy = buf.readInt(); this.X = buf.readInt(); this.Z = buf.readInt(); }
    public void toBytes(FriendlyByteBuf buf) { }
    public void encode(FriendlyByteBuf friendlyByteBuf) { friendlyByteBuf.writeBlockPos(desktopPos); friendlyByteBuf.writeInt(shopEntryIDToBuy); friendlyByteBuf.writeInt(X); friendlyByteBuf.writeInt(Z);}
    public static TerminalPurchasePacket decode(FriendlyByteBuf friendlyByteBuf) { return new TerminalPurchasePacket(friendlyByteBuf); }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) { return; }
            Level level = player.level();
            BlockState state = level.getBlockState(desktopPos);

//            player.sendSystemMessage(Component.literal("getting desktop"));
            DesktopProperties properties;
            // Get Desktop BE
            if (state.getBlock() instanceof IDesktopType desktopBlock) {
                properties = desktopBlock.GetProperties();
            } else {
                sendResponsePacket(false, player);
                return;
            }

            // Get Shop Entry
            List<TerminalShopEntry> shopEntries = TerminalShop.getAvailableShopItems();
            if (level.getBlockEntity(desktopPos) instanceof DesktopBlockEntityBase) {
                shopEntries = TerminalShop.getAvailableShopItems(properties.computePower());

                if (shopEntryIDToBuy >= 0 || shopEntryIDToBuy < shopEntries.size()) {
                    this.shopEntry = shopEntries.get(shopEntryIDToBuy);
                }
                } else { sendResponsePacket(false, player); ctx.get().setPacketHandled(true); return; }


            // Check $$$ Requirements
            if (PlayerMoneyData.getValue(player) >= shopEntry.dollarCost()) {

                // Check if spawn location is close enough
                if (Math.abs(desktopPos.getX() - X) <= DELIVERY_MAX_DISTANCE && Math.abs(desktopPos.getZ() - Z) <= DELIVERY_MAX_DISTANCE) {

                    // Check if spawn block is valid
                    BlockPos packageSpawnPosition = new BlockPos(X, 256, Z);
                    if (level.getBlockState(packageSpawnPosition).getBlock().defaultBlockState().isAir()) {
                        // Spawning package & spending $$$
                        PlayerMoneyData.setValue(player, PlayerMoneyData.getValue(player) - shopEntry.dollarCost());

                        // Setup Package Items
                        List<ItemStack> items = new ArrayList<>();
                        items.add(shopEntry.itemToPurchase().copy());

                        PackageBlock packageBlock = (PackageBlock)BlockRegistry.PACKAGE.get();
                        packageBlock.placePackage(level, packageSpawnPosition, items);

                        sendResponsePacket(true, player);
                        ctx.get().setPacketHandled(true);
                        return;
                    }
                }
            }
            sendResponsePacket(false, player);
        });

        ctx.get().setPacketHandled(true);
    }

    public static void sendResponsePacket(boolean purchaseSuccessful, ServerPlayer player){
        NetworkManager.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new TerminalPurchaseResponsePacket(purchaseSuccessful)
        );
    }

}
