package net.redchujelly.gravesandgolems;

import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.redchujelly.gravesandgolems.entity.GolemEntity;
import net.redchujelly.gravesandgolems.registry.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.redchujelly.gravesandgolems.registry.EntityTypesRegistry.GOLEM;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(GravesAndGolems.MODID)
public class GravesAndGolems {
    public static final String MODID = "gravesandgolems";
    public static final Logger LOGGER = LogUtils.getLogger();


    public GravesAndGolems(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BlockRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);
        EntityTypesRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        ParticleTypesRegistry.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::createDefaultAttributes);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    public void createDefaultAttributes(EntityAttributeCreationEvent event){
        event.put(GOLEM.get(), GolemEntity.createAttributes().build());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

}
