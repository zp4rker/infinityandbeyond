package com.zp4rker.iab.buildmode

import com.zp4rker.bukkot.yaml.YamlFile
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BuildMode : JavaPlugin() {
    override fun onEnable() {
        instance = this
        inventoriesFile = YamlFile(this, "inventories.yml")
    }

    override fun onDisable() {
        inventoriesFile.save()
    }

    companion object {
        lateinit var instance: BuildMode
        lateinit var inventoriesFile: YamlFile

        fun saveInventory(player: Player) {
            for (item in player.inventory.contents) {
                if (item != null) {
                    inventoriesFile.set("${player.uniqueId}.${player.inventory.contents.indexOf(item)}", item)
                }
            }
            inventoriesFile.save()
        }

        fun restoreInventory(player: Player) {
            inventoriesFile.reload()
            if (!inventoriesFile.contains("${player.uniqueId}")) return

            for (key in inventoriesFile.getConfigurationSection("${player.uniqueId}")!!.getKeys(false)) {
                player.inventory.setItem(key.toInt(), inventoriesFile.getItemStack("${player.uniqueId}.$key")!!)
            }
            inventoriesFile.set("${player.uniqueId}", null)
            inventoriesFile.save()
        }
    }
}