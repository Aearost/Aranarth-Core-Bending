package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.aearost.aranarthcore.Main;

public class ArenaDrops implements Listener {

	public ArenaDrops(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Prevents a player's inventory from dropping when dying.
	 * 
	 * The teleport back to the arena spawn is handled by Multiverse, and is
	 * permitted to do so by EssentialsSpawn.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onArenaDeath(final EntityDeathEvent e) {
		LivingEntity livingEntity = e.getEntity();
		if (livingEntity.getWorld().getName().toLowerCase().equals("arena")) {
			if (livingEntity instanceof Player) {
				e.getDrops().clear();
			}
		}

	}

}
