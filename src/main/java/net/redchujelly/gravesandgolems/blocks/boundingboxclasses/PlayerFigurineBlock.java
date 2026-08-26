package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;
import net.redchujelly.gravesandgolems.entity.GolemEntity;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.EntityTypesRegistry;
import org.jspecify.annotations.Nullable;

import static net.minecraft.world.level.block.CarvedPumpkinBlock.clearPatternBlocks;
import static net.minecraft.world.level.block.CarvedPumpkinBlock.updatePatternBlocks;

public class PlayerFigurineBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE_NS = Shapes.or(Block.box(6,0,7,10,6,9), Block.box(4,6,7,12,12,9), Block.box(6,12,6,10,16,10));
    private static final VoxelShape SHAPE_EW = Shapes.or(Block.box(7,0,6,9,6,10), Block.box(7,6,4,9,12,12), Block.box(6,12,6,10,16,10));

    private @Nullable BlockPattern golemBase;


    public PlayerFigurineBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(FACING).getAxis().equals(Direction.Axis.Z)) {
            return SHAPE_NS;
        }
        else return SHAPE_EW;
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
