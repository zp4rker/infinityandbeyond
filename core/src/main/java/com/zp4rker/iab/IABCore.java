package com.zp4rker.iab;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.zp4rker.iab.api.Explorer;
import com.zp4rker.iab.api.FlightLog;
import com.zp4rker.iab.api.Planet;
import com.zp4rker.iab.api.Spaceship;
import com.zp4rker.iab.api.storage.CelestialLocationCodec;
import com.zp4rker.iab.api.storage.PlaneteryLocationCodec;
import com.zp4rker.iab.api.storage.UUIDCodec;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.bson.codecs.configuration.CodecRegistries;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class IABCore extends JavaPlugin {
    public static IABCore INST;
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        INST = this;
        LOGGER = getLogger();

        disableMongoLoggers();

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

    public static Datastore DATABASE;

    private void setupDatabase() {
        String connectionString = getConfig().getString("database.connectionString");
        try {
            assert connectionString != null;
            MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(CodecRegistries.fromRegistries(
                    CodecRegistries.fromCodecs(
                            new CelestialLocationCodec(),
                            new PlaneteryLocationCodec(),
                            new UUIDCodec()
                    ),
                    MongoClientSettings.getDefaultCodecRegistry()
            )).applyConnectionString(new ConnectionString(connectionString)).build();
            DATABASE = Morphia.createDatastore(MongoClients.create(settings), "infinityandbeyond");
            DATABASE.getMapper().map(Explorer.class, Spaceship.class, FlightLog.class, Planet.class);
            DATABASE.ensureIndexes();
        } catch (MongoException | AssertionError e) {
            LOGGER.warning("Invalid connection string, or unable to connect to database server! Shutting down...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void disableMongoLoggers() {
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(new AbstractFilter() {
            @Override
            public Result filter(LogEvent event) {
                if (event == null) return Result.NEUTRAL;
                if (event.getLoggerName().contains("org.mongodb") && event.getLevel().isLessSpecificThan(Level.WARN)) return Result.DENY;
                return Result.NEUTRAL;
            }
        });
    }
}
