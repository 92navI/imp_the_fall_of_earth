package com.navi92.imp_the_fall_of_earth.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

public class StormData extends SavedData {

    public static StormData INSTANCE;

    private boolean isOn;

    private long current;

    private long intervalStorm;

    private long intervalWait;

    private boolean isOnTimer;

    private long cooldown;

    private boolean isOnCooldown;

    public StormData() {
        this(false, 0L, 0L, 0L, false, 0L, false);
    }

    public StormData(boolean isOn, long current, long intervalStorm, long intervalWait, boolean isOnTimer, long cooldown, boolean isOnCooldown) {
        this.setDirty();
        this.isOn = isOn;
        this.current = current;
        this.intervalStorm = intervalStorm;
        this.intervalWait = intervalWait;
        this.isOnTimer = isOnTimer;
        this.cooldown = cooldown;
        this.isOnCooldown = isOnCooldown;
    }

    @Override
    public String toString() {
        return String.format("""
                        Storm is currently on: %s
                        Timer interval: %s ticks
                        Timer progress current: %s ticks""",
                isOn, intervalStorm, current);
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        pCompoundTag.putBoolean("is_on", isOn);
        pCompoundTag.putLong("current", current);
        pCompoundTag.putLong("interval_storm", intervalStorm);
        pCompoundTag.putLong("interval_wait", intervalWait);
        pCompoundTag.putBoolean("is_on_timer", isOnTimer);
        pCompoundTag.putLong("cooldown", current);
        pCompoundTag.putBoolean("will_be_on", isOn);

        return pCompoundTag;
    }

    public static StormData load(CompoundTag pCompoundTag) {
        boolean isOn = pCompoundTag.getBoolean("is_on");
        long current = pCompoundTag.getLong("current");
        long intervalStorm = pCompoundTag.getLong("interval_storm");
        long intervalWait = pCompoundTag.getLong("interval_wait");
        boolean isOnTimer = pCompoundTag.getBoolean("is_on_timer");
        long cooldown = pCompoundTag.getLong("cooldown");
        boolean willBeOn = pCompoundTag.getBoolean("will_be_on");

        return new StormData(isOn, current, intervalStorm, intervalWait, isOnTimer, cooldown, willBeOn);
    }

    public static StormData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(StormData::load, StormData::new, "storm");
    }


    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on, MinecraftServer server) {
        setDirty();
        isOn = on;

        if (on) {
            server.getLevel(Level.OVERWORLD)
                    .setWeatherParameters(0, -1, true, false);
        } else {
            server.getLevel(Level.OVERWORLD)
                    .setWeatherParameters(-1, 0, false, false);
        }
    }

    public long getStormInterval() {
        return intervalStorm;
    }

    public long getWaitInterval() {
        return intervalWait;
    }

    public void setIntervals(long intervalStorm, long intervalWait) {
        setDirty();
        this.intervalStorm = intervalStorm;
        this.intervalWait = intervalWait;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        setDirty();
        this.current = current;
    }

    public boolean increment(int n, boolean isOnCooldown) {
        setDirty();
        this.current = current + n;
        if (isOnCooldown)
            return current >= cooldown;
        else {
            if (isOn)
                return current >= intervalStorm;
            else
                return current >= intervalWait;
        }
    }

    public boolean toggle(MinecraftServer server) {
        setOn(!isOn, server);
        return isOn;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        setDirty();
        this.cooldown = cooldown;
    }

    public boolean isOnCooldown() {
        return isOnCooldown;
    }

    public void setOnCooldown(boolean onCooldown) {
        setDirty();
        this.isOnCooldown = onCooldown;
    }


    public boolean isOnTimer() {
        return isOnTimer;
    }

    public void setOnTimer(boolean onTimer) {
        setDirty();
        isOnTimer = onTimer;
    }

}
