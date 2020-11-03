package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.aearost.aranarthcore.AranarthCore;

public class ShopCreate implements Listener {
	
	public ShopCreate(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles the creation of a shop.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopSignLeftClick(final SignChangeEvent e) {
		// Do stuff
	}
}
