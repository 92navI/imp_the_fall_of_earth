package com.navi92.imp_the_fall_of_earth.data.damage_sources.types;

import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {
    ResourceKey<DamageType> FREEZE_IN_STORM = ResourceKey.create(Registries.DAMAGE_TYPE,
            new ResourceLocation(TheFallOfEarth.MOD_ID, "freeze_in_storm")
    );
}