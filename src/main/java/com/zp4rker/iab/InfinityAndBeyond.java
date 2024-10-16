package com.zp4rker.iab;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zp4rker.iab.api.*;
import com.zp4rker.iab.api.storage.*;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Logger;

public class InfinityAndBeyond extends JavaPlugin implements Listener {
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        LOGGER = getLogger();

        saveDefaultConfig();

        registerCommands();
        registerListeners();

        setupDatabase();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Planet planet = new Planet("Origin", new CelestialLocation(0, 0), 100, 100);
        planet.save();

        Explorer explorer = new Explorer(event.getPlayer(), event.getPlayer().getName());
        Spaceship spaceship = new Spaceship("The Explorer", explorer, new PlanetaryLocation(planet, 0, 50, 0));
        FlightLog flightLog = new FlightLog(spaceship, new CelestialLocation(0, 0), new CelestialLocation(1, 1));

        explorer.save();
        spaceship.save();
        flightLog.save();

        LOGGER.info(spaceship.getCaptain().getName());
    }

    private void registerCommands() {
        // Register commands
    }

    private void registerListeners() {
        Arrays.asList(
                this
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
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
}
