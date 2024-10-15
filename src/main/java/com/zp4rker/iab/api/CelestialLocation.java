package com.zp4rker.iab.api;

public class CelestialLocation {
    private final int x, y;

    public CelestialLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
