package com.zp4rker.iab.prompts

import com.zp4rker.iab.LOGGER
import com.zp4rker.iab.api.Explorer
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.ValidatingPrompt
import org.bukkit.entity.Player

class ExplorerSetupPrompt : ValidatingPrompt() {
    var explorer: Explorer? = null

    override fun getPromptText(ctx: ConversationContext): String = "Please enter your explorer name"
    override fun getFailedValidationText(context: ConversationContext, invalidInput: String): String {
        return "Explorer names have to be 35 characters or less"
    }

    override fun isInputValid(ctx: ConversationContext, input: String) = input.length <= 35
    override fun acceptValidatedInput(ctx: ConversationContext, input: String): Prompt {
        explorer = Explorer(ctx.forWhom as Player, input)

        return if (explorer?.save() == true) {
            SingleMessagePrompt("Set your explorer name to $input", postPromptFunc = {
                LOGGER.info("Set explorer name for ${(it.forWhom as Player).name}")
            })
        } else {
            SingleMessagePrompt("Failed to set your explorer name! Please try again")
        }
    }

    override fun blocksForInput(ctx: ConversationContext) = true
}