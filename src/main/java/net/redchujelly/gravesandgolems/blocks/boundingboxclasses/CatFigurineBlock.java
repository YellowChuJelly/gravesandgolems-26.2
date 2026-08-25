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

public class CatFigurineBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE_NS = Block.box(6.5,0,3,9.5,12,13);
    private static final VoxelShape SHAPE_EW = Block.box(3,0,6.5,13,12,9.5);

    public CatFigurineBlock(Properties pProperties) {
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
