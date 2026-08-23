package net.redchujelly.gravesandgolems.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.redchujelly.gravesandgolems.blocks.entity.ScreeningTableBlockEntity;

public class CapabilitiesRegistry {

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.Item.BLOCK, BlockEntityRegistry.DIRT_BUCKET_BE.get(), (o, direction) -> o.getItemHandler());
        event.registerBlockEntity(Capabilities.Item.BLOCK, BlockEntityRegistry.SCREENING_TABLE_BE.get(), (screeningTableBlockEntity, direction) -> screeningTableBlockEntity.getItemHandler(direction));
    }
}
