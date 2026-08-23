package net.redchujelly.gravesandgolems.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;

import java.util.Optional;

public class WeatheringScreeningTableBlock extends ScreeningTableBlock {

    public static BooleanProperty WAXED = BooleanProperty.create("waxed");
    public static EnumProperty<WeatheringStage> WEATHERING = EnumProperty.create("weathering_stage", WeatheringStage.class);
    public static final MapCodec<WeatheringScreeningTableBlock> CODEC = simpleCodec(WeatheringScreeningTableBlock::new);


    public WeatheringScreeningTableBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(SIFTING, false).setValue(WEATHERING, WeatheringStage.UNAFFECTED).setValue(WAXED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        //could be a lot better sorry
        if (itemStack.getItem() instanceof HoneycombItem && !state.getValue(WAXED)) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(WAXED, true), 3);
                if (itemStack.getItem().equals(Items.HONEYCOMB)){
                    itemStack.shrink(1);
                }
            } else {
                level.levelEvent(3003, pos, 0);
            }
            return InteractionResult.SUCCESS;

        } else if (itemStack.getItem() instanceof AxeItem && state.getValue(WAXED)) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(WAXED, false), 3);
                if (itemStack.isDamageableItem()){
                    itemStack.hurtAndBreak(1, player, hand);
                }
            } else {
                level.levelEvent(3004, pos, 0);
            }
            return InteractionResult.SUCCESS;

        } else if (itemStack.getItem() instanceof AxeItem && !state.getValue(WAXED)) {
            if (!state.getValue(WEATHERING).equals(WeatheringStage.UNAFFECTED)) {
                if (!level.isClientSide()) {
                    WeatheringStage toStage = regressStage(state.getValue(WEATHERING));
                    if (toStage!= null){
                        level.setBlock(pos, state.setValue(WEATHERING, toStage), 3);
                        level.playSound(null, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS);
                    }
                    if (itemStack.isDamageableItem()){
                        itemStack.hurtAndBreak(1, player, hand);
                    }
                } else {
                    level.levelEvent(3005, pos, 0);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(WEATHERING).equals(WeatheringStage.OXIDIZED) && !state.getValue(WAXED);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        float chance = random.nextFloat();
        if (chance < 0.009f){
            WeatheringStage toStage = advanceStage(state.getValue(WEATHERING));
            if (toStage != null){
                level.setBlock(pos, state.setValue(WEATHERING, toStage), 3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WEATHERING).add(WAXED);
    }



    private static WeatheringStage advanceStage(WeatheringStage stage){
        if (stage.equals(WeatheringStage.UNAFFECTED)){
            return WeatheringStage.EXPOSED;
        }
        if (stage.equals(WeatheringStage.EXPOSED)){
            return WeatheringStage.WEATHERED;
        }
        if (stage.equals(WeatheringStage.WEATHERED)){
            return WeatheringStage.OXIDIZED;
        } else return null;
    }

    private static WeatheringStage regressStage(WeatheringStage stage){
        if (stage.equals(WeatheringStage.UNAFFECTED)){
            return null;
        }
        if (stage.equals(WeatheringStage.EXPOSED)){
            return WeatheringStage.UNAFFECTED;
        }
        if (stage.equals(WeatheringStage.WEATHERED)){
            return WeatheringStage.EXPOSED;
        } else return WeatheringStage.WEATHERED;
    }

    public enum WeatheringStage implements StringRepresentable {
        UNAFFECTED("unaffected"),
        EXPOSED("exposed"),
        WEATHERED("weathered"),
        OXIDIZED("oxidized");

        private final String name;

        WeatheringStage(String pName) {
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
