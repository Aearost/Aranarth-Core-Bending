package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aearost.aranarthcore.Main;
import com.aearost.aranarthcore.utils.ChatUtils;

public class RanksClick implements Listener {

	public RanksClick(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles clicking of 
	 * 
	 * @param e
	 */
	@EventHandler
	public void onRankClick(final InventoryClickEvent e) {

        if (ChatUtils.stripColor(e.getView().getTitle()).equals("Aranarth Ranks")) {
        	e.setCancelled(true);
        	
        	int slot = e.getSlot();
        	
        	// Peasant
        	if (slot == 4) {
        		
        	}
        	// Esquire
        	else if (slot == 12) {
        		
        	}
        	// Knight
        	else if (slot == 14) {
        		
        	}
        	// Baron
        	else if (slot == 20) {
        		
        	}
        	// Count
        	else if (slot == 22) {
        		
        	}
        	// Duke
        	else if (slot == 24) {
        		
        	}
        	// Prince
        	else if (slot == 30) {
        		
        	}
        	// King
        	else if (slot == 32) {
        		
        	}
        	// Emperor
        	else if (slot == 40) {
        		
        	} else {
        		Bukkit.getLogger().info(ChatUtils.translateToColor("&4REEEEEEEEEEEEEEEEEEEEEEE @toku"));
        	}
        }
		
	}

}
