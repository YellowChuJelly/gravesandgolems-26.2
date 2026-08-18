package net.redchujelly.gravesandgolems.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.redchujelly.gravesandgolems.blocks.entity.CustomBrushableBlockEntity;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;

public class BrushableBonePileBlock extends CustomBrushableBlock implements Fallable {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;


    public BrushableBonePileBlock(Block turnsInto, SoundEvent brushSound, SoundEvent brushCompletedSound, Properties properties) {
        super(turnsInto, brushSound, brushCompletedSound, properties);
        registerDefaultState(stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        ticks.scheduleTick(pos, this, 2);
        if (directionToNeighbour.equals(Direction.UP)){
            if (neighbourState.is(BlockRegistry.CURSED_FIRE.get())){
                return state.setValue(LIT, true);
            }
            else return state.setValue(LIT, false);
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity) {
        Vec3 centerOfEntity = entity.getBoundingBox().getCenter();
        level.levelEvent(2001, BlockPos.containing(centerOfEntity), Block.getId(entity.getBlockState()));
        level.gameEvent(entity, GameEvent.BLOCK_DESTROY, centerOfEntity);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos below = pos.below();
            if (FallingBlock.isFree(level.getBlockState(below))) {
                double xx = (double)pos.getX() + random.nextDouble();
                double yy = (double)pos.getY() - 0.05;
                double zz = (double)pos.getZ() + random.nextDouble();
                level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }

    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity var6 = level.getBlockEntity(pos);
        if (var6 instanceof CustomBrushableBlockEntity brushableBlockEntity) {
            brushableBlockEntity.checkReset(level);
        }

        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
            FallingBlockEntity entity = FallingBlockEntity.fall(level, pos, state);
            entity.disableDrop();
        }
    }

}
