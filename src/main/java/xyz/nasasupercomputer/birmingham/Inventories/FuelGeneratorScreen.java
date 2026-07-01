package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FuelGeneratorScreen extends AbstractContainerScreen<FuelGeneratorMenu> {

	public static final Component GUI_TITLE = Component.translatable("inventory.birmingham.fuel_generator").withStyle(ChatFormatting.RED);
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_fuel_generator.png");
    
	@SuppressWarnings("unchecked") // shut up
	public FuelGeneratorScreen(FuelGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, GUI_TITLE);
		
		// 176, 185 "per gui"
		// full thing is \/
        this.imageWidth = 176;
        this.imageHeight = 185;
        
	    this.titleLabelX = 5;
	    
	    this.inventoryLabelX = 8;
	    this.inventoryLabelY = 89;
	}

	// mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
	    // Background is typically rendered first
	    this.renderBackground(graphics);

	    // Render things here before widgets (background textures)

	    // Then the widgets if this is a direct child of the Screen
	    super.render(graphics, mouseX, mouseY, partialTick);
	    
	    // Render Toggle Switch
	    renderToggleSwitch(graphics);
	    
	    // Render FE Tank

	    
	    // Render things after widgets (tooltips)
	    this.renderTooltip(graphics, mouseX, mouseY);
	}
	
    private void renderToggleSwitch(GuiGraphics guiGraphics) {
    	int x = this.leftPos;
    	int y = this.topPos;
    	
    	boolean isToggled = this.menu.getSmelting();
    	if (isToggled) {
    		// Machine is ON
    		guiGraphics.blit(GUI_TEXTURE, x + 151, y + 23, 57, 186, 17, 27, 17, 27);
    	}
    	else {
    		// Machine is OFF
    		guiGraphics.blit(GUI_TEXTURE, x + 151, y + 23, 39, 186, 17, 27, 17, 27);
    	}
    	
            
//            int l = this.menu.getBurnProgress();
//            pGuiGraphics.blit(this.texture, i + 79, j + 34, 176, 14, l + 1, 16);
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

        // 176, 185 "per gui"
        // Make the UI Change if smelting
        boolean isSmelting = this.menu.getSmelting();
        pGuiGraphics.blit(GUI_TEXTURE, x, y, isSmelting ? 176 : 0, 0, 176, 185, 384, 256);
        
	}
}
