package com.navi92.imp_the_fall_of_earth.item.custom;

import com.navi92.imp_the_fall_of_earth.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

public class BlasterZoomed extends Blaster {
    public BlasterZoomed(Properties pProperties, int color) {
        super(pProperties, color);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            pPlayer.setItemInHand(pUsedHand, Blaster.getByColor(color, itemStack.getDamageValue(), getWait(itemStack), isShot(itemStack)));

            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pIsSelected && pEntity instanceof Player player) {
            MobEffects.MOVEMENT_SLOWDOWN.applyEffectTick(player, 2);
        }
    }

    public static ItemStack getByColor(int color, int damage, int wait, boolean isShot) {
        return switch (color) {
            case 1 -> getInstanceWithArgs(ModItems.BLASTER_ZOOMED_BLUE.get(), damage, wait, isShot);
            case 2 -> getInstanceWithArgs(ModItems.BLASTER_ZOOMED_GREEN.get(), damage, wait, isShot);
            case 3 -> getInstanceWithArgs(ModItems.BLASTER_ZOOMED_RED.get(), damage, wait, isShot);
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }

    public static ItemStack getInstanceWithArgs(Item item, int damage, int wait, boolean isShot) {
        BlasterZoomed blaster = (BlasterZoomed) item;
        ItemStack itemStack = item.getDefaultInstance();
        itemStack.setDamageValue(damage);
        setShot(itemStack, isShot);
        setWait(itemStack, wait);
        return itemStack;
    }
}
