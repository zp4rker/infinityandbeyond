package com.zp4rker.iab.api;

import com.zp4rker.iab.IABCore;
import com.zp4rker.iab.api.db.Saveable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.query.experimental.filters.Filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity("spaceships")
public class Spaceship extends Saveable {
    @Id
    private String name;
    private final Date inauguration = new Date();

    private PlanetaryLocation location;

    private UUID captainId;
    private List<UUID> crew = new ArrayList<>();

    private int health = 100;

    private int maxHealth = 100;
    private int speed = 1;
    private int size = 1;

    private int tripCount = 0;
    private float flightTime = 0;
    private float distanceTravelled = 0;
    private int planetsVisited = 0;

    public Spaceship(String name, Explorer captain, PlanetaryLocation location) {
        this.name = name;
        this.captainId = captain.getUUID();
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInauguration() {
        return inauguration;
    }

    public PlanetaryLocation getLocation() {
        return location;
    }

    public void setLocation(PlanetaryLocation location) {
        this.location = location;
    }

    public UUID getCaptainId() {
        return captainId;
    }

    public void setCaptainId(UUID captainId) {
        this.captainId = captainId;
    }

    public Explorer getCaptain() {
        return Explorer.fromId(captainId);
    }

    public void setCaptain(Explorer captain) {
        this.captainId = captain.getUUID();
    }

    public List<UUID> getCrew() {
        return crew;
    }

    public void addToCrew(Explorer explorer) {
        crew.add(explorer.getUUID());
    }

    public void removeFromCrew(Explorer explorer) {
        crew.remove(explorer.getUUID());
    }

    public void setCrew(List<UUID> crew) {
        this.crew = crew;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void repair(int repair) {
        health += repair;
        if (health > maxHealth) health = maxHealth;
    }

    public void setHealth(int health) {
        if (health < 0) this.health = 0;
        else this.health = Math.min(health, maxHealth);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void incremementTripCount() {
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

    public void incremePlanetsVisited() {
        planetsVisited++;
    }

    public static Spaceship fromName(String name) {
        return IABCore.DATABASE.find(Spaceship.class).filter(Filters.eq("name", name)).first();
    }
}
