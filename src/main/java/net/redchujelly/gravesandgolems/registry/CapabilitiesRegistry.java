package net.redchujelly.gravesandgolems.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CapabilitiesRegistry {

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.Item.BLOCK, BlockEntityRegistry.DIRT_BUCKET_BE.get(), (o, direction) -> o.getItemHandler());
    }
}
