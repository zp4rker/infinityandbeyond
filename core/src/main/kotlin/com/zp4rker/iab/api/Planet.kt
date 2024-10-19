package com.zp4rker.iab.api

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.zp4rker.iab.DB_MANAGER
import com.zp4rker.iab.db.XYCoordPersister
import java.sql.SQLException

@DatabaseTable(tableName = "planets")
class Planet {
    @DatabaseField(id = true)
    private var name: String = ""

    @DatabaseField(persisterClass = XYCoordPersister::class, canBeNull = false)
    private val location: XYCoord = XYCoord(0, 0)

    @DatabaseField(canBeNull = false)
    private val width = 0

    @DatabaseField(canBeNull = false)
    private val height = 0

    fun save(): Boolean {
        try {
            DB_MANAGER?.savePlanet(this) ?: throw SQLException("Database manager not setup!")
            return true
        } catch (e: SQLException) {
            return false
        }
    }

    fun delete(): Boolean {
        try {
            DB_MANAGER?.deletePlanet(this) ?: throw SQLException("Database manager not setup!")
            return true
        } catch (e: SQLException) {
            return false
        }
    }

    fun inDatabase() = find(name) != null

    companion object {
        fun find(name: String): Planet? {
            return try {
                DB_MANAGER?.findPlanet(name) ?: throw SQLException("Database manager not setup!")
            } catch (e: SQLException) {
                null
            }
        }
    }
}