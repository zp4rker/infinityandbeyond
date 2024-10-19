package com.zp4rker.iab.api

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.zp4rker.iab.DB_MANAGER
import java.sql.SQLException
import java.util.*

@DatabaseTable(tableName = "spaceships")
class Spaceship {
    @DatabaseField(id = true)
    private var name: String = ""

    @DatabaseField(canBeNull = false)
    private var inauguration = Date()

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private lateinit var captain: Explorer

    @DatabaseField(foreign = true)
    private var planet: Planet? = null

    @DatabaseField
    private var location: Coord? = null

    @DatabaseField
    private var health = 100

    @DatabaseField
    private var maxHealth = 100

    @DatabaseField
    private var speed = 1

    @DatabaseField
    private var maxSize = 10

    @DatabaseField
    private var tripCount = 0

    @DatabaseField
    private var flightTime = 0f

    @DatabaseField
    private var distanceTravelled = 0f

    fun save(): Boolean {
        try {
            DB_MANAGER?.saveSpaceship(this) ?: throw SQLException("Database manager not setup!")
            return true
        } catch (e: SQLException) {
            return false
        }
    }

    fun delete(): Boolean {
        try {
            DB_MANAGER?.deleteSpaceship(this) ?: throw SQLException("Database manager not setup!")
            return true
        } catch (e: SQLException) {
            return false
        }
    }

    fun inDatabase() = find(name) != null

    companion object {
        fun find(name: String): Spaceship? {
            return try {
                DB_MANAGER?.findSpaceship(name)
            } catch (e: SQLException) {
                null
            }
        }
    }
}