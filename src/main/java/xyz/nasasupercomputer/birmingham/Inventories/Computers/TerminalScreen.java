package xyz.nasasupercomputer.birmingham.Inventories.Computers;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TerminalScreen extends Screen {

	private static final Component GUI_TITLE = Component.literal(" ").withStyle(ChatFormatting.YELLOW);
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_terminal_monitor.png");

    public TerminalScreen() {
        super(GUI_TITLE);
    }

    @Override
    protected void init() {
        super.init();

//        // Example Button Code
//        addRenderableWidget(Button.builder(Component.literal("Run"),
//                button -> {
//                    // Handle click
//                })
//                .bounds(this.width / 2 - 40, this.height / 2, 80, 20)
//                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int x = (width - 176);
        int y = (height - 185);
        
    	// Background is typically rendered first
        this.renderBackground(graphics);
        
        // Render things here before widgets (background textures)

        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTick);

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 176, 185);

        // Render things after widgets (string test)
        graphics.drawString(font, "Terminalasdasdasdasd", x, y, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
