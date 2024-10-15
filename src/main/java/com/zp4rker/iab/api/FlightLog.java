package com.zp4rker.iab.api;

import java.util.List;

public class FlightLog {
    private final Spaceship spaceship;
    private final HumanExplorer captain;
    private final List<Explorer> crew;

    private final CelestialLocation locFrom;
    private final CelestialLocation locTo;

    private /*final*/ float flightTime;
    private /*final*/ float flightDistance;

    public FlightLog(Spaceship spaceship, CelestialLocation locFrom, CelestialLocation locTo) {
        this.spaceship = spaceship;
        this.captain = spaceship.getCaptain();
        this.crew = spaceship.getCrew();

        this.locFrom = locFrom;
        this.locTo = locTo;

        // Calculate flight time
        // Calculate flightDistance
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public HumanExplorer getCaptain() {
        return captain;
    }

    public List<Explorer> getCrew() {
        return crew;
    }

    public CelestialLocation getLocFrom() {
        return locFrom;
    }

    public CelestialLocation getLocTo() {
        return locTo;
    }

    public float getFlightTime() {
        return flightTime;
    }

    public float getFlightDistance() {
        return flightDistance;
    }
}
