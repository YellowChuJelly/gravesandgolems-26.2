package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.CustomHorizontalBlock;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;

public class VaseBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE = Shapes.or(Block.box(3,0,3,13,6,13), Block.box(6,6,6,10,16,10));

    public VaseBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
