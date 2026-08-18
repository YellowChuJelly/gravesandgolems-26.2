package net.redchujelly.gravesandgolems.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class CurtainBlock extends CustomHorizontalBlock{
    public static EnumProperty<CurtainPart> CURTAIN_PART = EnumProperty.create("curtain_part", CurtainPart.class);

    public CurtainBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        CurtainPart part = getPart(level, pos);
        return this.defaultBlockState().setValue(CURTAIN_PART, part).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (directionToNeighbour.equals(Direction.UP) || directionToNeighbour.equals(Direction.DOWN)){
            return state.setValue(CURTAIN_PART, getPart(level, pos));
        }
        return state;
    }

    private CurtainPart getPart(LevelReader level, BlockPos pos){
        boolean sameAbove = level.getBlockState(pos.above()).getBlock().equals(this);
        boolean sameBelow = level.getBlockState(pos.below()).getBlock().equals(this);
        if (sameAbove && sameBelow){
            return CurtainPart.MIDDLE;
        } else if (sameAbove) {
            return CurtainPart.BOTTOM;
        } else if (sameBelow){
            return CurtainPart.TOP;
        } else return CurtainPart.SINGLE;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(CURTAIN_PART);
    }

    public enum CurtainPart implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom"),
        SINGLE("single");

        private final String name;

        CurtainPart(String pName) {
            this.name = pName;
        }

        @Override
        public String toString() {
            return this.getSerializedName();
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
