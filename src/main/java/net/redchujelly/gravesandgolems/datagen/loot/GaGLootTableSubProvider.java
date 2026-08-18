package net.redchujelly.gravesandgolems.datagen.loot;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;

import java.util.function.BiConsumer;

public class GaGLootTableSubProvider implements LootTableSubProvider {

    private final HolderGetter.Provider provider;

    public GaGLootTableSubProvider(HolderLookup.Provider provider){
        this.provider = provider;
    }


    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {

        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "suspicious_grave_dirt_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.SEA_SILK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5,20))).setWeight(7))
                                .add(LootItem.lootTableItem(BlockRegistry.STEVE_FIGURINE.get()).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.ALEX_FIGURINE.get()).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.BLACK_CAT_FIGURINE.get()).setQuality(3)))
        );
    }
}
