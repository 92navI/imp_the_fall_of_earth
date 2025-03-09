package com.navi92.imp_the_fall_of_earth.util;

import com.navi92.imp_the_fall_of_earth.main.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

public class MessagingService {
    public static void sendToggledOnMessage(MinecraftServer server) {
        if (Config.do_storm_warning_messages)
            server.getLevel(Level.OVERWORLD).players().forEach(p -> p.sendSystemMessage(
                    Component.translatable("message.imp_the_fall_of_earth.toggled_storm_on")));
    }

    public static void trySendWarningMessage(MinecraftServer server) {
        server.getLevel(Level.OVERWORLD).players().forEach(p -> p.sendSystemMessage(
                Component.translatable("message.imp_the_fall_of_earth.storm_warning")));
    }

    public static void sendToggledOffMessage(MinecraftServer server) {
        server.getLevel(Level.OVERWORLD).players().forEach(p -> p.sendSystemMessage(
                Component.translatable("message.imp_the_fall_of_earth.toggled_storm_off")));
    }
}
