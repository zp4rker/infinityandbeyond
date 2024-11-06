package com.zp4rker.iab.testing.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import com.zp4rker.bukkot.extensions.minimessage
import com.zp4rker.iab.buildmode.BuildMode
import org.bukkit.entity.Player

@CommandAlias("binventory")
object Binventory : BaseCommand() {
    @Subcommand("save")
    fun saveInventory(player: Player) {
        if (player.inventory.isEmpty) {
            player.sendMessage("<red>Your inventory is empty!".minimessage())
            return
        }

        if (BuildMode.inventoriesFile.contains("${player.uniqueId}")) {
            player.sendMessage("<red>You already have a saved inventory!".minimessage())
            return
        }

        BuildMode.saveInventory(player)
        player.sendMessage("<green>Inventory saved!".minimessage())
    }

    @Subcommand("restore")
    fun restoreInventory(player: Player) {
        if (!player.inventory.isEmpty) {
            player.sendMessage("<red>Your inventory is not empty!".minimessage())
            return
        }

        if (!BuildMode.inventoriesFile.contains("${player.uniqueId}")) {
            player.sendMessage("<red>You don't have a saved inventory!".minimessage())
            return
        }

        BuildMode.restoreInventory(player)
        player.sendMessage("<green>Inventory restored!".minimessage())
    }
}