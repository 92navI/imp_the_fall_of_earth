package com.navi92.imp_the_fall_of_earth.event;

import com.navi92.imp_the_fall_of_earth.entity.ModEntities;
import com.navi92.imp_the_fall_of_earth.entity.renderer.BlasterChargeRenderer;
import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = TheFallOfEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.BLASTER_CHARGE.get(), BlasterChargeRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = TheFallOfEarth.MOD_ID, value = Dist.CLIENT)
    public static class ForgeClientEvents {

    }
}

