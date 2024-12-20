package com.zp4rker.iab

import co.aikar.commands.PaperCommandManager
import com.zp4rker.bukkot.extensions.register
import com.zp4rker.iab.db.DBManager
import com.zp4rker.iab.listeners.WelcomeMessage
import com.zp4rker.iab.prompts.ExplorerSetup
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.filter.AbstractFilter
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.sql.SQLException

lateinit var IAB: InfinityAndBeyond
var DB_MANAGER: DBManager? = null

class InfinityAndBeyond : JavaPlugin() {
    override fun onEnable() {
        IAB = this

        saveDefaultConfig()

        registerCommands()
        registerListeners()

        configDatabase()
        shushOrmliteLogger()
        createTables()
    }

    override fun onDisable() {
        DB_MANAGER?.closeConnection()
    }

    private fun registerCommands() {
        val manager = PaperCommandManager(this)
        // listOf().forEach { manager.registerCommand(it) }
    }

    private fun registerListeners() {
        arrayOf(
            ExplorerSetup.Listener,
            WelcomeMessage
        ).forEach { it.register(this) }
    }

    private fun configDatabase() {
        var connectionUrl = config.getString("database.connectionUrl", null)
        if (connectionUrl.isNullOrBlank()) {
            val dbType = config.getString("database.type", "mysql")
            val host = config.getString("database.host", null)
            val user = config.getString("database.user", null)
            val password = config.getString("database.password", null)
            val dbname = config.getString("database.dbname", "infinityandbeyond")

            if (host == null || (dbType.equals("mysql", true) && arrayOf(user, password).any { it == null })) {
                logger.warning("Invalid/incomplete database configuration found! Falling back to file database")
                val file = File(dataFolder, "plugindata.db").absolutePath
                connectionUrl = "jdbc:sqlite:$file"
            } else {
                connectionUrl = DBManager.connectionUrl(dbType!!, host, user, password, dbname!!)
            }
        }
        DB_MANAGER = DBManager(connectionUrl)
        logger.info("Completed database configuration")
    }

    private fun createTables() {
        try {
            DB_MANAGER?.let {
                logger.info("Ensuring tables are setup")
                it.createTables()
                logger.info("Completed tables setup")
            }
        } catch (e: SQLException) {
            logger.severe("Unable to create tables: ${e.message}")
            logger.info("Disabling plugin")
            server.pluginManager.disablePlugin(this)
        }
    }

    private fun shushOrmliteLogger() {
        (LogManager.getRootLogger() as org.apache.logging.log4j.core.Logger).addFilter(object : AbstractFilter() {
            override fun filter(event: LogEvent?): Filter.Result {
                event?.let {
                    if (it.loggerName.startsWith("com.j256.ormlite") && it.level.isLessSpecificThan(Level.WARN)) {
                        return Filter.Result.DENY
                    }
                }
                return Filter.Result.NEUTRAL
            }
        })
    }
}