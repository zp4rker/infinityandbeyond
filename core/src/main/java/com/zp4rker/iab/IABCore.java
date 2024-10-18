package com.zp4rker.iab;

import com.zp4rker.iab.api.*;
import com.zp4rker.iab.db.DBManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class IABCore extends JavaPlugin {
    public static IABCore INST;
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        INST = this;
        LOGGER = getLogger();

        saveDefaultConfig();

        registerCommands();
        registerListeners();

        connectDatabase();
        createTables();

        testDatabase();
    }

    @Override
    public void onDisable() {
        if (DB_MANAGER != null) {
            DB_MANAGER.closeConnection();
        }
    }

    private void registerCommands() {
        // Register commands
    }

    private void registerListeners() {
        // Arrays.asList().forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public static DBManager DB_MANAGER = null;

    private void connectDatabase() {
        String connectionStr = getConfig().getString("database.connectionString");
        try {
            DB_MANAGER = new DBManager(connectionStr);
            LOGGER.info("Connected to database");
        } catch (SQLException e) {
            LOGGER.severe("Unable to connect to database! Shutting down...");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void createTables() {
        try {
            DB_MANAGER.createTables();
            LOGGER.info("Created tables");
        } catch (SQLException e) {
            LOGGER.severe("Unable to create tables! Shutting down...");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void testDatabase() {
        Planet planet = new Planet("Origin", new Loc(0, 0, 0), 1000, 1000);
        Explorer explorer = new Explorer(UUID.randomUUID(), "zp4rker", 0, 0, 0, 0);
        Spaceship spaceship = new Spaceship("The Explorer", explorer, new PlanetaryLocation(planet, 0, 0, 0));

        LOGGER.info("Planet save and read: " + (planet.save() == planet.inDatabase()));
        LOGGER.info("Explorer save and read: " + (explorer.save() == explorer.inDatabase()));
        LOGGER.info("Spaceship save and read: " + (spaceship.save() == spaceship.inDatabase()));
    }
}
