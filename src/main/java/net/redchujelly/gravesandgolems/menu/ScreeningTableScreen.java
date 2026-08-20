package net.redchujelly.gravesandgolems.menu;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.redchujelly.gravesandgolems.GravesAndGolems;

public class ScreeningTableScreen extends AbstractContainerScreen<ScreeningTableMenu> {
    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "textures/gui/screening_table/screening_table_gui.png");

    public ScreeningTableScreen(ScreeningTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
