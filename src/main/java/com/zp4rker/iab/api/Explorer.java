package com.zp4rker.iab.api;

import com.zp4rker.iab.InfinityAndBeyond;
import com.zp4rker.iab.api.storage.Saveable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.query.experimental.filters.Filters;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;

import java.util.UUID;

@Entity("explorers")
public class Explorer extends Saveable {
    @Id
    private final String uuid;
    private String name;

    private int tripCount = 0;
    private float flightTime = 0;
    private float distanceTravelled = 0;
    private int planetsVisited = 0;

    public Explorer(Player player, String name) {
        this.uuid = player.getUniqueId().toString();
        this.name = name;
    }

    public UUID getUUID() {
        return UUID.fromString(uuid);
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

    public static Explorer fromId(UUID id) {
        return InfinityAndBeyond.DATABASE.find(Explorer.class).filter(Filters.eq("uuid", id)).first();
    }
}
