package com.zp4rker.iab.listeners

import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.utils.Lang
import net.kyori.adventure.text.TextReplacementConfig
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
            val tr0 = TextReplacementConfig.builder().matchLiteral("%name%").replacement(explorer.name).build()
            val tr1 = TextReplacementConfig.builder().matchLiteral("%planet%").replacement("Origin").build()
            Lang.getMessages("welcome-message.regular").forEach {
                event.player.sendMessage(it.replaceText(tr0).replaceText(tr1))
            }
        }
    }
}