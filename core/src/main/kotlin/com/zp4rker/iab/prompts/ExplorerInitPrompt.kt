package com.zp4rker.iab.prompts

import com.zp4rker.bukkot.extensions.plain
import com.zp4rker.bukkot.listener.expect
import com.zp4rker.iab.PLUGIN
import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.utils.Lang
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.entity.Player

class ExplorerInitPrompt(private val player: Player) {
    fun run() {
        player.sendMessage(Lang.getMessage("explorer-setup.prompt"))

        var name: String? = null
        player.expect<AsyncChatEvent>(PLUGIN, predicate = {
            it.isCancelled = true
            if (it.message().plain().length > 35) {
                player.sendMessage(Lang.getMessage("explorer-setup.error"))
                false
            } else {
                true
            }
        }) {
            name =  it.message().plain()
        }

        if (name == null) return
        val explorer = Explorer(player, name!!)
        if (explorer.save()) {
            val tr = TextReplacementConfig.builder().match("%name%").replacement(explorer.name).build()
            player.sendMessage(Lang.getMessage("explorer-setup.success").replaceText(tr))
        } else {
            player.sendMessage(Lang.getMessage("explorer-setup.fail"))
        }
    }
}