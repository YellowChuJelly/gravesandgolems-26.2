package net.redchujelly.gravesandgolems.mixin;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrushableBlockEntity.class)
public interface BrushableBlockEntityInvoker {
    @Invoker("dropContent")
    void callDropContent(ServerLevel level, LivingEntity user, ItemStack brush);
}
