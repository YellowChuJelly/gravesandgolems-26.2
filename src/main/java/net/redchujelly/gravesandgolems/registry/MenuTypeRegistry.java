package net.redchujelly.gravesandgolems.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.menu.DirtBucketMenu;

import java.util.function.Supplier;

public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, GravesAndGolems.MODID);
//
//    public static final DeferredHolder<MenuType<?>, MenuType<DirtBucketMenu>> DIRT_BUCKET_MENU =
//            registerMenuType("pedestal_menu", DirtBucketMenu::new);

    public static final Supplier<MenuType<DirtBucketMenu>> DIRT_BUCKET_MENU = MENU_TYPES.register("my_menu_extra", () -> IMenuTypeExtension.create(DirtBucketMenu::new));


    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
