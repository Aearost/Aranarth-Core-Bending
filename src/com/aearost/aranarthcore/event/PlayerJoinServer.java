package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
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
		if (!AranarthPlayerUtils.hasPlayedBefore(e.getPlayer())) {
			AranarthPlayerUtils.addPlayer(e.getPlayer().getName(), new AranarthPlayer(0, true, 0.00));
		}
	}
}
