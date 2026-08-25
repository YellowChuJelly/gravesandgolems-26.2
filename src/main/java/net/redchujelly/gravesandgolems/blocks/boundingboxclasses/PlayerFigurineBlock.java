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

public class PlayerFigurineBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE_NS = Shapes.or(Block.box(6,0,7,10,6,9), Block.box(4,6,7,12,12,9), Block.box(6,12,6,10,16,10));
    private static final VoxelShape SHAPE_EW = Shapes.or(Block.box(7,0,6,9,6,10), Block.box(7,6,4,9,12,12), Block.box(6,12,6,10,16,10));

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
}
