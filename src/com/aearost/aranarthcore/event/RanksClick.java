package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
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
        	double balance = AranarthPlayerUtils.getBalance(player);
        	int rank = AranarthPlayerUtils.getRank(player);
        	
        	int slot = e.getSlot();
        	
        	// Peasant
        	if (slot == 4) {
//        		if (rank == 0) {
//        			player.sendMessage(ChatUtils.translateToColor("&cYou are already a Peasant!"));
//        		} else if (rank > 0) {
//        			player.sendMessage(ChatUtils.translateToColor("&cYou cannot rank yourself down."));
//        		}
        		player.closeInventory();
        	}
        	// Esquire
        	else if (slot == 12) {
        		
        		player.closeInventory();
        		
        	}
        	// Knight
        	else if (slot == 14) {
        		
        		player.closeInventory();
        	}
        	// Baron
        	else if (slot == 20) {
        		
        		player.closeInventory();
        	}
        	// Count
        	else if (slot == 22) {
        		
        		player.closeInventory();
        	}
        	// Duke
        	else if (slot == 24) {
        		
        		player.closeInventory();
        	}
        	// Prince
        	else if (slot == 30) {
        		
        		player.closeInventory();
        	}
        	// King
        	else if (slot == 32) {
        		
        		player.closeInventory();
        	}
        	// Emperor
        	else if (slot == 40) {
        		
        		player.closeInventory();
        	}
        }
		
	}

}
