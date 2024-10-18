package com.zp4rker.iab.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zp4rker.iab.IABCore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DatabaseTable(tableName = "spaceships")
public class Spaceship {
    @DatabaseField(id = true)
    private String name;
    @DatabaseField(canBeNull = false)
    private Date inauguration = new Date();

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Explorer captain;

    @DatabaseField(foreign = true)
    private Planet planet;
    @DatabaseField
    private Loc location;

    @DatabaseField
    private int health = 100;

    @DatabaseField
    private int maxHealth = 100;
    @DatabaseField
    private int speed = 1;
    @DatabaseField
    private int maxSize = 10;

    @DatabaseField
    private int tripCount = 0;
    @DatabaseField
    private float flightTime = 0;
    @DatabaseField
    private float distanceTravelled = 0;
    @DatabaseField
    private int planetsVisited = 0;

    public Spaceship(String name, Date inauguration, Explorer captain, PlanetaryLocation location, int health, int maxHealth, int speed, int maxSize, int tripCount, float flightTime, float distanceTravelled, int planetsVisited) {
        this.name = name;
        this.inauguration = inauguration;
        this.planet = location.getPlanet();
        this.location = location.getLoc();
        this.captain = captain;
        this.health = health;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.maxSize = maxSize;
        this.tripCount = tripCount;
        this.flightTime = flightTime;
        this.distanceTravelled = distanceTravelled;
        this.planetsVisited = planetsVisited;
    }

    public Spaceship(String name, Explorer captain, PlanetaryLocation location) {
        this.name = name;
        this.captain = captain;
        this.planet = location.getPlanet();
        this.location = location.getLoc();
    }

    public Spaceship() {
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
        return new PlanetaryLocation(planet, location.getX(), location.getY(), location.getZ());
    }

    public void setLocation(PlanetaryLocation location) {
        this.planet = location.getPlanet();
        this.location = location.getLoc();
    }

    public Explorer getCaptain() {
        return captain;
    }

    public void setCaptain(Explorer captain) {
        this.captain = captain;
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

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
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

    public boolean save() {
        try {
            IABCore.DB_MANAGER.saveSpaceship(this);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete() {
        try {
            IABCore.DB_MANAGER.deleteSpaceship(this);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean inDatabase() {
        return find(name) != null;
    }

    public static Spaceship find(String name) {
        try {
            return IABCore.DB_MANAGER.findSpaceship(name);
        } catch (SQLException e) {
            return null;
        }
    }
}
