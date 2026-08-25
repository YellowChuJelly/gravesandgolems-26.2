package net.redchujelly.gravesandgolems.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class GaGRecipeProvider extends RecipeProvider {
    public GaGRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SCREENING_TABLE)
                .pattern("psp")
                .pattern("psp")
                .pattern("p p")
                .define('p', ItemTags.PLANKS)
                .define('s', Tags.Items.STRINGS)
                .unlockedBy("has_string", this.has(Tags.Items.STRINGS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.COPPER_SCREENING_TABLE)
                .pattern("psp")
                .pattern("psp")
                .pattern("p p")
                .define('p', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('s', ItemRegistry.SEA_SILK_FABRIC)
                .unlockedBy("has_sea_silk", this.has(ItemRegistry.SEA_SILK.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ItemRegistry.SEA_SILK_FABRIC)
                .pattern("sss")
                .define('s', ItemRegistry.SEA_SILK)
                .unlockedBy("has_sea_silk", this.has(ItemRegistry.SEA_SILK.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ItemRegistry.RED_VELVET_FABRIC, 8)
                .pattern("sss")
                .define('s', Blocks.WOOL.red())
                .unlockedBy("has_red_wool", this.has(Blocks.WOOL.red()))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ItemRegistry.RED_VELVET_FABRIC, 4)
                .requires(BlockRegistry.RED_VELVET_BLOCK)
                .unlockedBy("has_red_wool", this.has(Blocks.WOOL.red()))
                .save(this.output, "red_velvet_fabric_from_block");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ItemRegistry.SEA_SILK_FABRIC, 4)
                .requires(BlockRegistry.SEA_SILK_FABRIC_BLOCK)
                .unlockedBy("has_red_wool", this.has(Blocks.WOOL.red()))
                .save(this.output, "sea_silk_fabric_from_block");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ItemRegistry.SEA_SILK, 4)
                .requires(BlockRegistry.SEA_SILK_BLOCK)
                .unlockedBy("has_red_wool", this.has(Blocks.WOOL.red()))
                .save(this.output, "sea_silk_from_block");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SEA_SILK_CURTAIN, 8)
                .pattern("s")
                .pattern("s")
                .define('s', BlockRegistry.SEA_SILK_FABRIC_BLOCK)
                .unlockedBy("has_sea_silk_fabric", this.has(ItemRegistry.SEA_SILK_FABRIC.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RED_VELVET_CURTAIN, 8)
                .pattern("s")
                .pattern("s")
                .define('s', BlockRegistry.RED_VELVET_BLOCK)
                .unlockedBy("has_red_velvet_fabric", this.has(ItemRegistry.RED_VELVET_FABRIC.get()))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.GILDED_RED_VELVET_CURTAIN)
                .requires(BlockRegistry.RED_VELVET_CURTAIN)
                .requires(Items.GOLD_NUGGET)
                .unlockedBy("has_red_velvet_fabric", this.has(ItemRegistry.RED_VELVET_FABRIC.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BONE_PILE, 2)
                .pattern("ss")
                .pattern("ss")
                .define('s', Items.BONE)
                .unlockedBy("has_bone", this.has(Items.BONE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CATACOMB_WALL, 4)
                .pattern("ss")
                .pattern("ss")
                .define('s', BlockRegistry.BONE_PILE)
                .unlockedBy("has_bone_pile", this.has(BlockRegistry.BONE_PILE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_BOWL)
                .pattern("s s")
                .pattern(" s ")
                .define('s', ItemRegistry.DELFTWARE_SHERD)
                .unlockedBy("has_delftware_sherd", this.has(ItemRegistry.DELFTWARE_SHERD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_VASE)
                .pattern(" s ")
                .pattern(" s ")
                .pattern("s s")
                .define('s', ItemRegistry.DELFTWARE_SHERD)
                .unlockedBy("has_delftware_sherd", this.has(ItemRegistry.DELFTWARE_SHERD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_POT)
                .pattern("s s")
                .pattern("sss")
                .define('s', ItemRegistry.DELFTWARE_SHERD)
                .unlockedBy("has_delftware_sherd", this.has(ItemRegistry.DELFTWARE_SHERD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_POT_TALL)
                .pattern("s s")
                .pattern("s s")
                .pattern("sss")
                .define('s', ItemRegistry.DELFTWARE_SHERD)
                .unlockedBy("has_delftware_sherd", this.has(ItemRegistry.DELFTWARE_SHERD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_TEAPOT)
                .pattern(" s ")
                .pattern("s s")
                .pattern(" ss")
                .define('s', ItemRegistry.DELFTWARE_SHERD)
                .unlockedBy("has_delftware_sherd", this.has(ItemRegistry.DELFTWARE_SHERD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_TILE, 4)
                .pattern("ss")
                .pattern("ss")
                .define('s', BlockRegistry.DELFTWARE_TILE)
                .unlockedBy("has_delftware_sherd", this.has(ItemRegistry.DELFTWARE_SHERD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, BlockRegistry.DELFTWARE_FLOORING, 4)
                .pattern("ss")
                .pattern("ss")
                .define('s', BlockRegistry.DELFTWARE_TILE)
                .unlockedBy("has_delftware_sherd", this.has(BlockRegistry.DELFTWARE_TILE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, BlockRegistry.DIRT_BUCKET)
                .pattern("sis")
                .pattern("sbs")
                .pattern("sss")
                .define('s', Blocks.CONCRETE.blue())
                .define('i', Tags.Items.INGOTS_IRON)
                .define('b', Tags.Items.BUCKETS)
                .unlockedBy("has_delftware_sherd", this.has(BlockRegistry.DELFTWARE_TILE))
                .save(this.output);

        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SEA_SILK_FABRIC_BLOCK, ItemRegistry.SEA_SILK_FABRIC);
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SEA_SILK_BLOCK, ItemRegistry.SEA_SILK);
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RED_VELVET_BLOCK, ItemRegistry.RED_VELVET_FABRIC);

    }

    public static class Runner extends RecipeProvider.Runner{
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider){
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output){
            return new GaGRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "gravesandgolems";
        }
    }
}
