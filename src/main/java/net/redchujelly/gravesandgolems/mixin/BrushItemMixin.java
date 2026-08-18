package net.redchujelly.gravesandgolems.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.redchujelly.gravesandgolems.blocks.entity.CustomBrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrushItem.class)
public class BrushItemMixin {
    @Definition(id = "BrushableBlockEntity", type = BrushableBlockEntity.class)
    @Expression("? instanceof BrushableBlockEntity")
    @WrapOperation(method = "onUseTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean gravesAndGolems_customBlockEntityCheck(Object object, Operation<Boolean> original, @Local(name = "level")LocalRef<Level> level, @Local(name = "player")LocalRef<Player> player, @Local(name = "blockHitResult")LocalRef<BlockHitResult> blockhitResult, @Local(argsOnly = true)LocalRef<ItemStack> itemStack) {
        if (object instanceof BlockEntity blockEntity && blockEntity instanceof CustomBrushableBlockEntity brushableBlockEntity){
            if (level.get() instanceof ServerLevel serverLevel) {
                boolean brushableUpdatedState = brushableBlockEntity.brush(
                        level.get().getGameTime(), serverLevel, player.get(), blockhitResult.get().getDirection(), itemStack.get()
                        );
                if (brushableUpdatedState) {
                    EquipmentSlot equippedHand = itemStack.get().equals(player.get().getItemBySlot(EquipmentSlot.OFFHAND))
                            ? EquipmentSlot.OFFHAND
                            : EquipmentSlot.MAINHAND;
                    itemStack.get().hurtAndBreak(1, player.get(), equippedHand);
                }
            }
        }
        return original.call(object);
    }
}
