package com.navi92.imp_the_fall_of_earth.network;

import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import com.navi92.imp_the_fall_of_earth.network.packets.C2SLeftClickAirToFirePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(TheFallOfEarth.MOD_ID, "main"))
            .serverAcceptedVersions(a -> true)
            .clientAcceptedVersions(a -> true)
            .networkProtocolVersion(() -> "1.0")
            .simpleChannel();

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(C2SLeftClickAirToFirePacket.class, id())
                .encoder(C2SLeftClickAirToFirePacket::encode)
                .decoder(C2SLeftClickAirToFirePacket::new)
                .consumerMainThread(C2SLeftClickAirToFirePacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
    }
}
