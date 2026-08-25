package net.redchujelly.gravesandgolems.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.ParticleTypesRegistry;

public class CursedFireBlock extends BaseFireBlock {
    public static final MapCodec<CursedFireBlock> CODEC = simpleCodec(CursedFireBlock::new);

    public CursedFireBlock(Properties properties) {
        super(properties.replaceable(), 3.0f);
    }

    public MapCodec<CursedFireBlock> codec() {
        return CODEC;
    }


    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        return this.canSurvive(state, level, pos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSurviveOnBlock(level.getBlockState(pos.below()));
    }

    public static boolean canSurviveOnBlock(BlockState state) {
        return state.is(BlockRegistry.CATACOMB_WALL.get()) || state.is(BlockRegistry.BONE_PILE.get()) || state.is(BlockRegistry.SUSPICIOUS_BONE_PILE.get());
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    private ParticleOptions randomSkull(float random){
        if (random < 0.05f){
            return ParticleTypesRegistry.SKULL_SMOKE.get();
        }
        else return ParticleTypes.LARGE_SMOKE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(24) == 0) {
            level.playLocalSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        float rand = random.nextFloat();
        if (!this.canBurn(belowState) && !belowState.isFaceSturdy(level, below, Direction.UP)) {
            if (this.canBurn(level.getBlockState(pos.west()))) {
                for(int i = 0; i < 2; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble() * (double)0.1F;
                    double yy = (double)pos.getY() + random.nextDouble();
                    double zz = (double)pos.getZ() + random.nextDouble();
                    if (rand < 0.15f) {
                        level.addParticle(randomSkull(rand), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    }
                }
            }

            if (this.canBurn(level.getBlockState(pos.east()))) {
                for(int i = 0; i < 2; ++i) {
                    double xx = (double)(pos.getX() + 1) - random.nextDouble() * (double)0.1F;
                    double yy = (double)pos.getY() + random.nextDouble();
                    double zz = (double)pos.getZ() + random.nextDouble();
                    if (rand < 0.15f) {
                        level.addParticle(randomSkull(rand), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    }
                }
            }

            if (this.canBurn(level.getBlockState(pos.north()))) {
                for(int i = 0; i < 2; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble();
                    double yy = (double)pos.getY() + random.nextDouble();
                    double zz = (double)pos.getZ() + random.nextDouble() * (double)0.1F;
                    if (rand < 0.15f) {
                        level.addParticle(randomSkull(rand), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    }
                }
            }

            if (this.canBurn(level.getBlockState(pos.south()))) {
                for(int i = 0; i < 2; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble();
                    double yy = (double)pos.getY() + random.nextDouble();
                    double zz = (double)(pos.getZ() + 1) - random.nextDouble() * (double)0.1F;
                    if (rand < 0.15f) {
                        level.addParticle(randomSkull(rand), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    }
                }
            }

            if (this.canBurn(level.getBlockState(pos.above()))) {
                for(int i = 0; i < 2; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble();
                    double yy = (double)(pos.getY() + 1) - random.nextDouble() * (double)0.1F;
                    double zz = (double)pos.getZ() + random.nextDouble();
                    if (rand < 0.15f) {
                        level.addParticle(randomSkull(rand), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    }
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double xx = (double)pos.getX() + random.nextDouble();
                double yy = (double)pos.getY() + random.nextDouble() * (double)0.5F + (double)0.5F;
                double zz = (double)pos.getZ() + random.nextDouble();
                if (rand < 0.15f) {
                    level.addParticle(randomSkull(rand), xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }
        }

    }
}
