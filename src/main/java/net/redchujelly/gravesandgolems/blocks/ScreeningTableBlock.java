package net.redchujelly.gravesandgolems.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.redchujelly.gravesandgolems.blocks.entity.ScreeningTableBlockEntity;
import net.redchujelly.gravesandgolems.registry.BlockEntityRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;
import org.jspecify.annotations.Nullable;

public class ScreeningTableBlock extends BaseEntityBlock {
    public static final MapCodec<ScreeningTableBlock> CODEC = simpleCodec(ScreeningTableBlock::new);
    public static final BooleanProperty SIFTING = BooleanProperty.create("sifting");
    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 13, 0, 16, 16, 16),
            Block.box(0, 0, 0, 3, 16, 3),
            Block.box(13, 0, 0, 16, 16, 3),
            Block.box(0, 0, 13, 3, 16, 16),
            Block.box(13, 0, 13, 16, 16, 16)
    );

    public ScreeningTableBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(SIFTING, false));
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScreeningTableBlockEntity(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ScreeningTableBlockEntity screenBE){
            screenBE.drops();
        }
        return super.onDestroyedByPlayer(state, level, pos, player, toolStack, willHarvest, fluid);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.is(ItemRegistry.SIFTABLES)){
            if (!level.isClientSide()){
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof ScreeningTableBlockEntity screenBE){
                    screenBE.playerUseItemStack(player, itemStack, hand);
                }

            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        if (level.isClientSide()){
            return null;
        }

        return createTickerHelper(type, BlockEntityRegistry.SCREENING_TABLE_BE.get(), (level1, pos, state, entity) -> entity.tick(level1, pos, state));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SIFTING);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ScreeningTableBlockEntity screenBE){
                screenBE.playerUseNoItem(player);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
