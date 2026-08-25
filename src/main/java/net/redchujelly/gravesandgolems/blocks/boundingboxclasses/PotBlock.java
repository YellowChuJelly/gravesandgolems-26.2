package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;

public class PotBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE = Shapes.or(Block.box(3,0,3,13,9,13), Block.box(4,9,4,12,12,12));

    public PotBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
