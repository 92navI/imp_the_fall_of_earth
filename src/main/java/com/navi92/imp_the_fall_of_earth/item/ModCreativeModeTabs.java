package com.navi92.imp_the_fall_of_earth.item;

import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheFallOfEarth.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CUTSCENES_TAB = CREATIVE_MODE_TABS.register(
            "spectral_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CRYSTAL_BLUE.get()))
                    .title(Component.translatable("creativetab." + TheFallOfEarth.MOD_ID + ".spectral_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {

                        output.accept(ModItems.CRYSTAL_BLUE.get());
                        output.accept(ModItems.CRYSTAL_GREEN.get());
                        output.accept(ModItems.CRYSTAL_RED.get());
                        output.accept(ModItems.DURASTEEL_INGOT.get());

                        output.accept(ModItems.BLASTER_EMPTY.get());

                        output.accept(ModItems.BLASTER_BLUE.get());
                        output.accept(ModItems.BLASTER_GREEN.get());
                        output.accept(ModItems.BLASTER_RED.get());

                        output.accept(ModItems.PISTOL_EMPTY.get());

                        output.accept(ModItems.PISTOL_BLUE.get());
                        output.accept(ModItems.PISTOL_GREEN.get());
                        output.accept(ModItems.PISTOL_RED.get());

                    })).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
