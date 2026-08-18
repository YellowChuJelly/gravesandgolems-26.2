package net.redchujelly.gravesandgolems;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.redchujelly.gravesandgolems.blocks.entity.CustomBrushableBlockRenderer;
import net.redchujelly.gravesandgolems.client.particle.GaGParticleProvider;
import net.redchujelly.gravesandgolems.entity.GolemEntityModel;
import net.redchujelly.gravesandgolems.entity.GolemEntityRenderer;
import net.redchujelly.gravesandgolems.registry.BlockEntityRegistry;
import net.redchujelly.gravesandgolems.registry.EntityTypesRegistry;
import net.redchujelly.gravesandgolems.registry.ParticleTypesRegistry;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = GravesAndGolems.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = GravesAndGolems.MODID, value = Dist.CLIENT)
public class GravesAndGolemsClient {
    public GravesAndGolemsClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        GravesAndGolems.LOGGER.info("HELLO FROM CLIENT SETUP");
        GravesAndGolems.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Add our layer here.
        event.registerLayerDefinition(GolemEntityModel.LAYER_LOCATION, GolemEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ParticleTypesRegistry.SKULL_SMOKE.get(), GaGParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(
                EntityTypesRegistry.GOLEM.get(), GolemEntityRenderer::new
        );
        event.registerBlockEntityRenderer(
                BlockEntityRegistry.CUSTOM_BRUSHABLE_BE.get(), CustomBrushableBlockRenderer::new
        );

    }
}
