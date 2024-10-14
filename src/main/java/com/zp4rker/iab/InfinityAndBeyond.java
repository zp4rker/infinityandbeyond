package com.zp4rker.iab;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class InfinityAndBeyond extends JavaPlugin {
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        InfinityAndBeyond.LOGGER = getLogger();
        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        // Register commands
    }

    private void registerListeners() {
        // Register listeners
    }
}
