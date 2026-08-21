package net.redchujelly.gravesandgolems.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class GaGTagsProvider extends ItemTagsProvider {

    public GaGTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GravesAndGolems.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemRegistry.DIRT_BUCKET_PLACEABLES)
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "suspicious_grave_dirt")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "suspicious_bone_pile")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "bone_pile")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "catacomb_wall")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "figurine_steve")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "figurine_alex")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "figurine_black_cat")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "trowel")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sea_silk")));

        this.tag(ItemRegistry.DIRT_BUCKET_PLACEABLES)
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("dirt")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("grass_block")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("coarse_dirt")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("rooted_dirt")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("podzol")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("mycelium")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("stone")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("cobblestone")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("sand")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("suspicious_sand")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("sandstone")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("red_sand")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("red_sandstone")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("mud")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("snow")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("soul_sand")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("soul_soil")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("packed_mud")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("clay")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("cobblestone")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("cobbled_deepslate")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("mossy_cobblestone")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("gravel")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("suspicious_gravel")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("brush")))
                .add(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("sniffer_egg")));

        this.tag(ItemRegistry.DIRT_BUCKET_PLACEABLES)
                .addTags(ItemTags.DECORATED_POT_SHERDS)
                .addTags(Tags.Items.MUSIC_DISCS);

    }
}
