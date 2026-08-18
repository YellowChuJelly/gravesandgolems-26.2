package net.redchujelly.gravesandgolems.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import org.jspecify.annotations.Nullable;

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
            this.golemBase = BlockPatternBuilder.start().aisle(new String[]{"~ ~", "###", "~#~"}).aisle(new String[]{"~~~", "###", "~#~"}).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.GRAVE_DIRT.get()))).where('~', BlockInWorld.hasState(BlockBehaviour.BlockStateBase::isAir)).build();
        }

        return this.golemBase;
    }

    private void trySpawnGolem(Level level, BlockPos topPos) {
        BlockPattern.BlockPatternMatch golemMatch = this.getOrCreateGolemBase().find(level, topPos);
        if (golemMatch != null) {
            level.setBlock(topPos, Blocks.BONE_BLOCK.defaultBlockState(), 3);
        }
    }
}
