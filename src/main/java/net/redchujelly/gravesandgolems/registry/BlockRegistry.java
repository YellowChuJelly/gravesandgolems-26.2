package net.redchujelly.gravesandgolems.registry;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.blocks.*;
import net.redchujelly.gravesandgolems.items.DirtBucketBlockItem;

import java.util.function.Function;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GravesAndGolems.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(GravesAndGolems.MODID);



    public static final DeferredBlock<Block> BONE_PILE = registerBlock("bone_pile",
            properties -> new BonePileBlock(new ColorRGBA(0), properties.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.DEEPSLATE_BRICKS).lightLevel(p -> p.getValue(BlockStateProperties.LIT) ? 5 : 0)));
    public static final DeferredBlock<Block> SUSPICIOUS_BONE_PILE = registerBlock("suspicious_bone_pile",
            properties -> new BrushableBonePileBlock(BlockRegistry.BONE_PILE.get(), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, properties.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).strength(1F).sound(SoundType.SUSPICIOUS_GRAVEL).sound(SoundType.DEEPSLATE_BRICKS).lightLevel(p -> p.getValue(BlockStateProperties.LIT) ? 5 : 0)));
    public static final DeferredBlock<Block> CATACOMB_WALL = registerBlock("catacomb_wall",
            properties -> new CatacombWallBlock(properties.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredBlock<Block> GRAVE_DIRT = registerBlock("grave_dirt",
            properties -> new Block(properties.mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> SUSPICIOUS_GRAVE_DIRT = registerBlock("suspicious_grave_dirt",
            properties -> new CustomBrushableBlock(BlockRegistry.GRAVE_DIRT.get(), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.25F).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SEA_SILK_BLOCK = registerBlock("sea_silk_fiber_block",
            properties -> new CustomHorizontalBlock(properties.mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava()));
    public static final DeferredBlock<Block> SEA_SILK_FABRIC_BLOCK = registerBlock("sea_silk_block",
            properties -> new Block(properties.mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava()));
    public static final DeferredBlock<Block> RED_VELVET_BLOCK = registerBlock("red_velvet_block",
            properties -> new Block(properties.mapColor(DyeColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava()));
    public static final DeferredBlock<Block> SEA_SILK_CURTAIN = registerBlock("sea_silk_curtain",
            properties -> new CurtainBlock(properties.mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava()));
    public static final DeferredBlock<Block> RED_VELVET_CURTAIN = registerBlock("red_velvet_curtain",
            properties -> new CurtainBlock(properties.mapColor(DyeColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava()));
    public static final DeferredBlock<Block> GILDED_RED_VELVET_CURTAIN = registerBlock("red_velvet_curtain_gold",
            properties -> new CurtainBlock(properties.mapColor(DyeColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava()));
    public static final DeferredBlock<Block> CURSED_FIRE = registerBlockOnly("cursed_fire",
            properties -> new CursedFireBlock(properties.strength(0f).noCollision().noOcclusion().lightLevel(p -> 15)));


    public static final DeferredBlock<Block> STEVE_FIGURINE = registerBlock("figurine_steve",
            properties -> new SmallHorizontalBlock(properties.strength(1.5f).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> ALEX_FIGURINE = registerBlock("figurine_alex",
            properties -> new SmallHorizontalBlock(properties.strength(1.5f).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> BLACK_CAT_FIGURINE = registerBlock("figurine_black_cat",
            properties -> new SmallHorizontalBlock(properties.strength(1.5f).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> DIRT_BUCKET = registerDirtBucketBlock("dirt_bucket",
            properties -> new DirtBucketBlock(properties.strength(1.5f).sound(SoundType.BAMBOO).noOcclusion()));


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        BLOCK_ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }
    private static <T extends Block> void registerDirtBlockItem(String name, DeferredBlock<T> block){
        BLOCK_ITEMS.registerItem(name, properties -> new DirtBucketBlockItem(block.get(), properties.useBlockDescriptionPrefix().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(1)));
    }

    private static <T extends Block> void registerBlockItemStacksTo(String name, DeferredBlock<T> block, int stacksTo){
        BLOCK_ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix().stacksTo(stacksTo)));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function){
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerDirtBucketBlock(String name, Function<BlockBehaviour.Properties, T> function){
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerDirtBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockStacksTo(String name, Function<BlockBehaviour.Properties, T> function, int stacksTo){
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItemStacksTo(name, toReturn, stacksTo);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockOnly(String name, Function<BlockBehaviour.Properties, T> function){
        return BLOCKS.registerBlock(name, function);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }
}
