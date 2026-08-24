package net.redchujelly.gravesandgolems.datagen.loot;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class GaGLootTableSubProvider implements LootTableSubProvider {

    private final HolderLookup.Provider provider;


    public GaGLootTableSubProvider(HolderLookup.Provider provider){
        this.provider = provider;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.provider.lookupOrThrow(Registries.ENCHANTMENT);

        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "suspicious_grave_dirt_loot_table_test")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(BlockRegistry.BONE_PILE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,8))).setWeight(2))
                                .add(LootItem.lootTableItem(ItemRegistry.SEA_SILK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5,20))).setWeight(7))
                                .add(LootItem.lootTableItem(ItemRegistry.SEA_SILK_FABRIC.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3,15))).setWeight(2))
                                .add(LootItem.lootTableItem(BlockRegistry.STEVE_FIGURINE.get()).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.ALEX_FIGURINE.get()).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.BLACK_CAT_FIGURINE.get()).setQuality(3))

                                .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setQuality(2))
                                .add(LootItem.lootTableItem(Items.IRON_NUGGET).setQuality(1).setWeight(2))
                                .add(LootItem.lootTableItem(Items.BONE).setQuality(1).setWeight(2))
                        )
        );

        //SIFTING TABLES
        //DIRT
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.HANGING_ROOTS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.FLINT).setWeight(2))
                                .add(LootItem.lootTableItem(Items.ANDESITE).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.DIORITE).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.GRANITE).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.BONE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.BOWL).setWeight(1))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.CANDLE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.CLAY).setWeight(2))
                                .add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(2))
                                .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(1))
                                .add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD))
                                .add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD))
                                .add(LootItem.lootTableItem(Items.SCRAPE_POTTERY_SHERD))
                                .add(LootItem.lootTableItem(Items.DANGER_POTTERY_SHERD))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_RELIC).setWeight(1))
                        )
        );

        //SAND
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(2))
                                .add(LootItem.lootTableItem(Items.DRY_SHORT_GRASS).setWeight(1))
                                .add(LootItem.lootTableItem(Items.DRY_TALL_GRASS).setWeight(1))
                                .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(2))
                                .add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(3))
                                .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.PRISMARINE).setWeight(1).setQuality(2))
                                .add(TagEntry.tagContents(ItemRegistry.DEAD_CORALS).setWeight(1))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(4))
                                .add(LootItem.lootTableItem(ItemRegistry.SEA_SILK.get()).setWeight(3).setWeight(1)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get()))
                                .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(2))
                                .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(2))
                                .add(LootItem.lootTableItem(Items.TURTLE_SCUTE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.BLACK_CAT_FIGURINE.get()).setWeight(1).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.SNIFFER_EGG).setWeight(1))
                        )
        );

        //MUD
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.MANGROVE_ROOTS).setWeight(2))
                                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(2))
                                .add(LootItem.lootTableItem(Items.BRICK).setWeight(4))
                                .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM).setWeight(2))
                                .add(LootItem.lootTableItem(Items.RED_MUSHROOM).setWeight(2))
                                .add(LootItem.lootTableItem(Items.COCOA_BEANS).setWeight(1))
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(3))
                                .add(LootItem.lootTableItem(Items.BONE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(2))
                                .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(3))
                                .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(1))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.FERMENTED_SPIDER_EYE).setWeight(3))
                                .add(LootItem.lootTableItem(Items.CANDLE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.SLIME_BALL).setWeight(5))
                                .add(LootItem.lootTableItem(Items.CAULDRON).setWeight(1))
                                .add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.HOWL_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.MOURNER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.SHELTER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.STEVE_FIGURINE.get()).setWeight(1).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.ZOMBIE_HEAD).setWeight(1))
                        )
        );

        //CLAY
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(4))
                                .add(LootItem.lootTableItem(Items.RAIL).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.PALE_HANGING_MOSS).setWeight(2))
                                .add(LootItem.lootTableItem(Items.PALE_MOSS_BLOCK).setWeight(2))
                                .add(LootItem.lootTableItem(Items.POINTED_DRIPSTONE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(1))
                                .add(LootItem.lootTableItem(Items.GLOW_INK_SAC).setWeight(1))
                                .add(LootItem.lootTableItem(Items.CALCITE).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(1).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.AMETHYST_CLUSTER).setWeight(4))
                                .add(LootItem.lootTableItem(Items.SMALL_AMETHYST_BUD).setWeight(5))
                                .add(LootItem.lootTableItem(Items.SPORE_BLOSSOM).setWeight(4))
                                .add(LootItem.lootTableItem(Items.GUSTER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.FLOW_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.ALEX_FIGURINE.get()).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.PITCHER_POD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.TORCHFLOWER_SEEDS).setWeight(1))
                        )
        );

        //GRAVEL
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.SULFUR_SPIKE).setWeight(3))
                                .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.COAL).setWeight(2))
                                .add(LootItem.lootTableItem(Items.TUFF).setWeight(3))
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(1).setQuality(-1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.provider)))
                                .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.CINNABAR).setWeight(2).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.SEA_SILK.get()).setWeight(4).setWeight(1)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get()))
                                .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.TURTLE_EGG).setWeight(3))
                                .add(LootItem.lootTableItem(Items.NAUTILUS_SHELL).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.MINER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.SNORT_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                         )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA).setWeight(1))
                        )
        );

        //SOULSAND
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.COAL).setWeight(3))
                                .add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(3))
                                .add(LootItem.lootTableItem(Items.IRON_CHAIN).setWeight(2))
                                .add(LootItem.lootTableItem(Items.BONE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(3))
                                .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST).setWeight(2).setQuality(2))
                                .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(1).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.QUARTZ).setWeight(6))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4))
                                .add(LootItem.lootTableItem(Items.BURN_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.HEARTBREAK_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.WITHER_SKELETON_SKULL).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.DRIED_GHAST).setWeight(1).setQuality(2).setWeight(1)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get()))
                         )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(2))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(1))
                        )
        );

        //GRAVE_DIRT
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(75).setQuality(-1).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table"))).setWeight(90).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_junk_table"))).setWeight(100).setQuality(-1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_goods_table"))).setWeight(10))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_goods_table"))).setWeight(15).setWeight(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_rare_table"))).setWeight(1).setQuality(1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.COPPER_SCREENING_TABLE.get())))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_junk_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.BONE).setWeight(3))
                                .add(LootItem.lootTableItem(ItemRegistry.SEA_SILK.get()).setWeight(4).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.BONE_PILE.get()).setWeight(2))
                                .add(LootItem.lootTableItem(Items.BOOK).setWeight(1))
                                .add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(1))
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.CANDLE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1).setQuality(2))
                        )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_goods_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(2))
                                .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(3))
                                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(2))
                                .add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.ALEX_FIGURINE.get()).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.STEVE_FIGURINE.get()).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(BlockRegistry.BLACK_CAT_FIGURINE.get()).setWeight(1).setQuality(2))
                                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).setQuality(3))
                         )
        );
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_rare_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.BELL).setWeight(1))
                        )
        );


        //EMPTY TABLE
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "empty_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool())
        );


        HolderSet.Named<Enchantment> weaponEnchants = enchantments.getOrThrow(EnchantmentTags.ON_RANDOM_LOOT);
        HolderSet.Named<Enchantment> armorEnchants = enchantments.getOrThrow(EnchantmentTags.ON_RANDOM_LOOT );


        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "iron_item_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.IRON_SPEAR).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                ));
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gold_item_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder()))
                                .add(LootItem.lootTableItem(Items.GOLDEN_SPEAR).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder()))
                                .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder()))
                                .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder()))
                                .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder()))
                                ));
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "stone_item_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.STONE_SWORD).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.STONE_SPEAR).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                ));
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "diamond_item_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_SPEAR).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(weaponEnchants)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(1).setQuality(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))).apply(new EnchantRandomlyFunction.Builder().withOptions(armorEnchants)))
                                ));

        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "suspicious_bone_pile_loot_table")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "stone_item_loot_table"))).setWeight(5).setQuality(-2))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "iron_item_loot_table"))).setWeight(8))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gold_item_loot_table"))).setWeight(4))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "diamond_item_loot_table"))).setWeight(2).setQuality(2))
                        )
        );
    }
}
