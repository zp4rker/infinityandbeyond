package com.zp4rker.iab.api

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.zp4rker.iab.DB_MANAGER
import org.bukkit.entity.Player
import java.sql.SQLException
import java.util.UUID

@DatabaseTable(tableName = "explorers")
class Explorer() {
    @DatabaseField(id = true)
    var uuid: UUID = UUID.randomUUID()
    @DatabaseField(unique = true)
    var name: String = ""
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var spaceship: Spaceship = Spaceship()

    @DatabaseField
    var tripCount: Int = 0
    @DatabaseField
    var flightTime: Float = 0f
    @DatabaseField
    var distanceTravelled: Float = 0f

    constructor(player: Player, name: String) : this() {
        this.uuid = player.uniqueId
        this.name = name
    }

    fun save(): Boolean {
        try {
            DB_MANAGER?.saveExplorer(this) ?: throw SQLException("Database manager not setup!")
            return true
        } catch (e: SQLException) {
            return false
        }
    }

    fun delete(): Boolean {
        try {
            DB_MANAGER?.deleteExplorer(this) ?: throw SQLException("Database manager not setup!")
            return true
        } catch (e: SQLException) {
            return false
        }
    }

    fun inDatabase() = find(uuid) != null

    companion object {
        fun find(player: Player) = find(player.uniqueId)

        fun find(uuid: UUID): Explorer? {
            return try {
                DB_MANAGER?.findExplorer(uuid) ?: throw SQLException("Database manager not setup!")
            } catch (e: SQLException) {
                null
            }
        }
    }
}
