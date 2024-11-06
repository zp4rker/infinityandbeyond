package com.zp4rker.iab.testing

import co.aikar.commands.PaperCommandManager
import com.zp4rker.iab.testing.commands.Draw
import org.bukkit.plugin.java.JavaPlugin

class IABTesting : JavaPlugin() {
    override fun onEnable() {
        val manager = PaperCommandManager(this)
        listOf(
            Draw
        ).forEach { manager.registerCommand(it) }
    }
}