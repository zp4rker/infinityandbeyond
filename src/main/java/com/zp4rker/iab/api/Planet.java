package com.zp4rker.iab.api;

public class Planet {
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
