package com.navi92.imp_the_fall_of_earth.network.packets;

import com.navi92.imp_the_fall_of_earth.item.custom.Blaster;
import com.navi92.imp_the_fall_of_earth.item.custom.Shootable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class C2SLeftClickAirToFirePacket {

    private final ItemStack itemStack;

    public C2SLeftClickAirToFirePacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public C2SLeftClickAirToFirePacket(FriendlyByteBuf buffer) {
        this(buffer.readItem());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItem(itemStack);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();

        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (itemStack.getItem() instanceof Shootable shootable && player != null) {
                shootable.fire(itemStack, player.level(), player);
            }
        });

        context.setPacketHandled(true);
    }
}
