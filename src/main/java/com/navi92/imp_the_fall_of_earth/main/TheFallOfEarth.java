package com.navi92.imp_the_fall_of_earth.main;

import com.mojang.logging.LogUtils;
import com.navi92.imp_the_fall_of_earth.data.damage_sources.ModDamageSources;
import com.navi92.imp_the_fall_of_earth.entity.ModEntities;
import com.navi92.imp_the_fall_of_earth.item.ModCreativeModeTabs;
import com.navi92.imp_the_fall_of_earth.item.ModItems;
import com.navi92.imp_the_fall_of_earth.network.PacketHandler;
import com.navi92.imp_the_fall_of_earth.sound.ModSounds;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheFallOfEarth.MOD_ID)
public class TheFallOfEarth {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "imp_the_fall_of_earth";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public TheFallOfEarth() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }
}
