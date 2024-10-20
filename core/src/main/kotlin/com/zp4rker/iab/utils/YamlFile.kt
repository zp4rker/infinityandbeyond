package com.zp4rker.iab.utils

import com.zp4rker.iab.PLUGIN
import org.bukkit.configuration.Configuration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class YamlFile(fileName: String, copyResource: Boolean = true) {
    private val file = File(PLUGIN.dataFolder, fileName)
    private var config = YamlConfiguration()

    init {
        if (copyResource) {
            PLUGIN.saveResource(fileName, false)
        }

        reload()
    }

    val yaml: Configuration
        get() = config.root!!

    fun save() = config.save(file)

    fun reload() {
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file)
        } else {
            val stream = PLUGIN.getResource(file.name)
            if (stream != null) {
                config.load(stream.reader())
            }
        }
    }
}