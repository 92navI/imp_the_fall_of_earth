package com.navi92.imp_the_fall_of_earth.main;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TheFallOfEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue BLASTER_CHARGE_VELOCITY = BUILDER
            .comment("The velocity of a blaster charge")
            .defineInRange("charge_velocity", 1.2D, .1D, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue BLASTER_DAMAGE = BUILDER
            .comment("The damage of a blaster charge")
            .defineInRange("blaster_damage", 19.0D, .5D, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue PISTOL_DAMAGE = BUILDER
            .comment("The damage of a pistol charge")
            .defineInRange("pistol_damage", 11.0D, .5D, Double.MAX_VALUE);

    private static final ForgeConfigSpec.LongValue STORM_COOLDOWN = BUILDER
            .comment("The time in minutes it takes for the deadly Storm to boot up if not using -strict")
            .defineInRange("storm_cooldown", 5, 1, Long.MAX_VALUE);

    private static final ForgeConfigSpec.BooleanValue DO_STORM_WARNING_MESSAGES = BUILDER
            .comment("Whether the players should be notified if the storm is turned on with cooldown")
            .define("do_storm_warning_message", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static float blaster_charge_velocity;

    public static float pistol_damage;

    public static float blaster_damage;

    public static long storm_cooldown;

    public static boolean do_storm_warning_messages;

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        blaster_charge_velocity = BLASTER_CHARGE_VELOCITY.get().floatValue();
        blaster_damage = BLASTER_DAMAGE.get().floatValue();

        pistol_damage = PISTOL_DAMAGE.get().floatValue();

        storm_cooldown = STORM_COOLDOWN.get() * 60;

        do_storm_warning_messages = DO_STORM_WARNING_MESSAGES.get();
    }
}
