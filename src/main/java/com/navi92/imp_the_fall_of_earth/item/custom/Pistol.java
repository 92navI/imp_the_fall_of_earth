package com.navi92.imp_the_fall_of_earth.item.custom;

import com.navi92.imp_the_fall_of_earth.entity.custom.BlasterChargeEntity;
import com.navi92.imp_the_fall_of_earth.item.ModItems;
import com.navi92.imp_the_fall_of_earth.main.Config;
import com.navi92.imp_the_fall_of_earth.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Pistol extends Item implements Shootable {

    public int color;

    public int wait;
    public boolean isShot;
    public Pistol(Properties pProperties, int color) {
        super(pProperties);
        this.color = color;
    }

    public void fire(ItemStack pStack, Level pLevel, Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() <= 95) {
            if (!pLevel.isClientSide) {

                AbstractArrow abstractarrow = new BlasterChargeEntity(pLevel, player, this.color, 2);
                abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, Config.blaster_charge_velocity * 3.0F, 1.0F);

                pLevel.addFreshEntity(abstractarrow);
            }

            updateItemDamage(player);

            pLevel.playSound(null,
                    player.getX(), player.getY(), player.getZ(),
                    ModSounds.BLASTER_SHOOT_SOUND.get(), SoundSource.PLAYERS, .8F,
                    1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    private void updateItemDamage(Player player) {

        int damage = player.getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() + 20;
        player.getItemInHand(InteractionHand.MAIN_HAND).setDamageValue(damage);
        isShot = true;
        wait = 50;

    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (isShot) {
            if (pStack.getDamageValue() == 0) {
                isShot = false;
                return;
            }

            if (wait > 0) {
                wait--;
            } else {
                int damage = pStack.getDamageValue() - 1;
                pStack.setDamageValue(damage);
            }
        }
    }


    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }
}
