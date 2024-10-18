package com.zp4rker.iab.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zp4rker.iab.IABCore;
import com.zp4rker.iab.db.LocPersister;

import java.sql.SQLException;

@DatabaseTable(tableName = "planets")
public class Planet {
    @DatabaseField(id = true)
    private String name;
    @DatabaseField(persisterClass = LocPersister.class)
    private Loc location;

    @DatabaseField
    private int width;
    @DatabaseField
    private int height;

    public Planet(String name, Loc location, int width, int height) {
        this.name = name;
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public Planet() {
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
