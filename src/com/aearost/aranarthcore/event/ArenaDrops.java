package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import com.aearost.aranarthcore.Main;

public class ArenaDrops implements Listener {

	public ArenaDrops(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Prevents all items from being dropped.
	 * 
	 * This includes items dropped by destroying a block, as well as players
	 * dropping items, including on their death. The item will simply not spawn.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onArenaItemDrop(final ItemSpawnEvent e) {
		if (e.getLocation().getWorld().getName().toLowerCase().equals("arena")) {
			e.setCancelled(true);
		}
	}

}
