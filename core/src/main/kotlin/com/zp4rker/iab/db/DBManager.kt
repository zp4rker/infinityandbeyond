package com.zp4rker.iab.db

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.field.DataPersisterManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.zp4rker.iab.PLUGIN
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.api.Planet
import com.zp4rker.iab.api.Spaceship
import java.io.File
import java.util.*

class DBManager(connectionUrl: String) {
    private val cs: ConnectionSource = JdbcConnectionSource(connectionUrl)

    private val planetDao: Dao<Planet, String>
    private val explorerDao: Dao<Explorer, UUID>
    private val spaceshipDao: Dao<Spaceship, String>

    init {
        DataPersisterManager.registerDataPersisters(XYCoordPersister.getSingleton(), CoordPersister.getSingleton())

        planetDao = DaoManager.createDao(cs, Planet::class.java)
        explorerDao = DaoManager.createDao(cs, Explorer::class.java)
        spaceshipDao = DaoManager.createDao(cs, Spaceship::class.java)
    }

    fun createTables() {
        TableUtils.createTableIfNotExists(cs, Planet::class.java)
        TableUtils.createTableIfNotExists(cs, Explorer::class.java)
        TableUtils.createTableIfNotExists(cs, Spaceship::class.java)
    }

    fun findPlanet(name: String): Planet? = planetDao.queryForId(name)
    fun savePlanet(planet: Planet) = planetDao.createOrUpdate(planet).numLinesChanged == 1
    fun deletePlanet(planet: Planet) = planetDao.delete(planet)

    fun findExplorer(uuid: UUID): Explorer? = explorerDao.queryForId(uuid)
    fun saveExplorer(explorer: Explorer) = explorerDao.createOrUpdate(explorer).numLinesChanged == 1
    fun deleteExplorer(explorer: Explorer) = explorerDao.delete(explorer)

    fun findSpaceship(name: String): Spaceship? = spaceshipDao.queryForId(name)
    fun saveSpaceship(spaceship: Spaceship) = spaceshipDao.createOrUpdate(spaceship).numLinesChanged == 1
    fun deleteSpaceship(spaceship: Spaceship) = spaceshipDao.delete(spaceship)

    fun closeConnection() {
        cs.closeQuietly()
    }

    companion object {
        fun connectionUrl(dbType: String, host: String, user: String?, password: String?, dbname: String): String {
            if (dbType.lowercase() !in arrayOf("mysql", "sqlite")) {
                throw IllegalArgumentException("Unknown database type: $dbType")
            }

            return if (dbType.equals("mysql", true)) {
                "jdbc:mysql://$host/$dbname?user=$user&password=$password"
            } else {
                "jdbc:sqlite:${File(PLUGIN.dataFolder, host).absolutePath}"
            }
        }
    }
}