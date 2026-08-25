package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.CustomHorizontalBlock;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;

public class TeapotBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE_NS = Shapes.or(Block.box(4,0,5,12,6,11), Block.box(5,6,6,11,7,10));
    private static final VoxelShape SHAPE_EW = Shapes.or(Block.box(5,0,4,11,6,12    ), Block.box(6,6,5,10,7,11));

    public TeapotBlock(Properties pProperties) {
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
