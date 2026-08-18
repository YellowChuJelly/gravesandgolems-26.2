package net.redchujelly.gravesandgolems.blocks.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.BrushableBlockRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;


public class CustomBrushableBlockRenderer implements BlockEntityRenderer<CustomBrushableBlockEntity, BrushableBlockRenderState> {
    private final ItemModelResolver itemModelResolver;

    public CustomBrushableBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public BrushableBlockRenderState createRenderState() {
        return new BrushableBlockRenderState();
    }

    @Override
    public void extractRenderState(CustomBrushableBlockEntity blockEntity, BrushableBlockRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderState.extractBase(blockEntity, state, breakProgress);
        state.hitDirection = blockEntity.getHitDirection();
        state.dustProgress = (Integer)blockEntity.getBlockState().getValue(BlockStateProperties.DUSTED);
        if (blockEntity.getLevel() != null && blockEntity.getHitDirection() != null) {
            state.lightCoords = LightCoordsUtil.getLightCoords(LightCoordsUtil.BrightnessGetter.DEFAULT, blockEntity.getLevel(), blockEntity.getBlockState(), blockEntity.getBlockPos().relative(blockEntity.getHitDirection()));
        }

        this.itemModelResolver.updateForTopItem(state.itemState, blockEntity.getItem(), ItemDisplayContext.FIXED, blockEntity.getLevel(), (ItemOwner)null, 0);
    }

    @Override
    public void submit(BrushableBlockRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.dustProgress > 0 && state.hitDirection != null && !state.itemState.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.5F, 0.0F);
            float[] translations = this.translations(state.hitDirection, state.dustProgress);
            poseStack.translate(translations[0], translations[1], translations[2]);
            poseStack.mulPose(Axis.YP.rotationDegrees(75.0F));
            boolean eastWest = state.hitDirection == Direction.EAST || state.hitDirection == Direction.WEST;
            poseStack.mulPose(Axis.YP.rotationDegrees((float)((eastWest ? 90 : 0) + 11)));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            state.itemState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

    }


    private float[] translations(Direction direction, int completionState) {
        float[] xyzTranslations = new float[]{0.5F, 0.0F, 0.5F};
        float completionOffset = (float)completionState / 10.0F * 0.75F;
        switch (direction) {
            case EAST -> xyzTranslations[0] = 0.73F + completionOffset;
            case WEST -> xyzTranslations[0] = 0.25F - completionOffset;
            case UP -> xyzTranslations[1] = 0.25F + completionOffset;
            case DOWN -> xyzTranslations[1] = -0.23F - completionOffset;
            case NORTH -> xyzTranslations[2] = 0.25F - completionOffset;
            case SOUTH -> xyzTranslations[2] = 0.73F + completionOffset;
        }

        return xyzTranslations;
    }

    @Override
    public AABB getRenderBoundingBox(CustomBrushableBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB((double)pos.getX() - (double)0.25F, (double)pos.getY() - (double)0.25F, (double)pos.getZ() - (double)0.25F, (double)pos.getX() + (double)1.25F, (double)pos.getY() + (double)1.25F, (double)pos.getZ() + (double)1.25F);
    }
}
