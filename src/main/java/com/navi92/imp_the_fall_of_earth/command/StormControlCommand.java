package com.navi92.imp_the_fall_of_earth.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.navi92.imp_the_fall_of_earth.main.Config;
import com.navi92.imp_the_fall_of_earth.main.TheFallOfEarth;
import com.navi92.imp_the_fall_of_earth.util.MessagingService;
import com.navi92.imp_the_fall_of_earth.util.StormData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;

public class StormControlCommand {

    public StormControlCommand(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        var literalcommandnode = dispatcher.register(Commands.literal("storm")
                .then(Commands.literal("start")
                        .executes(this::stormStart)
                        .then(Commands.literal("-strict")
                                .executes(this::stormStartStrict)
                        )
                ).then(Commands.literal("end")
                        .executes(this::stormEnd)
                ).then(Commands.literal("timer")
                        .then(Commands.argument("storm_length", LongArgumentType.longArg())
                                .executes((c) -> stormStartTimer(c, false, false))
                                .then(Commands.argument("wait_length", LongArgumentType.longArg())
                                        .executes((c) -> stormStartTimer(c, false, true))
                                )
                        ).then(Commands.literal("-strict")
                                .then(Commands.argument("storm_length", LongArgumentType.longArg())
                                        .executes((c) -> stormStartTimer(c, true, false))
                                        .then(Commands.argument("wait_length", LongArgumentType.longArg())
                                                .executes((c) -> stormStartTimer(c, true, true))
                                        )
                                )
                        )
                ));

        dispatcher.register(Commands.literal("s").redirect(literalcommandnode));
    }

    private int stormStartStrict(@NotNull CommandContext<CommandSourceStack> command) {
        TheFallOfEarth.LOGGER.info("Started Storm \\w strict arg.");
        MessagingService.sendToggledOnMessage(command.getSource().getServer());

        StormData.INSTANCE.setOn(true, command.getSource().getServer());
        StormData.INSTANCE.setOnCooldown(false);
        StormData.INSTANCE.setOnTimer(false);
        return 0;
    }

    private int stormStart(@NotNull CommandContext<CommandSourceStack> command) {
        TheFallOfEarth.LOGGER.info(
                String.format("Starting Storm in %s minutes (%s game ticks)",
                        Config.storm_cooldown / 60F, Config.storm_cooldown * 20));
        MessagingService.trySendWarningMessage(command.getSource().getServer());

        StormData.INSTANCE.setOn(false, command.getSource().getServer());
        StormData.INSTANCE.setOnCooldown(true);
        StormData.INSTANCE.setCooldown(Config.storm_cooldown);
        StormData.INSTANCE.setOnTimer(false);
        return 0;
    }

    private int stormEnd(@NotNull CommandContext<CommandSourceStack> command) {
        TheFallOfEarth.LOGGER.info("Stopped Storm.");
        MessagingService.sendToggledOffMessage(command.getSource().getServer());

        StormData.INSTANCE.setOn(false, command.getSource().getServer());
        StormData.INSTANCE.setOnCooldown(false);
        StormData.INSTANCE.setOnTimer(false);

        return 0;
    }

    private int stormStartTimer(@NotNull CommandContext<CommandSourceStack> command, boolean strict, boolean twoArgs) {
        long stormLength = 60 * LongArgumentType.getLong(command, "storm_length");

        if (twoArgs) {
            long waitLength = 60 * LongArgumentType.getLong(command, "wait_length");
            StormData.INSTANCE.setIntervals(stormLength, waitLength);
        } else
            StormData.INSTANCE.setIntervals(stormLength, stormLength);

        if (strict) {

            TheFallOfEarth.LOGGER.info(
                    String.format("Starting Storm of length %s minute (%s ticks)",
                            stormLength / 60F, stormLength * 20));
            MessagingService.sendToggledOnMessage(command.getSource().getServer());

            StormData.INSTANCE.setOn(true, command.getSource().getServer());
            StormData.INSTANCE.setOnCooldown(false);
            StormData.INSTANCE.setOnTimer(true);
        } else {

            TheFallOfEarth.LOGGER.info(
                    String.format("Starting Storm in %s minutes(%s game ticks) with length %s minute (%s game ticks)",
                            Config.storm_cooldown / 60F, Config.storm_cooldown * 20, stormLength / 60F, stormLength * 20));
            MessagingService.trySendWarningMessage(command.getSource().getServer());

            StormData.INSTANCE.setOn(false, command.getSource().getServer());
            StormData.INSTANCE.setOnCooldown(true);
            StormData.INSTANCE.setCooldown(Config.storm_cooldown);
            StormData.INSTANCE.setOnTimer(true);
        }

        return 0;
    }
}
