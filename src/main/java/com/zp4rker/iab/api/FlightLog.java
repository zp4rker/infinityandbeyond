package com.zp4rker.iab.api;

import com.zp4rker.iab.api.storage.Saveable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;

import java.util.List;

@Entity("flightlogs")
public class FlightLog extends Saveable {
    private final Spaceship spaceship;
    private final Explorer captain;
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

    public Explorer getCaptain() {
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
