package com.aearost.aranarthcore.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.TrailUtils;

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
		Inventory gui = null;
		List<ItemStack> arrows = new ArrayList<>();
//		AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
//		if (aranarthPlayer.getSaintStatus() == 1) {
//			arrows = TrailUtils.getSaint1Trails();
//			gui = Bukkit.getServer().createInventory(player, 18, ChatUtils.translateToColor("&8&lArrow Trails"));
//		} else if (aranarthPlayer.getSaintStatus() == 2) {
//			arrows = TrailUtils.getSaint2Trails();
//			gui = Bukkit.getServer().createInventory(player, 36, ChatUtils.translateToColor("&8&lArrow Trails"));
//		} else if (aranarthPlayer.getSaintStatus() == 3) {
			arrows = TrailUtils.getSaint3Trails();
			gui = Bukkit.getServer().createInventory(player, 54, ChatUtils.translateToColor("&8&lArrow Trails"));
//		}

		for (int i = 0; i < arrows.size(); i++) {
			gui.setItem(i, arrows.get(i));
		}

		return gui;
	}

}