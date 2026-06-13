package xyz.nasasupercomputer.birmingham.Inventories;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import xyz.nasasupercomputer.birmingham.MainRegistry;

// GUI for the Coking Oven BigBlock.
public class GUICokingOven extends AbstractContainerScreen {

	private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(MainRegistry.MOD_ID, "textures/gui/machines/gui_coking_oven.png");
	
	@SuppressWarnings("unchecked")
	public GUICokingOven(AbstractContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, Component.translatable("inventory.birmingham.coking_oven"));
		
		this.imageHeight = 74;
		this.imageWidth = 176;
	}

    @Override
    protected void init() {
        super.init();
//        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
//        this.titleLabelY = 6;
//        this.inventoryLabelX = 8;
//        this.inventoryLabelY = this.imageHeight - 96 + 2;
    }
    
	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
		// TODO Auto-generated method stub
	}

}
