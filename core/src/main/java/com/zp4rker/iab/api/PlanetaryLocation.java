package com.zp4rker.iab.api;

public class PlanetaryLocation {
    private final Planet planet;
    private int x, y, z;

    public PlanetaryLocation(Planet planet, int x, int y, int z) {
        this.planet = planet;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Planet getPlanet() {
        return planet;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Coord getCoord() {
        return new Coord(x, y, z);
    }
}
