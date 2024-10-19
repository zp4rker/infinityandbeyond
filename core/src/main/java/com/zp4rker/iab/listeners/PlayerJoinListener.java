package com.zp4rker.iab.listeners;

import com.zp4rker.iab.IABCore;
import com.zp4rker.iab.api.Explorer;
import com.zp4rker.iab.prompts.ExplorerSetupPrompt;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Explorer.find(event.getPlayer()) == null) {
            event.setJoinMessage(null);
            new ConversationFactory(IABCore.INST)
                    .withFirstPrompt(new ExplorerSetupPrompt())
                    .withModality(true)
                    .withLocalEcho(false)
                    .buildConversation(event.getPlayer()).begin();
        }

        Explorer explorer = Explorer.find(event.getPlayer());
        if (explorer != null) {
            event.getPlayer().setCustomName(explorer.getName());
            event.getPlayer().setDisplayName(explorer.getName());
            event.getPlayer().setPlayerListName(explorer.getName());
        }
    }
}
