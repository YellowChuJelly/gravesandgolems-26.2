package net.redchujelly.gravesandgolems.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;

import java.util.concurrent.CompletableFuture;

public class GaGBlockTagsProvider extends BlockTagsProvider {
    public GaGBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GravesAndGolems.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.DIRT)
                .add(BlockRegistry.GRAVE_DIRT.getKey())
                .add(BlockRegistry.SUSPICIOUS_GRAVE_DIRT.getKey());
        this.tag(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT)
                .add(BlockRegistry.GRAVE_DIRT.getKey())
                .add(BlockRegistry.SUSPICIOUS_GRAVE_DIRT.getKey());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(BlockRegistry.BONE_PILE.getKey())
                .add(BlockRegistry.SUSPICIOUS_BONE_PILE.getKey())
                .add(BlockRegistry.GRAVE_DIRT.getKey())
                .add(BlockRegistry.SUSPICIOUS_GRAVE_DIRT.getKey());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.DIRT_BUCKET.getKey())
                .add(BlockRegistry.SOIL_BUCKET.getKey())
                .add(BlockRegistry.CATACOMB_WALL.getKey())
                .add(BlockRegistry.STEVE_FIGURINE.getKey())
                .add(BlockRegistry.COPPER_SCREENING_TABLE.getKey())
                .add(BlockRegistry.ALEX_FIGURINE.getKey())
                .add(BlockRegistry.BLACK_CAT_FIGURINE.getKey())
                .add(BlockRegistry.DELFTWARE_TILE.getKey())
                .add(BlockRegistry.DELFTWARE_FLOORING.getKey());
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlockRegistry.SCREENING_TABLE.getKey());
        this.tag(BlockTags.WOOL)
                .add(BlockRegistry.SEA_SILK_BLOCK.getKey())
                .add(BlockRegistry.SEA_SILK_FABRIC_BLOCK.getKey())
                .add(BlockRegistry.RED_VELVET_BLOCK.getKey())
                .add(BlockRegistry.GILDED_RED_VELVET_CURTAIN.getKey())
                .add(BlockRegistry.RED_VELVET_CURTAIN.getKey())
                .add(BlockRegistry.SEA_SILK_CURTAIN.getKey());

    }
}
