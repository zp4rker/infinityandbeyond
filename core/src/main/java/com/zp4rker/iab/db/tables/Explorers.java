package com.zp4rker.iab.db.tables;

import com.zp4rker.iab.IABCore;
import com.zp4rker.iab.api.Explorer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Explorers {
    private static final String table = "explorers";

    public static void createTable() throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            c.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(" +
                    "id MEDIUMINT AUTO_INCREMENT," +
                    "minecraftId CHAR(36) NOT NULL," +
                    "name VARCHAR(35) NOT NULL," +

                    "tripCount INT DEFAULT 0," +
                    "flightTime FLOAT DEFAULT 0," +
                    "distanceTravelled FLOAT DEFAULT 0," +
                    "planetsVisited INT DEFAULT 0," +

                    "PRIMARY KEY (id))"
            ).execute();
        }
    }

    public static void save(Explorer explorer) throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            int id = getId(explorer);
            if (id != -1) {
                c.prepareStatement("UPDATE " + table); // TODO: finish this query
            } else {
                c.prepareStatement("INSERT INTO " + table + "(" +
                        "minecraftId," +
                        "name," +
                        "tripCount," +
                        "flightTime," +
                        "distanceTravelled," +
                        "planetsVisited" +
                        ") values(" +
                        explorer.getUUID() + "," +
                        explorer.getName() + "," +
                        explorer.getTripCount() + "," +
                        explorer.getFlightTime() + "," +
                        explorer.getDistanceTravelled() + "," +
                        explorer.getPlanetsVisited() + ")"
                ).execute();
            }
        }
    }

    public static void delete(Explorer explorer) throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            int id = getId(explorer);
            if (id != -1) {
                c.prepareStatement(
                        "DELETE FROM " + table + " WHERE id = " + id
                ).execute();
            }
        }
    }

    public static Explorer find(UUID uuid) {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            ResultSet rs = c.prepareStatement(
                    "SELECT * FROM " + table + " WHERE uuid = " + uuid
            ).executeQuery();
            return constructFromResultSet(uuid, rs);
        } catch (SQLException e) {
            return null;
        }
    }

    public static int getId(Explorer explorer) {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            return c.prepareStatement(
                    "SELECT id FROM " + table + " WHERE uuid = " + explorer.getUUID()
            ).executeQuery().getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    public static Explorer fromId(int id) {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            ResultSet rs = c.prepareStatement(
                    "SELECT * FROM " + table + " WHERE id = " + id
            ).executeQuery();
            UUID uuid = UUID.fromString(rs.getString("uuid"));
            return constructFromResultSet(uuid, rs);
        } catch (SQLException e) {
            return null;
        }
    }

    private static Explorer constructFromResultSet(UUID uuid, ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int tripCount = rs.getInt("tripCount");
        float flightTime = rs.getFloat("flightTime");
        float distanceTravelled = rs.getFloat("distanceTravelled");
        int planetsVisited = rs.getInt("planetsVisited");
        return new Explorer(uuid, name, tripCount, flightTime, distanceTravelled, planetsVisited);
    }
}
