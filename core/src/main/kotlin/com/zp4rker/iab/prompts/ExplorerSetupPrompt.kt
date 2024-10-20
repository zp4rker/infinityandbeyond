package com.zp4rker.iab.prompts

import com.zp4rker.iab.api.Explorer
import com.zp4rker.iab.utils.Lang
import com.zp4rker.iab.utils.legacy
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.ValidatingPrompt
import org.bukkit.entity.Player

class ExplorerSetupPrompt : ValidatingPrompt() {
    var explorer: Explorer? = null

    override fun getPromptText(ctx: ConversationContext): String {
        return Lang.getMessage("explorer-setup.prompt").legacy()
    }

    override fun getFailedValidationText(context: ConversationContext, invalidInput: String): String {
        return Lang.getMessage("explorer-setup.error").legacy()
    }

    override fun isInputValid(ctx: ConversationContext, input: String) = input.length <= 35
    override fun acceptValidatedInput(ctx: ConversationContext, input: String): Prompt {
        explorer = Explorer(ctx.forWhom as Player, input)

        return if (explorer?.save() == true) {
            var message = Lang.getMessage("explorer-setup.success")
            val tr = TextReplacementConfig.builder().matchLiteral("%name%").replacement(input).build()
            message = message.replaceText(tr)
            SingleMessagePrompt(message.legacy())
        } else {
            SingleMessagePrompt(Lang.getMessage("explorer-setup.fail").legacy())
        }
    }

    override fun blocksForInput(ctx: ConversationContext) = true
}