package net.redchujelly.gravesandgolems.datagen;

import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.blocks.CurtainBlock;
import net.redchujelly.gravesandgolems.blocks.ScreeningTableBlock;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class GaGModelProvider extends ModelProvider {
    public GaGModelProvider(PackOutput output) {
        super(output, GravesAndGolems.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        //ITEMS
        itemModels.generateFlatItem(ItemRegistry.SEA_SILK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SEA_SILK_FABRIC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.RED_VELVET_FABRIC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DELFTWARE_SHERD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.declareCustomModelItem(ItemRegistry.TROWEL.get());


        //BLOCKS
        MultiVariant unlitBones = new MultiVariant(WeightedList.of(
                        new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.BONE_PILE.get(), "", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                        new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.BONE_PILE.get(), "_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
                ));
        MultiVariant litBones = new MultiVariant(WeightedList.of(
                        new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.BONE_PILE.get(), "_lit_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                        new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.BONE_PILE.get(), "_lit_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
                ));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.BONE_PILE.get())
                .with(PropertyDispatch.initial(BlockStateProperties.LIT)
                        .select(false, unlitBones)
                        .select(true, litBones)
                )
        );

        MultiVariant litCatacombs = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_lit_0", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 10),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_lit_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 1),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_lit_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 1),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_lit_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 1)));
        MultiVariant unlitCatacombs = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 10),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 1),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 1),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.CATACOMB_WALL.get(), "_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 1)));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.CATACOMB_WALL.get())
                .with(PropertyDispatch.initial(BlockStateProperties.LIT, BlockStateProperties.HORIZONTAL_FACING)
                        .select(false, Direction.NORTH, unlitCatacombs)
                        .select(false, Direction.SOUTH, unlitCatacombs.with(Y_ROT_180))
                        .select(false, Direction.EAST, unlitCatacombs.with(Y_ROT_90))
                        .select(false, Direction.WEST, unlitCatacombs.with(Y_ROT_270))
                        .select(true, Direction.NORTH, litCatacombs)
                        .select(true, Direction.SOUTH, litCatacombs.with(Y_ROT_180))
                        .select(true, Direction.EAST, litCatacombs.with(Y_ROT_90))
                        .select(true, Direction.WEST, litCatacombs.with(Y_ROT_270))
                )
        );


        MultiVariant curtainsBot = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.SEA_SILK_CURTAIN.get(), "_bottom", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("sea_silk_curtain_base")).put(TextureSlot.BOTTOM, simpleMaterial("sea_silk_curtain_bottom")).put(TextureSlot.TOP, simpleMaterial("sea_silk_curtain_toptop"))));
        MultiVariant curtainsMid = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.SEA_SILK_CURTAIN.get(), "_middle", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("sea_silk_curtain")).put(TextureSlot.BOTTOM, simpleMaterial("sea_silk_curtain_bottom")).put(TextureSlot.TOP, simpleMaterial("sea_silk_curtain_toptop"))));
        MultiVariant curtainsTop = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.SEA_SILK_CURTAIN.get(), "_top", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("sea_silk_curtain_top")).put(TextureSlot.TOP, simpleMaterial("sea_silk_curtain_toptop")).put(TextureSlot.BOTTOM, simpleMaterial("sea_silk_curtain_bottom"))));
        MultiVariant curtainsSgl = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.SEA_SILK_CURTAIN.get(), "", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("sea_silk_curtain_single")).put(TextureSlot.TOP, simpleMaterial("sea_silk_curtain_toptop")).put(TextureSlot.BOTTOM, simpleMaterial("sea_silk_curtain_bottom"))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.SEA_SILK_CURTAIN.get())
                .with(PropertyDispatch.initial(CurtainBlock.CURTAIN_PART, BlockStateProperties.HORIZONTAL_FACING)
                        .select(CurtainBlock.CurtainPart.TOP, Direction.NORTH, curtainsTop)
                        .select(CurtainBlock.CurtainPart.TOP, Direction.SOUTH, curtainsTop.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.TOP, Direction.EAST, curtainsTop.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.TOP, Direction.WEST, curtainsTop.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.NORTH, curtainsBot)
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.SOUTH, curtainsBot.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.EAST, curtainsBot.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.WEST, curtainsBot.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.NORTH, curtainsMid)
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.SOUTH, curtainsMid.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.EAST, curtainsMid.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.WEST, curtainsMid.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.NORTH, curtainsSgl)
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.SOUTH, curtainsSgl.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.EAST, curtainsSgl.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.WEST, curtainsSgl.with(Y_ROT_270))
                )
        );


        MultiVariant susBones = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_0", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_0", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant susBones1 = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant susBones2 = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant susBones3 = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant litsusBones = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_lit_0", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_lit_0", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant litsusBones1 = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_lit_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_lit_1", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant litsusBones2 = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_lit_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_lit_2", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        MultiVariant litsusBones3 = new MultiVariant(WeightedList.of(
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_1_lit_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 2),
                new Weighted<>((BlockModelGenerators.plainModel(blockModels.createSuffixedVariant(BlockRegistry.SUSPICIOUS_BONE_PILE.get(), "_2_lit_3", ModelTemplates.CUBE_ALL, TextureMapping::cube))), 5)
        ));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.SUSPICIOUS_BONE_PILE.get())
                .with(PropertyDispatch.initial(BlockStateProperties.LIT, BlockStateProperties.DUSTED)
                        .select(false, 0, susBones)
                        .select(true, 0, litsusBones)
                        .select(false, 1, susBones1)
                        .select(true, 1, litsusBones1)
                        .select(false, 2, susBones2)
                        .select(true, 2, litsusBones2)
                        .select(false, 3, susBones3)
                        .select(true, 3, litsusBones3)
                )
        );

        curtainsBot = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.RED_VELVET_CURTAIN.get(), "_bottom", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain_base")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_bottom")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop"))));
        curtainsMid = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.RED_VELVET_CURTAIN.get(), "_middle", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_bottom")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop"))));
        curtainsTop = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.RED_VELVET_CURTAIN.get(), "_top", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain_top")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_bottom"))));
        curtainsSgl = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.RED_VELVET_CURTAIN.get(), "", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain_single")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_bottom"))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.RED_VELVET_CURTAIN.get())
                .with(PropertyDispatch.initial(CurtainBlock.CURTAIN_PART, BlockStateProperties.HORIZONTAL_FACING)
                        .select(CurtainBlock.CurtainPart.TOP, Direction.NORTH, curtainsTop)
                        .select(CurtainBlock.CurtainPart.TOP, Direction.SOUTH, curtainsTop.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.TOP, Direction.EAST, curtainsTop.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.TOP, Direction.WEST, curtainsTop.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.NORTH, curtainsBot)
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.SOUTH, curtainsBot.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.EAST, curtainsBot.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.WEST, curtainsBot.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.NORTH, curtainsMid)
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.SOUTH, curtainsMid.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.EAST, curtainsMid.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.WEST, curtainsMid.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.NORTH, curtainsSgl)
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.SOUTH, curtainsSgl.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.EAST, curtainsSgl.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.WEST, curtainsSgl.with(Y_ROT_270))
                )
        );

        curtainsBot = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get(), "_bottom", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain_gold_base")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_gold_bottom")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop"))));
        curtainsMid = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get(), "_middle", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_gold_bottom")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop"))));
        curtainsTop = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get(), "_top", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain_gold_top")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_gold_bottom"))));
        curtainsSgl = plainVariant(blockModels.createSuffixedVariant(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get(), "", ModelTemplates.CUBE_BOTTOM_TOP, m -> new TextureMapping().put(TextureSlot.SIDE, simpleMaterial("red_velvet_curtain_gold_single")).put(TextureSlot.TOP, simpleMaterial("red_velvet_curtain_toptop")).put(TextureSlot.BOTTOM, simpleMaterial("red_velvet_curtain_gold_bottom"))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.GILDED_RED_VELVET_CURTAIN.get())
                .with(PropertyDispatch.initial(CurtainBlock.CURTAIN_PART, BlockStateProperties.HORIZONTAL_FACING)
                        .select(CurtainBlock.CurtainPart.TOP, Direction.NORTH, curtainsTop)
                        .select(CurtainBlock.CurtainPart.TOP, Direction.SOUTH, curtainsTop.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.TOP, Direction.EAST, curtainsTop.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.TOP, Direction.WEST, curtainsTop.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.NORTH, curtainsBot)
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.SOUTH, curtainsBot.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.EAST, curtainsBot.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.BOTTOM, Direction.WEST, curtainsBot.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.NORTH, curtainsMid)
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.SOUTH, curtainsMid.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.EAST, curtainsMid.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.MIDDLE, Direction.WEST, curtainsMid.with(Y_ROT_270))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.NORTH, curtainsSgl)
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.SOUTH, curtainsSgl.with(Y_ROT_180))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.EAST, curtainsSgl.with(Y_ROT_90))
                        .select(CurtainBlock.CurtainPart.SINGLE, Direction.WEST, curtainsSgl.with(Y_ROT_270))
                )
        );

        blockModels.createTrivialCube(BlockRegistry.GRAVE_DIRT.get());
        blockModels.createBrushableBlock(BlockRegistry.SUSPICIOUS_GRAVE_DIRT.get());
        blockModels.createHorizontallyRotatedBlock(BlockRegistry.SEA_SILK_BLOCK.get(), TexturedModel.CUBE);
        blockModels.createTrivialCube(BlockRegistry.SEA_SILK_FABRIC_BLOCK.get());
        blockModels.createTrivialCube(BlockRegistry.RED_VELVET_BLOCK.get());

        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.ALEX_FIGURINE.get(), "figurine_alex");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.STEVE_FIGURINE.get(), "figurine_steve");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.BLACK_CAT_FIGURINE.get(), "figurine_black_cat");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.DIRT_BUCKET.get(), "dirt_bucket");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.DELFTWARE_BOWL.get(), "delftware_bowl");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.DELFTWARE_POT.get(), "delftware_pot");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.DELFTWARE_POT_TALL.get(), "delftware_pot_tall");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.DELFTWARE_TEAPOT.get(), "delftware_teapot");
        registerCustomModelDirectionalBlock(blockModels, itemModels, BlockRegistry.DELFTWARE_VASE.get(), "delftware_vase");

        blockModels.blockStateOutput.accept(
                MultiPartGenerator.multiPart(BlockRegistry.SCREENING_TABLE.get())
                        .with(condition().term(ScreeningTableBlock.SIFTING, true), plainVariant(Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "block/sifting_particles")))
                        .with(condition().term(ScreeningTableBlock.SIFTING, true), plainVariant(Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "block/screening_table")))
                        .with(condition().term(ScreeningTableBlock.SIFTING, false), plainVariant(Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "block/screening_table"))));

        MultiVariant floorFireModels = blockModels.createFloorFireModels(BlockRegistry.CURSED_FIRE.get());
        MultiVariant sideFireModels = blockModels.createSideFireModels(BlockRegistry.CURSED_FIRE.get());
        blockModels.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(BlockRegistry.CURSED_FIRE.get())
                                .with(floorFireModels)
                                .with(sideFireModels)
                                .with(sideFireModels.with(Y_ROT_90))
                                .with(sideFireModels.with(Y_ROT_180))
                                .with(sideFireModels.with(Y_ROT_270))
                );
    }
    
    private Material simpleMaterial(String texture){
        return new Material(this.modLocation("block/" + texture));
    }

    private void registerCustomModelDirectionalBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block, String name){
        Identifier modelLoc = modLocation("block/" + name);
        MultiVariant model = plainVariant(modelLoc);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING));
        itemModels.generateFlatItem(block.asItem(), ModelTemplates.FLAT_ITEM);
    }
    private void registerCustomModelBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block, String name){
        Identifier modelLoc = modLocation("block/" + name);
        MultiVariant model = plainVariant(modelLoc);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model));
        itemModels.declareCustomModelItem(block.asItem());
    }
}
