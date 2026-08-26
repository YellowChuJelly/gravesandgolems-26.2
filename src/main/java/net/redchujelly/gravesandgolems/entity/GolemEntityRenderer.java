package net.redchujelly.gravesandgolems.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.redchujelly.gravesandgolems.GravesAndGolems;

public class GolemEntityRenderer extends MobRenderer<GolemEntity, LivingEntityRenderState, GolemEntityModel> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "textures/entity/golem.png");

    public GolemEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new GolemEntityModel(context.bakeLayer(GolemEntityModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState livingEntityRenderState) {
        return TEXTURE_LOCATION;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
