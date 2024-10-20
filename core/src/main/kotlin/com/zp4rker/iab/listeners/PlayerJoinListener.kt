package com.zp4rker.iab.listeners

import com.zp4rker.iab.LOGGER
import com.zp4rker.iab.PLUGIN
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.prompts.ExplorerSetupPrompt
import org.bukkit.conversations.ConversationFactory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (Explorer.find(event.player) != null) return

        event.joinMessage = null
        val prompt = ExplorerSetupPrompt()
        ConversationFactory(PLUGIN)
            .withFirstPrompt(prompt)
            .addConversationAbandonedListener { e ->
                prompt.explorer?.let {
                    LOGGER.info("Explorer profile setup successfully")
                    (e.context.forWhom as Player).run {
                        customName = it.name
                        setDisplayName(it.name)
                        setPlayerListName(it.name)
                    }
                } ?: run {
                    LOGGER.info("Explorer profile not setup?")
                }
            }.buildConversation(event.player).begin()
    }
}