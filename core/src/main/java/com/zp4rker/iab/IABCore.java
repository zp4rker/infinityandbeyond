package com.zp4rker.iab;

import com.zp4rker.iab.db.DBManager;
import org.bukkit.plugin.java.JavaPlugin;

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

        setupDatabase();
    }

    private void registerCommands() {
        // Register commands
    }

    private void registerListeners() {
        // Arrays.asList().forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public static DBManager DB_MANAGER;
    private void setupDatabase() {
        String connectionStr = getConfig().getString("database.connectionString");
        DB_MANAGER = new DBManager(connectionStr);
    }
}
