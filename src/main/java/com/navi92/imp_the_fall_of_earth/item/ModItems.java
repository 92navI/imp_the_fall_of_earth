package com.navi92.imp_the_fall_of_earth.item;

import com.navi92.imp_the_fall_of_earth.item.custom.Blaster;
import com.navi92.imp_the_fall_of_earth.item.custom.BlasterZoomed;
import com.navi92.imp_the_fall_of_earth.item.custom.Pistol;
import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TheFallOfEarth.MOD_ID);

    public static final RegistryObject<Item> CRYSTAL_BLUE = ITEMS.register(
            "crystal_blue",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CRYSTAL_GREEN = ITEMS.register(
            "crystal_green",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CRYSTAL_RED = ITEMS.register(
            "crystal_red",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DURASTEEL_INGOT = ITEMS.register(
            "durasteel_ingot",
            () -> new Item(new Item.Properties().stacksTo(64)));


    public static final RegistryObject<Item> BLASTER_EMPTY = ITEMS.register(
            "blaster_empty",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLASTER_BLUE = ITEMS.register(
            "blaster_blue",
            () -> new Blaster(new Item.Properties().stacksTo(1).durability(100), 1));
    public static final RegistryObject<Item> BLASTER_ZOOMED_BLUE = ITEMS.register(
            "blaster_zoomed_blue",
            () -> new BlasterZoomed(new Item.Properties().stacksTo(1).durability(100), 1));
    public static final RegistryObject<Item> BLASTER_GREEN = ITEMS.register(
            "blaster_green",
            () -> new Blaster(new Item.Properties().stacksTo(1).durability(100), 2));
    public static final RegistryObject<Item> BLASTER_ZOOMED_GREEN = ITEMS.register(
            "blaster_zoomed_green",
            () -> new BlasterZoomed(new Item.Properties().stacksTo(1).durability(100), 2));
    public static final RegistryObject<Item> BLASTER_RED = ITEMS.register(
            "blaster_red",
            () -> new Blaster(new Item.Properties().stacksTo(1).durability(100), 3));

    public static final RegistryObject<Item> BLASTER_ZOOMED_RED = ITEMS.register(
            "blaster_zoomed_red",
            () -> new BlasterZoomed(new Item.Properties().stacksTo(1).durability(100), 3));


    public static final RegistryObject<Item> PISTOL_EMPTY = ITEMS.register(
            "pistol_empty",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PISTOL_BLUE = ITEMS.register(
            "pistol_blue",
            () -> new Pistol(new Item.Properties().stacksTo(1).durability(100), 1));
    public static final RegistryObject<Item> PISTOL_GREEN = ITEMS.register(
            "pistol_green",
            () -> new Pistol(new Item.Properties().stacksTo(1).durability(100), 2));
    public static final RegistryObject<Item> PISTOL_RED = ITEMS.register(
            "pistol_red",
            () -> new Pistol(new Item.Properties().stacksTo(1).durability(100), 3));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
