package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aearost.aranarthcore.Main;
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;

public class PlayerJoinServer implements Listener {

	public PlayerJoinServer(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Adds a new entry to the players HashMap.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore()) {
			AranarthPlayerUtils.addPlayer(e.getPlayer().getName(), new AranarthPlayer("peasant", true));
		}
	}
}
