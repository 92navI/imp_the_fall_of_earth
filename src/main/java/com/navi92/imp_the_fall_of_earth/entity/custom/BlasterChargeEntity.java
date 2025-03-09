package com.navi92.imp_the_fall_of_earth.entity.custom;

import com.navi92.imp_the_fall_of_earth.entity.ModEntities;
import com.navi92.imp_the_fall_of_earth.main.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BlasterChargeEntity extends Arrow {

    private static final EntityDataAccessor<Integer> CHARGE_COLOR_ACCESSOR = SynchedEntityData.defineId(BlasterChargeEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> CHARGE_TYPE_ACCESSOR = SynchedEntityData.defineId(BlasterChargeEntity.class, EntityDataSerializers.INT);

    public static final int MAX_LIFETIME = 80;

    public int lifetime = 0;

    public BlasterChargeEntity(EntityType<? extends Arrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        entityData.set(CHARGE_COLOR_ACCESSOR, 0);
        entityData.set(CHARGE_TYPE_ACCESSOR, 0);
    }

    public BlasterChargeEntity(Level pLevel, LivingEntity pShooter, int color, int type) {
        super(pLevel, pShooter);
        entityData.set(CHARGE_COLOR_ACCESSOR, color);
        entityData.set(CHARGE_TYPE_ACCESSOR, type);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        lifetime++;
        if (lifetime > MAX_LIFETIME) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        Entity owner = this.getOwner();
        if (entity != owner) {
            DamageSource damagesource;
            if (owner == null) {
                damagesource = this.damageSources().arrow(this, this);
            } else {
                damagesource = this.damageSources().arrow(this, owner);
                if (owner instanceof LivingEntity) {
                    ((LivingEntity) owner).setLastHurtMob(entity);
                }
            }

            boolean flag = entity.getType() == EntityType.ENDERMAN;

            if (entity.hurt(damagesource, entityData.get(CHARGE_TYPE_ACCESSOR) == 1 ? Config.blaster_damage : Config.pistol_damage)) {
                if (flag) {
                    return;
                }

                if (entity instanceof LivingEntity livingentity) {

                    if (livingentity instanceof Player && owner instanceof ServerPlayer && !this.isSilent()) {
                        ((ServerPlayer) owner).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                    }
                }

//            this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        this.discard();
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.BLASTER_CHARGE.get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    protected void defineSynchedData() {
        entityData.define(CHARGE_COLOR_ACCESSOR, 0);
        entityData.define(CHARGE_TYPE_ACCESSOR, 0);
        super.defineSynchedData();
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(CHARGE_COLOR_ACCESSOR, pCompound.getInt("charge_color"));
        this.entityData.set(CHARGE_TYPE_ACCESSOR, pCompound.getInt("charge_type"));
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("charge_color", this.entityData.get(CHARGE_COLOR_ACCESSOR));
        pCompound.putInt("charge_type", this.entityData.get(CHARGE_TYPE_ACCESSOR));
    }

    public int getChargeColor() {
        return entityData.get(CHARGE_COLOR_ACCESSOR);
    }
}
