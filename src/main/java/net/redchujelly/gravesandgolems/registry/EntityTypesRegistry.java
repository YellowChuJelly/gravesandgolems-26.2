package net.redchujelly.gravesandgolems.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.entity.GolemEntity;

import java.util.function.Supplier;

public class EntityTypesRegistry {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(GravesAndGolems.MODID);

    public static final Supplier<EntityType<GolemEntity>> GOLEM = ENTITY_TYPES.register(
            "golem",
            () -> EntityType.Builder.of(GolemEntity::new, MobCategory.MISC)
                    .sized(2.0f, 4.0f)
                    .eyeHeight(3.8f)
                    .fireImmune()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "golem")))
    );



    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
