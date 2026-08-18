package net.redchujelly.gravesandgolems.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;

public class BonePileBlock extends ColoredFallingBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BonePileBlock(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties.lightLevel(p -> p.getValue(LIT) ? 5 : 0));
        registerDefaultState(stateDefinition.any().setValue(LIT, false));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        ticks.scheduleTick(pos, this, this.getDelayAfterPlace());
        if (directionToNeighbour.equals(Direction.UP)){
            if (neighbourState.is(BlockRegistry.CURSED_FIRE.get())){
                return state.setValue(LIT, true);
            }
            else return state.setValue(LIT, false);
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
            FallingBlockEntity entity = FallingBlockEntity.fall(level, pos, state.setValue(LIT, false));
            this.falling(entity);
        }

    }
}
