package net.redchujelly.gravesandgolems.blocks;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.redchujelly.gravesandgolems.entity.GolemEntity;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.EntityTypesRegistry;
import org.jspecify.annotations.Nullable;

import static net.minecraft.world.level.block.CarvedPumpkinBlock.clearPatternBlocks;
import static net.minecraft.world.level.block.CarvedPumpkinBlock.updatePatternBlocks;

public class GolemSpawnBlock extends Block {
    private @Nullable BlockPattern golemBase;

    public GolemSpawnBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(state.getBlock())) {
            this.trySpawnGolem(level, pos);
        }
    }

    private BlockPattern getOrCreateGolemBase() {
        if (this.golemBase == null) {
            this.golemBase = BlockPatternBuilder.start().aisle(new String[]{" ", "+", "#"}).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.GRAVE_DIRT.get()))).where('+', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.BONE_PILE.get()))).where('~', BlockInWorld.hasState(BlockBehaviour.BlockStateBase::isAir)).build();
        }

        return this.golemBase;
    }

    private void trySpawnGolem(Level level, BlockPos topPos) {
        BlockPattern.BlockPatternMatch golemMatch = this.getOrCreateGolemBase().find(level, topPos);
        if (golemMatch != null) {
            GolemEntity golem = EntityTypesRegistry.GOLEM.get().create(level, EntitySpawnReason.TRIGGERED);
            if (golem != null) {
                spawnGolemInWorld(level, golemMatch, golem, golemMatch.getBlock(0, 2, 0).getPos());
                return;
            }
        }
    }


    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch match, Entity golem, BlockPos spawnPos) {
        clearPatternBlocks(level, match);
        golem.snapTo(spawnPos.getX() + 0.5, spawnPos.getY() + 0.05, spawnPos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(golem);

        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, golem);
        }

        updatePatternBlocks(level, match);
    }
}
