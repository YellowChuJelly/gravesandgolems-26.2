package net.redchujelly.gravesandgolems.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;

public class SkullSmokeQuadParticle extends SingleQuadParticle {

    public static SingleQuadParticle.Layer SKULL_SMOKE_LAYER = new Layer(
            false, TextureAtlas.LOCATION_PARTICLES, RenderPipelines.OPAQUE_PARTICLE
    );

    private final SpriteSet spriteSet;

    public SkullSmokeQuadParticle(ClientLevel level, double x, double y, double z, SpriteSet sprite) {
        super(level, x, y, z, sprite.first());
        this.spriteSet = sprite;
        this.setColor(0.1f, 0.13f, 0.1f);
        this.scale(1.8f);
        this.gravity = -0.15f;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    protected Layer getLayer() {
        return SKULL_SMOKE_LAYER;
    }
}
