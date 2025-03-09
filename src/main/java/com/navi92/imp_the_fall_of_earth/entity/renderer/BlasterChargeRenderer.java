package com.navi92.imp_the_fall_of_earth.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.navi92.imp_the_fall_of_earth.entity.custom.BlasterChargeEntity;
import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlasterChargeRenderer extends ArrowRenderer<BlasterChargeEntity> {
    public static final ResourceLocation BLASTER_CHARGE_BLUE_LOCATION = new ResourceLocation(TheFallOfEarth.MOD_ID, "textures/entity/projectiles/blaster_charge_blue.png");
    public static final ResourceLocation BLASTER_CHARGE_RED_LOCATION = new ResourceLocation(TheFallOfEarth.MOD_ID, "textures/entity/projectiles/blaster_charge_red.png");
    public static final ResourceLocation BLASTER_CHARGE_GREEN_LOCATION = new ResourceLocation(TheFallOfEarth.MOD_ID, "textures/entity/projectiles/blaster_charge_green.png");

    public BlasterChargeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(BlasterChargeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BlasterChargeEntity pEntity) {
        return switch (pEntity.getChargeColor()) {
            case 0 -> throw new NullPointerException("No color assigned to blaster charge!");
            case 1 -> BLASTER_CHARGE_BLUE_LOCATION;
            case 2 -> BLASTER_CHARGE_GREEN_LOCATION;
            case 3 -> BLASTER_CHARGE_RED_LOCATION;
            default -> throw new IllegalStateException("Unexpected color value: " + pEntity.getChargeColor());
        };
    }
}
