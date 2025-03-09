package com.navi92.imp_the_fall_of_earth.event;

import com.navi92.imp_the_fall_of_earth.command.StormControlCommand;
import com.navi92.imp_the_fall_of_earth.data.damage_sources.ModDamageSources;
import com.navi92.imp_the_fall_of_earth.item.custom.Shootable;
import com.navi92.imp_the_fall_of_earth.main.Config;
import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import com.navi92.imp_the_fall_of_earth.network.PacketHandler;
import com.navi92.imp_the_fall_of_earth.network.packets.C2SLeftClickAirToFirePacket;
import com.navi92.imp_the_fall_of_earth.util.MessagingService;
import com.navi92.imp_the_fall_of_earth.util.StormData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

public class ServerEvents {
    @Mod.EventBusSubscriber(modid = TheFallOfEarth.MOD_ID)
    public static class ServerForgeEvents {

        private static int tick = 0;

        @SubscribeEvent
        public static void onLevelTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                if (tick < 20) tick++;
                else {
                    tick = 0;
                    StormData storm = StormData.INSTANCE;

                    if (storm.isOnCooldown()) {
                        if (storm.increment(1, true)) {
                            storm.setCurrent(0);
                            storm.setOnCooldown(false);
                        }
                    } else if (storm.isOnTimer()) {
                        if (storm.increment(1, false)) {
                            storm.setCurrent(0);
                            if (storm.toggle(event.getServer()))
                                MessagingService.sendToggledOnMessage(event.getServer());
                            else
                                MessagingService.sendToggledOffMessage(event.getServer());
                        }
                        if (!storm.isOn() && Config.storm_cooldown == storm.getWaitInterval() - storm.getCurrent())
                            MessagingService.trySendWarningMessage(event.getServer());
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onLevelLoad(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                if (serverLevel.dimension() == Level.OVERWORLD) {

                    StormData.INSTANCE = StormData.get(serverLevel);
                }
            }
        }

        @SubscribeEvent
        public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
            ItemStack item = event.getItemStack();

            if (item.getItem() instanceof Shootable) {
                PacketHandler.sendToServer(new C2SLeftClickAirToFirePacket(item));
            }
        }

        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            new StormControlCommand(event.getDispatcher());
            ConfigCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            Player player = event.getEntity();
            ItemStack item = event.getItemStack();

            if (item.getItem() instanceof Shootable shootable && !event.getLevel().isClientSide()) {
                shootable.fire(item, event.getLevel(), player);
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (event.side.isServer() && StormData.INSTANCE.isOn()) {
                if (isHigherThanWg((ServerLevel) player.level(), player.blockPosition())) {
                    if (!(player.getTeam() != null && player.getTeam().getName().equals("immune"))) {

                        DamageSource freezeInStorm =
                                new ModDamageSources(event.player.level().registryAccess()).getFreezeInStorm();

                        event.player.hurt(freezeInStorm, 5.0f);
                    }
                }
            }
        }

        public static boolean isHigherThanWg(ServerLevel level, BlockPos blockPos) {
            BlockPos topPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockPos);

            return topPos.getY() <= blockPos.getY();
        }
    }

    @Mod.EventBusSubscriber(modid = TheFallOfEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ServerModEvents {
    }
}
