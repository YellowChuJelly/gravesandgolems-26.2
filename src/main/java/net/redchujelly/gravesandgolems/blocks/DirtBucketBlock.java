package net.redchujelly.gravesandgolems.blocks;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.blocks.entity.DirtBucketBlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DirtBucketBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<DirtBucketBlock> CODEC = simpleCodec(DirtBucketBlock::new);

    public DirtBucketBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level instanceof ServerLevel) {
            BlockEntity var8 = level.getBlockEntity(pos);
            if (var8 instanceof DirtBucketBlockEntity dirtBucketBlockEntity) {
                player.openMenu(new SimpleMenuProvider(dirtBucketBlockEntity, DirtBucketBlockEntity.DEFAULT_NAME));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DirtBucketBlockEntity dirtBucketBlock) {
            if (!level.isClientSide() && player.preventsBlockDrops()) {
                ItemStack itemStack = new ItemStack(state.getBlock());
                dirtBucketBlock.setInventoryForDrop();
                saveBlockEntityToItem(dirtBucketBlock, itemStack, level.registryAccess());
                itemStack.applyComponents(dirtBucketBlock.collectComponents());
                ItemEntity entity = new ItemEntity(level, (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, itemStack);
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    //Used these next two methods from https://github.com/TheTestMod/BetterCrates 26.2 port, with permission !
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity te = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (te instanceof DirtBucketBlockEntity) {
            ItemStack stack = new ItemStack(Item.byBlock(this));
            saveBlockEntityToItem(te, stack, builder.getLevel().registryAccess());
            List<ItemStack> result = new ArrayList<>();
            result.add(stack);
            return result;
        } else {
            return super.getDrops(state, builder);
        }
    }

    private static void saveBlockEntityToItem(BlockEntity te, ItemStack stack, HolderLookup.Provider provider) {
        try (ProblemReporter.ScopedCollector problemReporter = new ProblemReporter.ScopedCollector(te.problemPath(), GravesAndGolems.LOGGER)) {
            TagValueOutput tagvalueoutput = TagValueOutput.createWithContext(problemReporter, provider);
            te.saveCustomOnly(tagvalueoutput);
            te.removeComponentsFromTag(tagvalueoutput);
            BlockItem.setBlockEntityData(stack, te.getType(), tagvalueoutput);
            stack.applyComponents(te.collectComponents());
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DirtBucketBlockEntity(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}
