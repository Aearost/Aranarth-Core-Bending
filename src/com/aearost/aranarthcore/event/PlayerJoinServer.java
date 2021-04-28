package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;

public class PlayerJoinServer implements Listener {

	public PlayerJoinServer(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Adds a new entry to the players HashMap if the player is not being tracked.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!AranarthPlayerUtils.hasPlayedBefore(player)) {
			AranarthPlayerUtils.addPlayer(player.getUniqueId(), new AranarthPlayer(player.getName(), 0, true, 50.00, 0));
		}
		// If the player changed their username
		else if (!AranarthPlayerUtils.getUsername(player).equals(player.getName())) {
			AranarthPlayerUtils.setUsername(player);
		}
	}
}
