package com.zp4rker.iab.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public class HumanExplorer extends Explorer {
    private final UUID uuid;

    private int flightsCaptained = 0;

    public HumanExplorer(Player player, String explorerName) {
        super(explorerName);
        this.uuid = player.getUniqueId();
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getFlightsCaptained() {
        return flightsCaptained;
    }

    public void incrementFlightsCaptained() {
        flightsCaptained++;
    }
}
