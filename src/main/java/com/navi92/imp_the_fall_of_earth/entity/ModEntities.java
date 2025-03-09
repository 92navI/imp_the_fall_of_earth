package com.navi92.imp_the_fall_of_earth.entity;

import com.navi92.imp_the_fall_of_earth.entity.custom.BlasterChargeEntity;
import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheFallOfEarth.MOD_ID);

    public static final RegistryObject<EntityType<BlasterChargeEntity>> BLASTER_CHARGE = ENTITY_TYPES.register("blaster_charge",
            () -> EntityType.Builder.<BlasterChargeEntity>of(BlasterChargeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("blaster_charge"));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
