package net.redchujelly.gravesandgolems.blocks.boundingboxclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.SmallHorizontalBlock;

public class BowlBlock extends SmallHorizontalBlock {

    private static final VoxelShape SHAPE = Block.box(3,0,3,13,5,13);

    public BowlBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
