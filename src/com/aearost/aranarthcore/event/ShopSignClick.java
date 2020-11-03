package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.utils.AranarthShopUtils;

public class ShopSignClick implements Listener {

	public ShopSignClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles selling items through shop signs.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopSignLeftClick(final PlayerInteractEvent e) {

//		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
//			if (AranarthShopUtils.isWallSign(e.getClickedBlock().getType())) {
//				Sign sign = (Sign) e.getClickedBlock().getState();
//				
//				// if (sign.getLine(0) is in the hashmap) { ... }
//			}
//		}
	}
	
	/**
	 * Handles buying items through shop signs.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopSignRightClick(final PlayerInteractEvent e) {

//		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//			if (AranarthShopUtils.isWallSign(e.getClickedBlock().getType())) {
//				Sign sign = (Sign) e.getClickedBlock().getState();
//				
//				// if (sign.getLine(0) is in the hashmap) { ... }
//			}
//		}
	}
}
