package net.redchujelly.gravesandgolems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.redchujelly.gravesandgolems.datagen.GaGModelProvider;
import net.redchujelly.gravesandgolems.datagen.GaGTagsProvider;
import net.redchujelly.gravesandgolems.datagen.loot.GaGBlockLootSubProvider;
import net.redchujelly.gravesandgolems.datagen.loot.GaGLootTableSubProvider;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = GravesAndGolems.MODID)
public class GravesAndGolemsDatagen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(true, new GaGModelProvider(output));
        event.createProvider(GaGTagsProvider::new);

        event.createProvider((output1, lookupProvider) -> new LootTableProvider(
                output, Set.of(), List.of(
                        new LootTableProvider.SubProviderEntry(GaGLootTableSubProvider::new, LootContextParamSets.ARCHAEOLOGY),
                        new LootTableProvider.SubProviderEntry(GaGBlockLootSubProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider
        ));
    }
}
