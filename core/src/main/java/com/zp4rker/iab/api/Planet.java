package com.zp4rker.iab.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zp4rker.iab.IABCore;

import java.sql.SQLException;

@DatabaseTable(tableName = "planets")
public class Planet {
    @DatabaseField(id = true)
    private final String name;
    @DatabaseField
    private final Loc location;

    @DatabaseField
    private final int width;
    @DatabaseField
    private final int height;

    public Planet(String name, Loc location, int width, int height) {
        this.name = name;
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public Loc getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean save() {
        try {
            IABCore.DB_MANAGER.savePlanet(this);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete() {
        try {
            IABCore.DB_MANAGER.deletePlanet(this);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean inDatabase() {
        return Planet.find(name) != null;
    }

    public static Planet find(String name) {
        try {
            return IABCore.DB_MANAGER.findPlanet(name);
        } catch (SQLException e) {
            return null;
        }
    }
}
