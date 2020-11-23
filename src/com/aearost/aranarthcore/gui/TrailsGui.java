package com.aearost.aranarthcore.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.aearost.aranarthcore.utils.ChatUtils;

/**
 * A chest GUI that appears once a user enters the /trails command. This GUI
 * allows the user to easily select and equip an arrowtrail that is available to
 * them with their given rank. Only those they have access to will be listed.
 * 
 * @author Aearost
 *
 */
public class TrailsGui {

	private Player player;
	private Inventory initializedGui;

	public TrailsGui(Player player) {
		this.player = player;
		this.initializedGui = initializeGui(player);
	}

	public void openGui() {
		player.openInventory(initializedGui);
	}

	private Inventory initializeGui(Player player) {
		Inventory gui = Bukkit.getServer().createInventory(player, 4,
				ChatUtils.translateToColor("&8&lArrow Trails"));

		
		
		

		return gui;
	}

}