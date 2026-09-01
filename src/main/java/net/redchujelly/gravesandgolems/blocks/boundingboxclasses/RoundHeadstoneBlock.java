package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;

public class RoundHeadstoneBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE_NS = Shapes.or(Block.box(1,0,5,15,14,11), Block.box(3,14,5,13,19,11));
    private static final VoxelShape SHAPE_EW = Shapes.or(Block.box(5,0,1,11,14,15), Block.box(5,14,3,11,19,13));

    public RoundHeadstoneBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(FACING).getAxis().equals(Direction.Axis.Z)) {
            return SHAPE_NS;
        }
        else return SHAPE_EW;
    }
}
