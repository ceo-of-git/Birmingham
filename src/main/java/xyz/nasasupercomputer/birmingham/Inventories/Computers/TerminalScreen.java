package xyz.nasasupercomputer.birmingham.Inventories.Computers;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TerminalScreen extends Screen {

	private static final Component GUI_TITLE = Component.literal(" ").withStyle(ChatFormatting.YELLOW);
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_terminal_monitor.png");
    private static final ResourceLocation TERMINAL_FONT_DIRECTORY = new ResourceLocation("birmingham", "terminal");

    private List<Component> terminalFeedback = new ArrayList<Component>();

    private EditBox input;

    public TerminalScreen() {
        super(GUI_TITLE);
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - 256) / 2;
        int y = (height - 256) / 2;

        input = new EditBox(this.font, x, y + 62, 140, 20, Component.literal("").withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY)));
        
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

    }

    public void sendTerminalCommand(String commandSent) {
        String command = commandSent.toLowerCase();

        switch (command) {
            case "help":                    // HELP: Displays all other non-secret terminal commands
                sendTerminalFeedback(Component.translatable("terminal.command.help").append(Component.translatable("terminal.command.help.additional").withStyle(ChatFormatting.ITALIC)));
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

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    	float scale = 2.0F;
    	// graphics.pose().scale(scale, scale, 1.0F);
    	
        int x = (width / (int)scale - 256) / 2;
        int y = (height / (int)scale - 256) / 2;

    	// Background is typically rendered first
        this.renderBackground(graphics);

        // Render box before screen because the text aurafarms anyways and goes on top.
        input.render(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 256, 256);

        graphics.drawString(font, Component.literal("hiihihih").withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY)).withStyle(ChatFormatting.GREEN), x, y, 0xFFFFFF);
    }

    @Override
    public void tick() {
        input.tick();
        super.tick();

        // Update the terminal feedback text
        // Technically super unoptimized, but this is so little and the gui is only open so often so I don't mind.
    }
}
