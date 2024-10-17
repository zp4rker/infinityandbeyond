package com.zp4rker.iab.db.tables;

import com.zp4rker.iab.IABCore;
import com.zp4rker.iab.api.Explorer;
import com.zp4rker.iab.api.Spaceship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpaceshipCrews {
    private static final String table = "spaceship_crews";

    public static void createTable() throws SQLException {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            c.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(" +
                    "id MEDIUMINT AUTO_INCREMENT," +

                    "spaceship MEDIUMINT NOT NULL," +
                    "explorer MEDIUMINT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (spaceship) REFERENCES spaceships(id)," +
                    "FOREIGN KEY (explorer) REFERENCES explorers(id))"
            ).execute();
        }
    }

    public static int getId(Spaceship spaceship, Explorer explorer) {
        try (Connection c = IABCore.DB_MANAGER.openConnection()) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT id FROM " + table + " WHERE spaceship = ? AND explorer = ?"
            );
            ps.setInt(1, Spaceships.getId(spaceship));
            ps.setInt(2, Explorers.getId(explorer));
            return ps.executeQuery().getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }
}
