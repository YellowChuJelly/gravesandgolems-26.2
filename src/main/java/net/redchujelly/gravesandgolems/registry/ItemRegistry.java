package net.redchujelly.gravesandgolems.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.items.TrowelItem;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GravesAndGolems.MODID);

    public static final DeferredItem<Item> SEA_SILK = ITEMS.registerSimpleItem("sea_silk");
    public static final DeferredItem<Item> SEA_SILK_FABRIC = ITEMS.registerSimpleItem("sea_silk_fabric");
    public static final DeferredItem<Item> RED_VELVET_FABRIC = ITEMS.registerSimpleItem("red_velvet");
    public static final DeferredItem<Item> TROWEL = ITEMS.registerItem("trowel", p -> new TrowelItem(ToolMaterial.COPPER, 1.5F, -3.0F, p));
    public static final DeferredItem<Item> DELFTWARE_SHERD = ITEMS.registerSimpleItem("delftware_sherd");


    //TAGS
    public static final TagKey<Item> DIRT_BUCKET_PLACEABLES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_bucket_placeables"));
    public static final TagKey<Item> SIFTABLES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "siftables"));
    public static final TagKey<Item> DEAD_CORALS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dead_corals"));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
