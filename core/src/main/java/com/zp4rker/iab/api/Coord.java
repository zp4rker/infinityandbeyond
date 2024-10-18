package com.zp4rker.iab.api;

public class Coord extends XYCoord {
    private final int z;

    public Coord(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public int getZ() {
        return z;
    }
}
