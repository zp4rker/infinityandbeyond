package com.zp4rker.iab.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Explorer {
    private final UUID uuid;
    private String name;

    private int tripCount = 0;
    private float flightTime = 0;
    private float distanceTravelled = 0;
    private int planetsVisited = 0;

    public Explorer(Player player, String name) {
        this.uuid = player.getUniqueId();
        this.name = name;
    }

    public Explorer(UUID uuid, String name, int tripCount, float flightTime, float distanceTravelled, int planetsVisited) {
        this.uuid = uuid;
        this.name = name;
        this.tripCount = tripCount;
        this.flightTime = flightTime;
        this.distanceTravelled = distanceTravelled;
        this.planetsVisited = planetsVisited;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void incrementTripCount() {
        tripCount++;
    }

    public float getFlightTime() {
        return flightTime;
    }

    public void addFlightTime(float flightTime) {
        this.flightTime += flightTime;
    }

    public float getDistanceTravelled() {
        return distanceTravelled;
    }

    public void addDistanceTravelled(float distanceTravelled) {
        this.distanceTravelled += distanceTravelled;
    }

    public int getPlanetsVisited() {
        return planetsVisited;
    }

    public void incrementPlanetsVisited() {
        planetsVisited++;
    }
}
