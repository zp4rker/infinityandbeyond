package com.zp4rker.iab.listeners

import com.zp4rker.bukkot.extensions.runTask
import com.zp4rker.iab.LOGGER
import com.zp4rker.iab.PLUGIN
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.prompts.ExplorerInitPrompt
import com.zp4rker.iab.utils.MM
import org.bukkit.conversations.ConversationFactory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (Explorer.find(event.player) != null) return

        event.joinMessage(null)
        PLUGIN.runTask(async = true) { ExplorerInitPrompt(event.player).run() }
    }
}