package xyz.nasasupercomputer.birmingham.Inventories.Computers;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlockEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TerminalScreen extends Screen {

	private static final Component GUI_TITLE = Component.literal(" ").withStyle(ChatFormatting.YELLOW);
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_terminal_monitor.png");
    private static final ResourceLocation TERMINAL_FONT_DIRECTORY = new ResourceLocation("birmingham", "terminal");
    private final BlockEntity desktopBlockEntity;

    private static final int TERMINAL_LINE_DISPLAY_LIMIT = 10; //

    private List<Component> terminalFeedback = new ArrayList<Component>();

    private EditBox input;

    public TerminalScreen(BlockEntity desktopBlockEntity) {
        super(GUI_TITLE);
        this.desktopBlockEntity = desktopBlockEntity;

        sendTerminalFeedback(Component.translatable("terminal.boot"));
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - 256) / 2;
        int y = (height - 256) / 2;

        input = new EditBox(this.font, x + 50, y + 145, 140, 20, Component.literal("").withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY)));
        
        input.setTextColor(0x2B9C19);
        input.setTextColorUneditable(0x2B9C19);
        input.setCanLoseFocus(false);
        input.setBordered(true);
        input.setMaxLength(50);
        input.setResponder(this::onTerminalInput);

        this.addRenderableWidget(input);
        this.setInitialFocus(input);
    }

    public void onTerminalInput(String key){

    }

    // Keycodes https://lexxie.dev/forge/1.20.1/constant-values.html#com.mojang.blaze3d.platform.InputConstants.KEY_NUMPADENTER
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == InputConstants.KEY_RETURN || pKeyCode == InputConstants.KEY_NUMPADENTER) { // ENTER KEY
            sendTerminalCommand(input.getValue());
            input.setValue("");
        }
        else if (pKeyCode == InputConstants.KEY_ESCAPE) { // ESC: Close GUI
            this.minecraft.player.closeContainer();
        }

        return !input.keyPressed(pKeyCode, pScanCode, pModifiers) && !input.canConsumeInput() && super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    public void sendTerminalFeedback(Component component){
        terminalFeedback.add(0, component.copy().withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY).withColor(ChatFormatting.GREEN)));

        if (terminalFeedback.size() > TERMINAL_LINE_DISPLAY_LIMIT) { terminalFeedback.remove(10); } // Limit the lines
    }

    public void sendTerminalCommand(String commandSent) {
        if (commandSent == null || commandSent.isBlank()) { return; }
        String[] fullCommand = commandSent.split("\\s+");
        String command = fullCommand[0].toLowerCase();
        String[] commandArgs = Arrays.copyOfRange(fullCommand, 1, fullCommand.length);

        switch (command) {
            case "help":                    // HELP: Displays all other non-secret terminal commands
                if (checkCmdArg(commandArgs, 0, "shop")) { sendTerminalFeedback(Component.translatable("terminal.command.help.shop")); }
                else if (checkCmdArg(commandArgs, 0, "stats")) { sendTerminalFeedback(Component.translatable("terminal.command.help.stats")); }
                else if (checkCmdArg(commandArgs, 0, "clear")) { sendTerminalFeedback(Component.translatable("terminal.command.help.clear")); }
                else {
                    sendTerminalFeedback(Component.translatable("terminal.command.help"));
                    sendTerminalFeedback(Component.translatable("terminal.command.help.additional"));
                    sendTerminalFeedback(Component.translatable("terminal.command.help.additional.2"));
                }
                break;

            case "shop":                    // SHOP: Buy things
                if (checkCmdArg(commandArgs, 0, "catalog")) {
                    // Catalog: list all buyable items
                } else if (checkCmdArg(commandArgs, 0, "buy")) {
                    // Buy: buy an item
                } else {
                    // ????
                }
                break;

            case "stats":                   // STATS: Display Computational Stats
                if (desktopBlockEntity instanceof DesktopBlockEntity desktopBE) {
                    sendTerminalFeedback(Component.translatable("command.stats.power").append(Component.literal(String.valueOf(desktopBE.GetProperties().computePower()))));
                    sendTerminalFeedback(Component.translatable("command.stats.speed").append(Component.literal(desktopBE.GetProperties().computeSpeed() * 100 + "%")));
                    sendTerminalFeedback(Component.translatable("command.stats.efficiency").append(Component.literal(desktopBE.GetProperties().powerEfficiency() * 100 + "%")));
                }
                break;

            case "clear":                   // CLEAR: Clears the screen
                for (int i = 0; i < TERMINAL_LINE_DISPLAY_LIMIT + 1; i++) {
                    sendTerminalFeedback(Component.empty());
                }
                break;

            case "instantly_close_game":    // Secret Command: Closes the game
                Minecraft.getInstance().close();
                break;

            case "ping":                    // Secret Command: Responds with "Pong!"
                sendTerminalFeedback(Component.literal("Pong!").withStyle(ChatFormatting.WHITE));
                break;

            default: // Unknown Command
                sendTerminalFeedback(Component.translatable("terminal.unknown.input").append(Component.literal(" \"" + command + "\"")));
                break;
        }
    }

    public boolean checkCmdArg(String[] args, int index, String checkFor){
        if (args == null || index >= args.length) {
            return false;
        }

        return args[index].equals(checkFor);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

        int x = (width - 256) / 2;
        int y = (height - 256) / 2;
        int feedbackX = (width - 256) / 2 + 135;
        int feedbackY = (width - 256) / 2 + 50;

        // Background is typically rendered first
        this.renderBackground(graphics);

        // Feedback text
        graphics.pose().pushPose();
        graphics.pose().scale(0.75f, 0.75f, 1.0f);
        for (Component feedback : terminalFeedback) {
            graphics.drawString(font, feedback, feedbackX, feedbackY, 0x2B9C19);

            feedbackY -= font.lineHeight;
        }
        graphics.pose().popPose();

        // Render box before screen because the text aurafarms anyways and goes on top.
        input.render(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 256, 256);

        // graphics.drawString(font, Component.literal("hiihihih").withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY)).withStyle(ChatFormatting.GREEN), x, y, 0xFFFFFF);
    }

    @Override
    public void tick() {
        input.tick();
        super.tick();

        // Update the terminal feedback text
        // Technically super unoptimized, but this is so little and the gui is only open so often so I don't mind.
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
