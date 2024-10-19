package com.zp4rker.iab;

import com.zp4rker.iab.db.DBManager;
import com.zp4rker.iab.listeners.PlayerJoinListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;

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

        configDatabase();
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
         Arrays.asList(new PlayerJoinListener()).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public static DBManager DB_MANAGER = null;

    private void configDatabase() {
        try {
            String connectionStr = getConfig().getString("database.connectionString", null);
            if (connectionStr == null) {
                String dbType = getConfig().getString("database.type", "mysql");
                String host = getConfig().getString("database.host", null);
                String user = getConfig().getString("database.user", null);
                String password = getConfig().getString("database.password", null);
                String dbname = getConfig().getString("database.dbname", "infinityandbeyond");
                if (host == null || (dbType.equalsIgnoreCase("mysql") && Stream.of(user, password).anyMatch(Objects::isNull))) {
                    LOGGER.warning("Invalid/incomplete database configuration found! Falling back to file database");
                    String file = getDataFolder().getAbsolutePath() + File.pathSeparator + "plugindata.db";
                    connectionStr = "jdbc:sqlite:" + file;
                } else {
                    connectionStr = DBManager.connectionString(dbType, host, user, password, dbname);
                }
            }
            DB_MANAGER = new DBManager(connectionStr);
            LOGGER.info("Completed database configuration");
        } catch (SQLException e) {
            LOGGER.severe("Unable to configure database: " + e.getMessage());
            LOGGER.info("Disabling plugin");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void createTables() {
        try {
            DB_MANAGER.createTables();
            LOGGER.info("Created tables");
        } catch (SQLException e) {
            LOGGER.severe("Unable to create tables: \n" + e.getMessage());
            LOGGER.info("Disabling plugin");
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
