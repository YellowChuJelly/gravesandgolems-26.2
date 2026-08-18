package net.redchujelly.gravesandgolems.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.blocks.entity.CustomBrushableBlockEntity;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GravesAndGolems.MODID);

    public static final Supplier<BlockEntityType<CustomBrushableBlockEntity>> CUSTOM_BRUSHABLE_BE = BLOCK_ENTITIES.register(
            "custom_brushable_be", () -> new BlockEntityType<>(CustomBrushableBlockEntity::new, BlockRegistry.SUSPICIOUS_GRAVE_DIRT.get(), BlockRegistry.SUSPICIOUS_BONE_PILE.get()));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
