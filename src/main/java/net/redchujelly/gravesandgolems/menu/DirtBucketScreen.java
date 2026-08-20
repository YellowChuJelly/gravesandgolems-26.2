package net.redchujelly.gravesandgolems.menu;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.redchujelly.gravesandgolems.GravesAndGolems;

public class DirtBucketScreen extends AbstractContainerScreen<DirtBucketMenu> {
    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "textures/gui/dirt_bucket/dirt_bucket_gui.png");

    public DirtBucketScreen(DirtBucketMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }
}
