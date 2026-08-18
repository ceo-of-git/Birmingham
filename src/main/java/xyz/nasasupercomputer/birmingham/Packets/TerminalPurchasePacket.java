package xyz.nasasupercomputer.birmingham.Packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Custom.Package.PackageBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Custom.Package.PackageBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopProperties;
import xyz.nasasupercomputer.birmingham.Capabilities.PlayerMoneyData;
import xyz.nasasupercomputer.birmingham.MainRegistry;
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
    private int shopEntryID;
    private int X;
    private int Z;

    private static final int DELIVERY_MAX_DISTANCE = 50;

    public TerminalPurchasePacket(int shopEntryID, int X, int Z, BlockPos desktopPos) { this.shopEntryID = shopEntryID; this.X = X; this.Z = Z; this.desktopPos = desktopPos; }
    public TerminalPurchasePacket(FriendlyByteBuf buf) { this.desktopPos = buf.readBlockPos(); this.shopEntryID = buf.readInt(); this.X = buf.readInt(); this.Z = buf.readInt(); }
    public void toBytes(FriendlyByteBuf buf) { }
    public void encode(FriendlyByteBuf friendlyByteBuf) { friendlyByteBuf.writeBlockPos(desktopPos); friendlyByteBuf.writeInt(shopEntryID); friendlyByteBuf.writeInt(X); friendlyByteBuf.writeInt(Z);}
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
            if (state.getBlock() instanceof DesktopBlock desktopBlock) {
                properties = desktopBlock.GetProperties(level, this.desktopPos);
            } else {
                sendResponsePacket(false, player);
                return;
            }

            // Get Shop Entry
//            player.sendSystemMessage(Component.literal("getting shop"));
            if (shopEntryID >= 0) {
                if (TerminalShop.getAvailableShopItems().size() > shopEntryID) { shopEntry = TerminalShop.getAvailableShopItems().get(shopEntryID); }
                else { sendResponsePacket(false, player); ctx.get().setPacketHandled(true); return; }
            } else { sendResponsePacket(false, player); ctx.get().setPacketHandled(true); return; }

//            player.sendSystemMessage(Component.literal("getting powah!"));
//            player.sendSystemMessage(Component.literal(properties.toString()));
//            player.sendSystemMessage(Component.literal(String.valueOf(shopEntry.powerRequirementToView() + " / ") + String.valueOf(properties.computePower())));
            // Check power requirements
            if (shopEntry.powerRequirementToView() <= properties.computePower()) {
//                player.sendSystemMessage(Component.literal("Checking $$$$ requirement"));
                // Check $$$ Requirements
                if (PlayerMoneyData.getValue(player) >= shopEntry.dollarCost()) {
//                    player.sendSystemMessage(Component.literal("Checking location closeness requirement"));
                    // Check if spawn location is close enough
                    if (Math.abs(desktopPos.getX() - X) <= DELIVERY_MAX_DISTANCE && Math.abs(desktopPos.getZ() - Z) <= DELIVERY_MAX_DISTANCE) {
//                        player.sendSystemMessage(Component.literal("Checking location block requirement"));
                        // Check if spawn block is valid
                        BlockPos packageSpawnPosition = new BlockPos(X, 256, Z);
                        if (level.getBlockState(packageSpawnPosition).getBlock().defaultBlockState().isAir()) {
//                            player.sendSystemMessage(Component.literal("spawning package"));
                            PlayerMoneyData.setValue(player, PlayerMoneyData.getValue(player) - shopEntry.dollarCost());

                            // Setup Package Items
                            List<ItemStack> items = new ArrayList<>();
                            items.add(shopEntry.itemToPurchase().copy());
//                            player.sendSystemMessage(Component.literal("Items - " + items.get(0).getDisplayName().getString()));

                            PackageBlock packageBlock = (PackageBlock)BlockRegistry.PACKAGE.get();
                            packageBlock.placePackage(level, packageSpawnPosition, items);

                            sendResponsePacket(true, player);
                            ctx.get().setPacketHandled(true);
                            return;
                        }
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
