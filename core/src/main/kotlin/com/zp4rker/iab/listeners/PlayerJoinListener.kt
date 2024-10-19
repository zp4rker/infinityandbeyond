package com.zp4rker.iab.listeners

import com.zp4rker.iab.PLUGIN
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.prompts.ExplorerSetupPrompt
import org.bukkit.conversations.ConversationFactory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (Explorer.find(event.player) == null) {
            event.joinMessage = null
            ConversationFactory(PLUGIN)
                .withFirstPrompt(ExplorerSetupPrompt())
                .withModality(true)
                .buildConversation(event.player).begin()
        }

        val explorer = Explorer.find(event.player)
        explorer?.let {
            event.player.customName = it.name
            event.player.setDisplayName(it.name)
            event.player.setPlayerListName(it.name)
        }
    }
}