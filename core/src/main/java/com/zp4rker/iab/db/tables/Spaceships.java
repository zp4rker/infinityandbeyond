package com.zp4rker.iab.db.tables;

import com.zp4rker.iab.IABCore;
import com.zp4rker.iab.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Spaceships {
    private static final String table = "spaceships";

    public static void createTable() throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            c.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(" +
                    "id MEDIUMINT AUTO_INCREMENT," +

                    "name VARCHAR(100) NOT NULL UNIQUE," +
                    "inauguration TIMESTAMP NOT NULL," +
                    "captain MEDIUMINT NOT NULL," +

                    "loc_planet INT," +
                    "loc_x SMALLINT," +
                    "loc_y SMALLINT," +
                    "loc_z SMALLINT," +

                    "health SMALLINT DEFAULT 100," +

                    "maxHealth SMALLINT DEFAULT 100," +
                    "speed SMALLINT DEFAULT 1," +
                    "maxSize SMALLINT DEFAULT 10," +

                    "tripCount INT DEFAULT 0," +
                    "flightTime FLOAT DEFAULT 0," +
                    "distanceTravelled FLOAT DEFAULT 0," +
                    "planetsVisited INT DEFAULT 0," +

                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (capitan) REFERENCES explorers(id))"
            ).execute();
        }
    }

    public static void save(Spaceship spaceship) throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            int id = getId(spaceship);
            if (id != -1) {
                c.prepareStatement("UPDATE " + table + " SET " +
                        "name = " + spaceship.getName() + "," +
                        "inauguration = " + spaceship.getInauguration() + "," +
                        "captain = " + Explorers.getId(spaceship.getCaptain()) + "," +
                        "loc_planet = " + spaceship.getLocation().getPlanet()/*todo: get planet id*/ + "," +
                        "loc_x = " + spaceship.getLocation().getX() + "," +
                        "loc_y = " + spaceship.getLocation().getY() + "," +
                        "loc_z = " + spaceship.getLocation().getZ() + "," +
                        "health = " + spaceship.getHealth() + "," +
                        "maxHealth = " + spaceship.getMaxHealth() + "," +
                        "speed = " + spaceship.getSpeed() + "," +
                        "maxSize = " + spaceship.getMaxSize() + "," +
                        "tripCount  = " + spaceship.getTripCount() + "," +
                        "flightTime = " + spaceship.getFlightTime() + "," +
                        "distanceTravelled =" + spaceship.getDistanceTravelled() + "," +
                        "planetsVisited = " + spaceship.getPlanetsVisited() +
                        " WHERE id = " + id
                ).execute();
            } else {
                c.prepareStatement("INSERT INTO " + table + "(" +
                        "name," +
                        "inauguration," +
                        "captain," +
                        "loc_planet," +
                        "loc_x," +
                        "loc_y," +
                        "loc_z," +
                        "health," +
                        "maxHealth," +
                        "speed," +
                        "maxSize," +
                        "tripCount," +
                        "flightTime," +
                        "distanceTravelled," +
                        "planetsVisited" +
                        ") values (" +
                        spaceship.getName() + "," +
                        spaceship.getInauguration() + "," +
                        Explorers.getId(spaceship.getCaptain()) + "," +
                        spaceship.getLocation().getPlanet()/*todo: get planet id*/ + "," +
                        spaceship.getLocation().getX() + "," +
                        spaceship.getLocation().getY() + "," +
                        spaceship.getLocation().getZ() + "," +
                        spaceship.getHealth() + "," +
                        spaceship.getMaxHealth() + "," +
                        spaceship.getSpeed() + "," +
                        spaceship.getMaxSize() + "," +
                        spaceship.getTripCount() + "," +
                        spaceship.getFlightTime() + "," +
                        spaceship.getDistanceTravelled() + "," +
                        spaceship.getPlanetsVisited() + ")"
                ).execute();
            }
        }
    }

    public static void delete(Spaceship spaceship) throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            int id = getId(spaceship);
            if (id != -1) {
                c.prepareStatement(
                        "DELETE FROM " + table + " WHERE id = " + id
                ).execute();
            }
        }
    }

    public static Spaceship find(String name) {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            ResultSet rs = c.prepareStatement(
                    "SELECT * FROM " + table + " WHERE name = " + name
            ).executeQuery();
            Date date = rs.getDate("inauguration");
            Explorer captain = Explorers.fromId(rs.getInt("captain"));
            // todo: fetch crew
            List<Explorer> crew = new ArrayList<>();
            // todo: construct planet from id
            Planet planet = new Planet("Placeholder", new CelestialLocation(0, 0), 100, 100);
            int loc_x = rs.getInt("loc_x");
            int loc_y = rs.getInt("loc_y");
            int loc_z = rs.getInt("loc_z");
            int health = rs.getInt("health");
            int maxHealth = rs.getInt("maxHealth");
            int speed = rs.getInt("speed");
            int maxSize = rs.getInt("maxSize");
            int tripCount = rs.getInt("tripCount");
            int flightTime = rs.getInt("flightTime");
            int distanceTravelled = rs.getInt("distanceTravelled");
            int planetsVisited = rs.getInt("planetsVisited");
            return new Spaceship(name, date, captain, crew, new PlanetaryLocation(planet, loc_x, loc_y, loc_z), health, maxHealth, speed, maxSize, tripCount, flightTime, distanceTravelled, planetsVisited);
        } catch (SQLException e) {
            return null;
        }
    }

    public static int getId(Spaceship spaceship) {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            return c.prepareStatement(
                    "SELECT id FROM " + table + " WHERE name = " + spaceship.getName()
            ).executeQuery().getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }
}
