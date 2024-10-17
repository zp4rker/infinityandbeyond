package com.zp4rker.iab.api;

import com.zp4rker.iab.api.db.Saveable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.UUID;

@Entity("flightlogs")
public class FlightLog extends Saveable {
    @Id
    private ObjectId id;

    private final String spaceshipName;
    private final UUID captainId;
    private final List<UUID> crew;

    private final CelestialLocation locFrom;
    private final CelestialLocation locTo;

    private /*final*/ float flightTime;
    private /*final*/ float flightDistance;

    public FlightLog(Spaceship spaceship, CelestialLocation locFrom, CelestialLocation locTo) {
        this.spaceshipName = spaceship.getName();
        this.captainId = spaceship.getCaptainId();
        this.crew = spaceship.getCrew();

        this.locFrom = locFrom;
        this.locTo = locTo;

        // Calculate flight time
        // Calculate flightDistance
    }

    public String getSpaceshipName() {
        return spaceshipName;
    }

    public Spaceship getSpaceship() {
        return Spaceship.fromName(spaceshipName);
    }

    public UUID getCaptainId() {
        return captainId;
    }

    public Explorer getCaptain() {
        return Explorer.fromId(captainId);
    }

    public List<UUID> getCrew() {
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
