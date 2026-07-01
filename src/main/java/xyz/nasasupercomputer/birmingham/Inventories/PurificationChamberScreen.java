package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

// SCREENS are the client-side version of guis..
// bad explanation but whatever
public class PurificationChamberScreen extends AbstractContainerScreen<PurificationChamberMenu> {

	public static final Component GUI_TITLE = Component.translatable("inventory.birmingham.purification_chamber").withStyle(ChatFormatting.BLACK);
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("birmingham", "textures/gui/machines/gui_purification_chamber.png");


	@SuppressWarnings("unchecked") // shut up
	public PurificationChamberScreen(PurificationChamberMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, GUI_TITLE);
		
        this.imageWidth = 176;
        this.imageHeight = 185;
        
	    this.titleLabelX = 5;
	    
	    this.inventoryLabelX = 8;
	    this.inventoryLabelY = this.imageHeight - 94;
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

		FluidTank firsttank = this.menu.getBlockEntity().getFluidTank1();
		FluidStack firstfluid = firsttank.getFluid();
		if (firstfluid.isEmpty()) {
			return;
		}

		int fluidHeight1 = getFluidHeight(firsttank);

		if (!isHovering(100, getFluidY(fluidHeight1) - this.topPos, 16, fluidHeight1, mouseX, mouseY)) {
			return;
		}

		Component component = MutableComponent.create(firstfluid.getDisplayName().getContents()).append(" (%s/%s mB)".formatted(firsttank.getFluidAmount(), firsttank.getCapacity()));
		graphics.renderTooltip(this.font, component, mouseX, mouseY);

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
        int x = this.leftPos; // what does this do? No idea. ai overview got me tho
        int y = this.topPos; // see above comment

		pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

		FluidTank firsttank = this.menu.getBlockEntity().getFluidTank1();
		FluidStack firstfluid = firsttank.getFluid();
		if (firstfluid.isEmpty()) {
			return;
		}
		IClientFluidTypeExtensions fluidTypeExtensions1 = IClientFluidTypeExtensions.of(firstfluid.getFluid()); // not sure since this is mostly tutorial but i thnik this gets only the client side stuff
		// (to get the textures)'
		ResourceLocation stillTexture1 = fluidTypeExtensions1.getStillTexture(firstfluid);
		if (stillTexture1 == null) {
			return;
		}
		int fluidHeight1 = getFluidHeight(firsttank);

		TextureAtlasSprite sprite1 = this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture1);
		int tintColor1 = fluidTypeExtensions1.getTintColor(firstfluid);

		float alpha1 = ((tintColor1 >> 24) & 0xFF) / 255f; // concerting the tint to a float somehow. I do not understand a single word in this line.
		float red1 = ((tintColor1 >> 16) & 0xFF) / 255f;
		float green1 = ((tintColor1 >> 8) & 0xFF) / 255f;
		float blue1 = ((tintColor1) & 0xFF) / 255f;

		if (alpha1 == 0) {
			alpha1 = 1;
		}
		pGuiGraphics.setColor(red1, green1, blue1, alpha1);
		pGuiGraphics.blit(
				this.leftPos + 100,
				getFluidY(fluidHeight1),
				0,
				16,
				fluidHeight1,
				sprite1);
		pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);

// i give up bro
//
//        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
//
//		pGuiGraphics.blit(INVENTORY_TEXTURE, x + 7, y + 101, 0, 83, 162, 54);
//		// hotbar
//		pGuiGraphics.blit(INVENTORY_TEXTURE, x + 7, y + 159, 0, 141, 162, 18);

	}

	private static int getFluidHeight(IFluidTank tank) {
		return 48 * tank.getFluidAmount()/tank.getCapacity();
	}

	private int getFluidY(int fluidHeight) {
		return this.topPos + 15 + (48 - fluidHeight);
	}
}
