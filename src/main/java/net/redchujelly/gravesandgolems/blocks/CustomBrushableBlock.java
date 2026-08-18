package net.redchujelly.gravesandgolems.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.redchujelly.gravesandgolems.blocks.entity.CustomBrushableBlockEntity;
import org.jspecify.annotations.Nullable;

public class CustomBrushableBlock extends BaseEntityBlock {
    public static final MapCodec<CustomBrushableBlock> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("turns_into").forGetter(CustomBrushableBlock::getTurnsInto), BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("brush_sound").forGetter(CustomBrushableBlock::getBrushSound), BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("brush_completed_sound").forGetter(CustomBrushableBlock::getBrushCompletedSound), propertiesCodec()).apply(i, CustomBrushableBlock::new));
    private static final IntegerProperty DUSTED;
    public static final int TICK_DELAY = 2;
    private final Block turnsInto;
    private final SoundEvent brushSound;
    private final SoundEvent brushCompletedSound;

    public MapCodec<CustomBrushableBlock> codec() {
        return CODEC;
    }

    public CustomBrushableBlock(Block turnsInto, SoundEvent brushSound, SoundEvent brushCompletedSound, BlockBehaviour.Properties properties) {
        super(properties);
        this.turnsInto = turnsInto;
        this.brushSound = brushSound;
        this.brushCompletedSound = brushCompletedSound;
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(DUSTED, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{DUSTED});
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, 2);
    }

    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        ticks.scheduleTick(pos, this, 2);
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity var6 = level.getBlockEntity(pos);
        if (var6 instanceof CustomBrushableBlockEntity brushableBlockEntity) {
            brushableBlockEntity.checkReset(level);
        }

//        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
//            FallingBlockEntity entity = FallingBlockEntity.fall(level, pos, state);
//            entity.disableDrop();
//        }

    }
//
//    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity) {
//        Vec3 centerOfEntity = entity.getBoundingBox().getCenter();
//        level.levelEvent(2001, BlockPos.containing(centerOfEntity), Block.getId(entity.getBlockState()));
//        level.gameEvent(entity, GameEvent.BLOCK_DESTROY, centerOfEntity);
//    }

//    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
//        if (random.nextInt(16) == 0) {
//            BlockPos below = pos.below();
//            if (FallingBlock.isFree(level.getBlockState(below))) {
//                double xx = (double)pos.getX() + random.nextDouble();
//                double yy = (double)pos.getY() - 0.05;
//                double zz = (double)pos.getZ() + random.nextDouble();
//                level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
//            }
//        }
//
//    }

    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new CustomBrushableBlockEntity(worldPosition, blockState);
    }

    public Block getTurnsInto() {
        return this.turnsInto;
    }

    public SoundEvent getBrushSound() {
        return this.brushSound;
    }

    public SoundEvent getBrushCompletedSound() {
        return this.brushCompletedSound;
    }

    static {
        DUSTED = BlockStateProperties.DUSTED;
    }
}
