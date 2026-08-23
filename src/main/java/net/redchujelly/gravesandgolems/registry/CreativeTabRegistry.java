package net.redchujelly.gravesandgolems.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GravesAndGolems.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("graves_and_golems_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.gravesandgolems"))
            .icon(() -> ItemRegistry.TROWEL.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.TROWEL.get());
                output.accept(BlockRegistry.DIRT_BUCKET.get());
                output.accept(BlockRegistry.SCREENING_TABLE.get());
                output.accept(BlockRegistry.COPPER_SCREENING_TABLE.get());
                output.accept(BlockRegistry.BONE_PILE.get());
                output.accept(BlockRegistry.CATACOMB_WALL.get());
                output.accept(BlockRegistry.GRAVE_DIRT.get());
                output.accept(BlockRegistry.SEA_SILK_BLOCK.get());
                output.accept(ItemRegistry.SEA_SILK.get());
                output.accept(BlockRegistry.SEA_SILK_FABRIC_BLOCK.get());
                output.accept(ItemRegistry.SEA_SILK_FABRIC.get());
                output.accept(BlockRegistry.SEA_SILK_CURTAIN.get());
                output.accept(ItemRegistry.RED_VELVET_FABRIC.get());
                output.accept(BlockRegistry.RED_VELVET_BLOCK.get());
                output.accept(BlockRegistry.RED_VELVET_CURTAIN.get());
                output.accept(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get());
                output.accept(BlockRegistry.ALEX_FIGURINE.get());
                output.accept(BlockRegistry.STEVE_FIGURINE.get());
                output.accept(BlockRegistry.BLACK_CAT_FIGURINE.get());
                output.accept(ItemRegistry.DELFTWARE_SHERD.get());
                output.accept(BlockRegistry.DELFTWARE_TILE.get());
                output.accept(BlockRegistry.DELFTWARE_FLOORING.get());
                output.accept(BlockRegistry.DELFTWARE_BOWL.get());
                output.accept(BlockRegistry.DELFTWARE_TEAPOT.get());
                output.accept(BlockRegistry.DELFTWARE_VASE.get());
                output.accept(BlockRegistry.DELFTWARE_POT.get());
                output.accept(BlockRegistry.DELFTWARE_POT_TALL.get());
//                output.accept(BlockRegistry.WAXED_COPPER_SCREENING_TABLE.get());
//                output.accept(BlockRegistry.EXPOSED_COPPER_SCREENING_TABLE.get());
//                output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_SCREENING_TABLE.get());
//                output.accept(BlockRegistry.WEATHERED_COPPER_SCREENING_TABLE.get());
//                output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_SCREENING_TABLE.get());
//                output.accept(BlockRegistry.OXIDIZED_COPPER_SCREENING_TABLE.get());
//                output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_SCREENING_TABLE.get());

            }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

