package com.zp4rker.iab.listeners

import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.utils.Lang
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class WelcomeMessage : Listener {
    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        event.joinMessage(null)

        val explorer = Explorer.find(event.player)
        if (explorer == null) {
            event.player.sendMessage(Lang.getMessage("welcome-message.newplayer"))
        } else {
            Lang.getMessages(
                "welcome-message.regular", mapOf(
                    "%name%" to explorer.name,
                    "%planet%" to "Origin"
                )
            ).forEach {
                event.player.sendMessage(it)
            }
        }
    }
}