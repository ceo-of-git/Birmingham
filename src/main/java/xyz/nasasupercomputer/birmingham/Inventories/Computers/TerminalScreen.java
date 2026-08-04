package xyz.nasasupercomputer.birmingham.Inventories.Computers;

import org.lwjgl.opengl.GL11;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class TerminalScreen extends Screen {

	private static final Component GUI_TITLE = Component.literal(" ").withStyle(ChatFormatting.YELLOW);
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_terminal_monitor.png");
    private static final ResourceLocation TERMINAL_FONT_DIRECTORY = new ResourceLocation("birmingham", "terminal");
    
    private EditBox input;

    public TerminalScreen() {
        super(GUI_TITLE);
    }

    @Override
    protected void init() {
        super.init();
        
        int x = (width - 256) / 2;
        int y = (height - 256) / 2;
        
        input = new EditBox(
        	this.font,
        	x + 20,
        	y + 220,
        	216,
        	20,
        	Component.literal("> ").withStyle(ChatFormatting.GREEN).withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY))
    	);
        
        input.setTextColor(0x00FF00);
        input.setTextColorUneditable(0x005500);
        input.setBordered(false);
        input.setMaxLength(50);
        
        input.setFocused(true);

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
    	float scale = 2.0F;
    	graphics.pose().scale(scale, scale, 1.0F);
    	
        int x = (width / (int)scale - 256) / 2;
        int y = (height / (int)scale - 256) / 2;

        
    	// Background is typically rendered first
        this.renderBackground(graphics);
        
        // Render things here before widgets (background textures)

        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTick);

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 256, 256);

        // Render things after widgets (string test)
        graphics.drawString(font, Component.literal("hiihihih").withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY)).withStyle(ChatFormatting.GREEN), x, y, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
