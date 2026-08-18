package net.redchujelly.gravesandgolems.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.redchujelly.gravesandgolems.blocks.CursedFireBlock;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {

    @WrapOperation(method = "getState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FireBlock;getStateForPlacement(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState gravesAndGolems_cursedFireState(FireBlock instance, BlockGetter direction, BlockPos result, Operation<BlockState> original, @Local(name = "belowState") LocalRef<BlockState> stateBelow){
        if (CursedFireBlock.canSurviveOnBlock(stateBelow.get())){
            return BlockRegistry.CURSED_FIRE.get().defaultBlockState();
        }
        return original.call(instance, direction, result);
    }
}
