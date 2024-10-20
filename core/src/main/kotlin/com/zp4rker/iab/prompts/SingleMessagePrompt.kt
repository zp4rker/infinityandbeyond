package com.zp4rker.iab.prompts

import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.MessagePrompt
import org.bukkit.conversations.Prompt

class SingleMessagePrompt(
    private val promptText: String,
    private val postPromptFunc: (ConversationContext) -> Unit = {},
    private val prePromptFunc: (ConversationContext) -> Unit = {}
) : MessagePrompt() {

    override fun getPromptText(ctx: ConversationContext): String {
        prePromptFunc.invoke(ctx)
        return promptText
    }

    override fun getNextPrompt(ctx: ConversationContext): Prompt? {
        postPromptFunc.invoke(ctx)
        return Prompt.END_OF_CONVERSATION
    }
}