package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;

// SCREENS are the client-side version of guis..
// bad explanation but whatever
public class AlloyBlastFurnaceScreen extends AbstractContainerScreen<AlloyBlastFurnaceMenu> {

	public static final Component GUI_TITLE = Component.translatable("inventory.birmingham.alloy_blast_furnace");
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_alloy_blast_furnace.png");

    
	@SuppressWarnings("unchecked") // shut up
	public AlloyBlastFurnaceScreen(AlloyBlastFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, GUI_TITLE);
		
        this.imageWidth = 176;
        this.imageHeight = 185;
        
	    this.titleLabelX = 5;
	    
	    this.inventoryLabelX = 8;
	    this.inventoryLabelY = 89;
	}
	
    private void renderProgressBar(GuiGraphics guiGraphics) {
    	int x = this.leftPos;
    	int y = this.topPos;
    	
    	guiGraphics.blit(GUI_TEXTURE, x + 45, y + 41, 2, 202, menu.getScaledProgress(), 8);
            
//            int l = this.menu.getBurnProgress();
//            pGuiGraphics.blit(this.texture, i + 79, j + 34, 176, 14, l + 1, 16);
    }

	// mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
	    // Background is typically rendered first
	    this.renderBackground(graphics);

	    // Render things here before widgets (background textures)

	    // Then the widgets if this is a direct child of the Screen
	    super.render(graphics, mouseX, mouseY, partialTick);
	    
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
