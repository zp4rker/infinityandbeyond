package com.zp4rker.iab.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zp4rker.iab.IABCore;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

@DatabaseTable(tableName = "explorers")
public class Explorer {
    @DatabaseField(id = true)
    private UUID uuid;
    @DatabaseField(unique = true, canBeNull = false)
    private String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Spaceship spaceship;

    @DatabaseField
    private int tripCount = 0;
    @DatabaseField
    private float flightTime = 0;
    @DatabaseField
    private float distanceTravelled = 0;
    @DatabaseField
    private int planetsVisited = 0;

    public Explorer(Player player, String name) {
        this.uuid = player.getUniqueId();
        this.name = name;
    }

    public Explorer() {
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

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
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

    public boolean save() {
        try {
            IABCore.DB_MANAGER.saveExplorer(this);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete() {
        try {
            IABCore.DB_MANAGER.deleteExplorer(this);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean inDatabase() {
        return find(uuid) != null;
    }

    public static Explorer find(Player player) {
        return find(player.getUniqueId());
    }

    public static Explorer find(UUID uuid) {
        try {
            return IABCore.DB_MANAGER.findExplorer(uuid);
        } catch (SQLException e) {
            return null;
        }
    }
}
