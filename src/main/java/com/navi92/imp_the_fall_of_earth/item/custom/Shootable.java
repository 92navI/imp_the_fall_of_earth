package com.navi92.imp_the_fall_of_earth.item.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Shootable {

     void fire(ItemStack pStack, Level pLevel, Player player);
}
