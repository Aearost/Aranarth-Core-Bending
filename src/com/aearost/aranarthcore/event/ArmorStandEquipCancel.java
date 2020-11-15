package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import com.aearost.aranarthcore.AranarthCore;

public class ArmorStandEquipCancel implements Listener {

	public ArmorStandEquipCancel(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles preventing a player from equipping items to an invisble armor stand.
	 * 
	 * @param e
	 */
	@EventHandler
	public void manipulate(PlayerArmorStandManipulateEvent e) {
		if (!e.getRightClicked().isVisible()) {
			e.setCancelled(true);
		}
	}

}
