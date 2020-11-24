package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class TrailArrowClick implements Listener {

	public TrailArrowClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Deals with all clicks of the trails GUI elements.
	 * 
	 * @author Aearost
	 *
	 */
	@EventHandler
	public void onRankClick(final InventoryClickEvent e) {

		if (ChatUtils.stripColor(e.getView().getTitle()).equals("Arrow Trails")) {
			e.setCancelled(true);
			
			int slot = e.getSlot();
			int saintStatus = AranarthPlayerUtils.getPlayer(e.getWhoClicked().getUniqueId()).getSaintStatus();
			
			if (saintStatus == 1) {
				
			} else if (saintStatus == 2) {
				
			} else if (saintStatus == 3) {
				
			}
		}
	}
	

}
