package com.zp4rker.iab.prompts;

import com.zp4rker.iab.api.Explorer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExplorerSetupPrompt extends ValidatingPrompt {
    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext conversationContext) {
        return "Please enter your explorer name";
    }

    @Nullable
    @Override
    protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String s) {
        return new ConfirmationPrompt(s);
    }

    @Nullable
    @Override
    protected String getFailedValidationText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return "Explorer names have to be 35 characters or less";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext conversationContext, @NotNull String s) {
        return !s.isEmpty() && s.length() <= 35;
    }

    private static class ConfirmationPrompt extends MessagePrompt {
        private final String explorerName;

        public ConfirmationPrompt(String explorerName) {
            this.explorerName = explorerName;
        }

        @Nullable
        @Override
        protected Prompt getNextPrompt(@NotNull ConversationContext conversationContext) {
            return Prompt.END_OF_CONVERSATION;
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext conversationContext) {
            Explorer explorer = new Explorer((Player) conversationContext.getForWhom(), explorerName);

            if (!explorer.save()) {
                return "Failed to set your explorer name! Please try again";
            }

            return "Set your explorer name to " + explorerName;
        }
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context) {
        return true;
    }
}
