package net.redchujelly.gravesandgolems.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.redchujelly.gravesandgolems.blocks.CustomBrushableBlock;
import net.redchujelly.gravesandgolems.blocks.entity.CustomBrushableBlockEntity;
import net.redchujelly.gravesandgolems.mixin.BrushableBlockEntityInvoker;

public class TrowelItem extends ShovelItem {
    public TrowelItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
        super(material, attackDamageBaseline, attackSpeedBaseline, properties);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
        boolean toreturn = super.mineBlock(itemStack, level, state, pos, owner);
        if (level instanceof ServerLevel serverLevel) {
            if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushable){
                ((BrushableBlockEntityInvoker) brushable).callDropContent(serverLevel, owner, new ItemStack(Items.BRUSH));
                if (level.getBlockState(pos).getBlock() instanceof BrushableBlock block){
                    Block.popResource(serverLevel, pos, new ItemStack(block.getTurnsInto().asItem()));
                }
            }
            else if (level.getBlockEntity(pos) instanceof CustomBrushableBlockEntity brushable){
                brushable.dropContent(serverLevel, owner, new ItemStack(Items.BRUSH));
                if (level.getBlockState(pos).getBlock() instanceof CustomBrushableBlock block){
                    Block.popResource(serverLevel, pos, new ItemStack(block.getTurnsInto().asItem()));
                }
            }
        }
        return toreturn;
    }
}
