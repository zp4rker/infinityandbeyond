package com.zp4rker.iab.prompts

import com.zp4rker.bukkot.api.BlockingFunction
import com.zp4rker.bukkot.extensions.minimessage
import com.zp4rker.bukkot.extensions.plain
import com.zp4rker.bukkot.extensions.runTask
import com.zp4rker.bukkot.listener.Predicate
import com.zp4rker.bukkot.listener.expectBlocking
import com.zp4rker.iab.IAB
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.utils.Lang
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class ExplorerSetup(private val player: Player) {
    @BlockingFunction
    fun run() {
        var name = ""

        val predicate: Predicate<AsyncChatEvent> = {
            var accept = true

            it.isCancelled = true
            val input = it.message().plain()

            if (input.length > 15) {
                player.sendMessage(Lang.getMessage("explorer-setup.error.toolong"))
                accept = false
            }

            if (input.length < 2) {
                player.sendMessage(Lang.getMessage("explorer-setup.error.tooshort"))
                accept = false
            }

            if (input.any { c -> !c.isLetter() && !c.isWhitespace() }) {
                player.sendMessage(Lang.getMessage("explorer-setup.error.invalid"))
                accept = false
            }

            // todo: check for illegal words

            accept
        }

        player.sendMessage(Lang.getMessage("explorer-setup.prompt.first"))
        player.expectBlocking<AsyncChatEvent>(IAB, predicate = predicate) {
            val input = it.message().plain().replaceFirstChar { c -> c.uppercase() }.trim()
            player.sendMessage("<gray><i>$input</i></gray>".minimessage())
            name = input
        }

        player.sendMessage(Lang.getMessage("explorer-setup.prompt.last"))
        player.expectBlocking<AsyncChatEvent>(IAB, predicate = predicate) {
            val input = it.message().plain().replaceFirstChar { c -> c.uppercase() }.trim()
            player.sendMessage("<gray><i>$input</i></gray>".minimessage())
            name += " $input"
        }

        val explorer = Explorer(player, name)
        if (explorer.save()) {
            player.sendMessage(Lang.getMessage("explorer-setup.success", mapOf("%name%" to explorer.name)))
        } else {
            player.sendMessage(Lang.getMessage("explorer-setup.fail"))
        }
    }

    companion object {
        @OptIn(BlockingFunction::class)
        val Listener = object : Listener {
            @EventHandler
            fun onJoin(event: PlayerJoinEvent) {
                if (Explorer.find(event.player) != null) return

                IAB.runTask(async = true) { ExplorerSetup(event.player).run() }
            }
        }
    }
}