package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aearost.aranarthcore.AranarthCore;

public class ShopSignClick implements Listener {

	public ShopSignClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles clicking of a shop sign.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopSignLeftClick(final PlayerInteractEvent e) {

		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (isWallSign(e.getClickedBlock().getType())) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				// if (sign.getLine(0) is in the hashmap) { ... }
			}
		}

	}

	private boolean isWallSign(Material clickedBlock) {
		return clickedBlock == Material.ACACIA_WALL_SIGN || clickedBlock == Material.BIRCH_WALL_SIGN
				|| clickedBlock == Material.CRIMSON_WALL_SIGN || clickedBlock == Material.DARK_OAK_WALL_SIGN
				|| clickedBlock == Material.JUNGLE_WALL_SIGN || clickedBlock == Material.OAK_WALL_SIGN
				|| clickedBlock == Material.SPRUCE_WALL_SIGN || clickedBlock == Material.WARPED_WALL_SIGN;
	}

}
