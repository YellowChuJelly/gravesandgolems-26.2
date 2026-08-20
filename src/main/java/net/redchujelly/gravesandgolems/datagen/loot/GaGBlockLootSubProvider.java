package net.redchujelly.gravesandgolems.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;

import java.util.Set;

public class GaGBlockLootSubProvider extends BlockLootSubProvider {
    public GaGBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        return BlockRegistry.BLOCKS.getEntries()
                .stream()
                // Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
                .map(e -> (Block) e.value())
                .toList();
    }

    protected LootTable.Builder createShulkerBoxDrop(Block shulkerBox) {
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                shulkerBox,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(shulkerBox)
                                                        .apply(
                                                                CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                                                        .include(DataComponents.CUSTOM_NAME)
                                                                        .include(DataComponents.CONTAINER)
                                                                        .include(DataComponents.LOCK)
                                                                        .include(DataComponents.CONTAINER_LOOT)
                                                        )
                                        )
                        )
                );
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockRegistry.BONE_PILE.get());
        this.dropSelf(BlockRegistry.CATACOMB_WALL.get());
        this.dropSelf(BlockRegistry.GRAVE_DIRT.get());
        this.dropSelf(BlockRegistry.SEA_SILK_BLOCK.get());
        this.dropSelf(BlockRegistry.SEA_SILK_FABRIC_BLOCK.get());
        this.dropSelf(BlockRegistry.RED_VELVET_BLOCK.get());
        this.dropSelf(BlockRegistry.SEA_SILK_CURTAIN.get());
        this.dropSelf(BlockRegistry.RED_VELVET_CURTAIN.get());
        this.dropSelf(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get());
        this.dropSelf(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get());
        this.dropSelf(BlockRegistry.STEVE_FIGURINE.get());
        this.dropSelf(BlockRegistry.ALEX_FIGURINE.get());
        this.dropSelf(BlockRegistry.BLACK_CAT_FIGURINE.get());
        this.add(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), noDrop());
        this.add(BlockRegistry.SUSPICIOUS_GRAVE_DIRT.get(), noDrop());
        this.add(BlockRegistry.CURSED_FIRE.get(), noDrop());
        this.add(BlockRegistry.DIRT_BUCKET.get(), createShulkerBoxDrop(BlockRegistry.DIRT_BUCKET.get()));
    }

}
