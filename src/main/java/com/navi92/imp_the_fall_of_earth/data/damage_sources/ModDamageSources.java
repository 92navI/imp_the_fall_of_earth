package com.navi92.imp_the_fall_of_earth.data.damage_sources;

import com.navi92.imp_the_fall_of_earth.data.damage_sources.types.ModDamageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageSources {
    private final Registry<DamageType> damageTypes;

    private final DamageSource freezeInStorm;

    public ModDamageSources(RegistryAccess pRegistry) {
        this.damageTypes = pRegistry.registryOrThrow(Registries.DAMAGE_TYPE);
        this.freezeInStorm = this.source(ModDamageTypes.FREEZE_IN_STORM);
    }

    private DamageSource source(ResourceKey<DamageType> pDamageTypeKey) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(pDamageTypeKey));
    }

    public DamageSource getFreezeInStorm() {
        return freezeInStorm;
    }
}