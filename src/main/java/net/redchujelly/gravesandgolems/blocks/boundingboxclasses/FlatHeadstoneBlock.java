package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;

public class FlatHeadstoneBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE_NS = Block.box(1,0,5,15,18,11);
    private static final VoxelShape SHAPE_EW = Block.box(5,0,1,11,18,15);

    public FlatHeadstoneBlock(Properties pProperties) {
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
