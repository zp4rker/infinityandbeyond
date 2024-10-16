package com.zp4rker.iab.api;

import com.zp4rker.iab.api.storage.Saveable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("planets")
public class Planet extends Saveable {
    private final String name;
    private final CelestialLocation location;

    private final int width, height;

    public Planet(String name, CelestialLocation location, int width, int height) {
        this.name = name;
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public CelestialLocation getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
