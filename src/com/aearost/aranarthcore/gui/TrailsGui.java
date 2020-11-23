package com.aearost.aranarthcore.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		Inventory gui = Bukkit.getServer().createInventory(player, 27,
				ChatUtils.translateToColor("&0&lRankup Confirm"));

		// Initialize Items
		ItemStack yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemStack cancel = new ItemStack(Material.RED_CONCRETE);
		ItemStack rankup = new ItemStack(Material.LIME_CONCRETE);

		// Removing name of panes
		ItemMeta yellowPaneMeta = yellowPane.getItemMeta();
		yellowPaneMeta.setDisplayName(" ");
		yellowPane.setItemMeta(yellowPaneMeta);
		ItemMeta blackPaneMeta = blackPane.getItemMeta();
		blackPaneMeta.setDisplayName(" ");
		blackPane.setItemMeta(blackPaneMeta);

		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName(ChatUtils.translateToColor("&c&lCancel Rankup"));
		cancel.setItemMeta(cancelMeta);
		ItemMeta rankupMeta = rankup.getItemMeta();
		rankupMeta
				.setDisplayName(ChatUtils.translateToColor("&a&lRankup to " + rankName + " &a&lfor &6&l" + rankupCost));
		rankup.setItemMeta(rankupMeta);

		// Initialize GUI
		for (int position = 0; position < 27; position++) {
			// Top and bottom lines
			if (position < 9 || position >= 18) {
				gui.setItem(position, blackPane);
			}
		}

		gui.setItem(9, blackPane);
		gui.setItem(10, yellowPane);
		gui.setItem(11, yellowPane);
		gui.setItem(12, cancel);
		gui.setItem(13, yellowPane);
		gui.setItem(14, rankup);
		gui.setItem(15, yellowPane);
		gui.setItem(16, yellowPane);
		gui.setItem(17, blackPane);

		return gui;
	}

}