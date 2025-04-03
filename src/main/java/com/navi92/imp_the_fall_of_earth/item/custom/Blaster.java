package com.navi92.imp_the_fall_of_earth.item.custom;

import com.navi92.imp_the_fall_of_earth.entity.custom.BlasterChargeEntity;
import com.navi92.imp_the_fall_of_earth.item.ModItems;
import com.navi92.imp_the_fall_of_earth.main.Config;
import com.navi92.imp_the_fall_of_earth.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Blaster extends Item implements Shootable {

    public int color;

    public Blaster(Properties pProperties, int color) {
        super(pProperties);
        this.color = color;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            pPlayer.setItemInHand(pUsedHand, BlasterZoomed.getByColor(color, itemStack.getDamageValue(), getWait(itemStack), isShot(itemStack)));

            return InteractionResultHolder.fail(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    public void fire(ItemStack pStack, Level pLevel, Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() <= 95) {
            if (!pLevel.isClientSide) {

                AbstractArrow abstractarrow = new BlasterChargeEntity(pLevel, player, this.color, 1);
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
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        int damage = stack.getDamageValue() + 10;
        stack.setDamageValue(damage);
        setShot(stack, true);
        setWait(stack, 25);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (!level.isClientSide) {
            ItemStack itemStack = player.getInventory().getItem(slotIndex);

            if (isShot(stack)) {
                if (stack.getDamageValue() == 0) {
                    setShot(stack, false);
                    return;
                }

                int wait = getWait(stack);
                if (wait > 0) {
                    setWait(stack, wait - 1);
                } else {
                    int damage = stack.getDamageValue() - 1;
                    stack.setDamageValue(damage);
                }
            }
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    public static ItemStack getByColor(int color, int damage, int wait, boolean isShot) {
        return switch (color) {
            case 1 -> getInstanceWithArgs(ModItems.BLASTER_BLUE.get(), damage, wait, isShot);
            case 2 -> getInstanceWithArgs(ModItems.BLASTER_GREEN.get(), damage, wait, isShot);
            case 3 -> getInstanceWithArgs(ModItems.BLASTER_RED.get(), damage, wait, isShot);
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }

    public static ItemStack getInstanceWithArgs(Item item, int damage, int wait, boolean isShot) {
        Blaster blaster = (Blaster) item;
        ItemStack itemStack = item.getDefaultInstance();
        itemStack.setDamageValue(damage);
        setShot(itemStack, isShot);
        setWait(itemStack, wait);
        return itemStack;
    }

    protected static int getWait(ItemStack itemStack) {
        return itemStack.getTag().getInt("wait");
    }

    protected static void setWait(ItemStack itemStack, int value) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("wait", value);
        itemStack.setTag(tag);
    }

    protected static boolean isShot(ItemStack itemStack) {
        return itemStack.getTag().getBoolean("isShot");
    }

    protected static void setShot(ItemStack itemStack, boolean value) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putBoolean("isShot", value);
        itemStack.setTag(tag);
    }
}
