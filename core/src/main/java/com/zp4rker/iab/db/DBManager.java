package com.zp4rker.iab.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zp4rker.iab.api.Explorer;
import com.zp4rker.iab.api.Planet;
import com.zp4rker.iab.api.Spaceship;

import java.sql.SQLException;
import java.util.UUID;

public class DBManager {
    private ConnectionSource cs = null;

    private final Dao<Planet, String> planetDao;
    private final Dao<Explorer, UUID> explorerDao;
    private final Dao<Spaceship, String> spaceshipDao;

    public DBManager(String connectionStr) throws SQLException {
        cs = new JdbcConnectionSource(connectionStr);

        planetDao = DaoManager.createDao(cs, Planet.class);
        explorerDao = DaoManager.createDao(cs, Explorer.class);
        spaceshipDao = DaoManager.createDao(cs, Spaceship.class);
    }

    public void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(cs, Explorer.class);
        TableUtils.createTableIfNotExists(cs, Spaceship.class);
    }

    public Planet findPlanet(String name) throws SQLException {
        return planetDao.queryForId(name);
    }

    public void savePlanet(Planet planet) throws SQLException {
        planetDao.create(planet);
    }

    public void deletePlanet(Planet planet) throws SQLException {
        planetDao.delete(planet);
    }

    public Explorer findExplorer(UUID uuid) throws SQLException {
        return explorerDao.queryForId(uuid);
    }

    public void saveExplorer(Explorer explorer) throws SQLException {
        explorerDao.createOrUpdate(explorer);
    }

    public void deleteExplorer(Explorer explorer) throws SQLException {
        explorerDao.delete(explorer);
    }

    public Spaceship findSpaceship(String name) throws SQLException {
        return spaceshipDao.queryForId(name);
    }

    public void saveSpaceship(Spaceship spaceship) throws SQLException {
        spaceshipDao.createOrUpdate(spaceship);
    }

    public void deleteSpaceship(Spaceship spaceship) throws SQLException {
        spaceshipDao.delete(spaceship);
    }

    public void closeConnection() {
        if (cs != null) {
            cs.closeQuietly();
            cs = null;
        }
    }
}
