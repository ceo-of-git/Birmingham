package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

// SCREENS are the client-side version of guis..
// bad explanation but whatever
public class Printer3DScreen extends AbstractContainerScreen<Printer3DMenu> {

	// public static final Component GUI_TITLE = Component.translatable("inventory.birmingham.3d_printer").withStyle(ChatFormatting.YELLOW);
	private static final ResourceLocation TERMINAL_FONT_DIRECTORY = new ResourceLocation("birmingham", "terminal");
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_3d_printer.png");

	public Printer3DScreen(Printer3DMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, Component.empty());
		
        this.imageWidth = 176;
        this.imageHeight = 185;
        
	    this.titleLabelX = 5;
	    
	    this.inventoryLabelX = 8;
	    this.inventoryLabelY = 89;
	}
	
    private void renderProgressBar(GuiGraphics guiGraphics) {
    	int x = this.leftPos;
    	int y = this.topPos;
    	
    	guiGraphics.blit(GUI_TEXTURE, x + 45, y + 61, 2, 202, menu.getScaledProgress(), 8);
            
//            int l = this.menu.getBurnProgress();
//            pGuiGraphics.blit(this.texture, i + 79, j + 34, 176, 14, l + 1, 16);
    }

	// mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

		int x = (width - 256) / 2;
		int y = (height - 256) / 2;
		int screenTextX = (width - 256) / 2 + 90;
		int screenTextY = (width - 256) / 2 + 50;

	    this.renderBackground(graphics);

	    super.render(graphics, mouseX, mouseY, partialTick);

		// Feedback text
		graphics.pose().pushPose();
		graphics.pose().scale(0.40f, 0.40f, 1.0f);

		Component line1 = Component.literal("test1").copy().withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY).withColor(ChatFormatting.GREEN));
		Component line2 = Component.literal("test2").copy().withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY).withColor(ChatFormatting.GREEN));
		Component line3 = Component.literal("test3").copy().withStyle(style -> style.withFont(TERMINAL_FONT_DIRECTORY).withColor(ChatFormatting.GREEN));
		graphics.drawString(font, line1, screenTextX, screenTextY, 0x2B9C19);
		graphics.drawString(font, line2, screenTextX, screenTextY + font.lineHeight, 0x2B9C19);
		graphics.drawString(font, line3, screenTextX, screenTextY + font.lineHeight, 0x2B9C19);

		graphics.pose().popPose();

	    // Progress Bar
	    renderProgressBar(graphics);

	    // Render things after widgets (tooltips)
	    this.renderTooltip(graphics, mouseX, mouseY);
	}
	
	// Called when the player tries to exit the screen in any way (ESC or E)
	@Override
	public void onClose() {
	    // Stop any handlers here
	    // Call last in case it interferes with the override
	    super.onClose();
	}

	// Called after onClose iirc for like complicated stuff
	@Override
	public void removed() {
	    // Reset initial states here
	    // Call last in case it interferes with the override
	    super.removed()
	;}

	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
	}
}
