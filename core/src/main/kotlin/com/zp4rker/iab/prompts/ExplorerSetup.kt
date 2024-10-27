package com.zp4rker.iab.prompts

import com.zp4rker.bukkot.api.BlockingFunction
import com.zp4rker.bukkot.extensions.minimessage
import com.zp4rker.bukkot.extensions.plain
import com.zp4rker.bukkot.extensions.runTask
import com.zp4rker.bukkot.listener.Predicate
import com.zp4rker.bukkot.listener.expect
import com.zp4rker.bukkot.listener.expectBlocking
import com.zp4rker.bukkot.listener.on
import com.zp4rker.iab.PLUGIN
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.utils.Lang
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.entity.Player
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
        player.expectBlocking<AsyncChatEvent>(PLUGIN, predicate = predicate) {
            val input = it.message().plain().replaceFirstChar { c -> c.uppercase() }.trim()
            player.sendMessage("<gray><i>$input</i></gray>".minimessage())
            name = input
        }

        player.sendMessage(Lang.getMessage("explorer-setup.prompt.last"))
        player.expectBlocking<AsyncChatEvent>(PLUGIN, predicate = predicate) {
            val input = it.message().plain().replaceFirstChar { c -> c.uppercase() }.trim()
            player.sendMessage("<gray><i>$input</i></gray>".minimessage())
            name += " $input"
        }

        val explorer = Explorer(player, name)
        if (explorer.save()) {
            val tr = TextReplacementConfig.builder().match("%name%").replacement(explorer.name).build()
            player.sendMessage(Lang.getMessage("explorer-setup.success").replaceText(tr))
        } else {
            player.sendMessage(Lang.getMessage("explorer-setup.fail"))
        }
    }

    companion object {
        @OptIn(BlockingFunction::class)
        fun startListening() {
            PLUGIN.on<PlayerJoinEvent>(predicate = { Explorer.find(it.player) == null }) {
                PLUGIN.runTask(async = true) { ExplorerSetup(it.player).run() }
            }
        }
    }
}