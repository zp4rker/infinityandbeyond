package com.zp4rker.iab.api;

public class Explorer {
    private String name;

    private int flightCount = 0;
    private float flightTime = 0;
    private float distanceTravelled = 0;
    private int planetsVisited = 0;

    public Explorer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlightCount() {
        return flightCount;
    }

    public void incrementFlightCount() {
        flightCount++;
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
