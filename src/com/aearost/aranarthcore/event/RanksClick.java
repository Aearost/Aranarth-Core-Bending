package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.utils.ChatUtils;

public class RanksClick implements Listener {

	public RanksClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles clicking of a slot in the Aranarth Ranks GUI.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onRankClick(final InventoryClickEvent e) {
		
        if (ChatUtils.stripColor(e.getView().getTitle()).equals("Aranarth Ranks")) {
        	e.setCancelled(true);
        	
        	Player player = (Player) e.getWhoClicked();
        	int slot = e.getSlot();
        	
        	// Peasant
        	if (slot == 4) {
        		Bukkit.getLogger().info("Peasant");
        		player.closeInventory();
        	}
        	// Esquire
        	else if (slot == 12) {
        		Bukkit.getLogger().info("Esquire");
        		player.closeInventory();
        	}
        	// Knight
        	else if (slot == 14) {
        		Bukkit.getLogger().info("Knight");
        		player.closeInventory();
        	}
        	// Baron
        	else if (slot == 20) {
        		Bukkit.getLogger().info("Baron");
        		player.closeInventory();
        	}
        	// Count
        	else if (slot == 22) {
        		Bukkit.getLogger().info("Count");
        		player.closeInventory();
        	}
        	// Duke
        	else if (slot == 24) {
        		Bukkit.getLogger().info("Duke");
        		player.closeInventory();
        	}
        	// Prince
        	else if (slot == 30) {
        		Bukkit.getLogger().info("Prince");
        		player.closeInventory();
        	}
        	// King
        	else if (slot == 32) {
        		Bukkit.getLogger().info("King");
        		player.closeInventory();
        	}
        	// Emperor
        	else if (slot == 40) {
        		Bukkit.getLogger().info("Emperor");
        		player.closeInventory();
        	}
        }
		
	}

}
