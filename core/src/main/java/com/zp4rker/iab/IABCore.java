package com.zp4rker.iab;

import com.zp4rker.iab.db.DBManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
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
        shushOrmliteLogger();
        createTables();
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
            LOGGER.info("Connected and setup database");
        } catch (SQLException e) {
            LOGGER.severe("Unable to connect to or setup database! Shutting down...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void createTables() {
        try {
            DB_MANAGER.createTables();
            LOGGER.info("Created tables");
        } catch (SQLException e) {
            LOGGER.severe("Unable to create tables! Shutting down...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void shushOrmliteLogger() {
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(new AbstractFilter() {
            @Override
            public Result filter(LogEvent event) {
                if (event == null) return Result.NEUTRAL;
                if (event.getLoggerName().startsWith("com.j256.ormlite") && event.getLevel().isLessSpecificThan(Level.WARN)) return Result.DENY;
                return Result.NEUTRAL;
            }
        });
    }
}
