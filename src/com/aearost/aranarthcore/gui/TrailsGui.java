package com.aearost.aranarthcore.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.Trail;
import com.aearost.aranarthcore.utils.TrailNameComparator;
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
		HashMap<Trail, ItemStack> arrows = new HashMap<>();
		AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
		if (aranarthPlayer.getSaintStatus() == 1) {
			arrows = TrailUtils.getSaint1Trails();
			gui = Bukkit.getServer().createInventory(player, 9, ChatUtils.translateToColor("&8&lArrow Trails"));
		} else if (aranarthPlayer.getSaintStatus() == 2) {
			arrows = TrailUtils.getSaint2Trails();
			gui = Bukkit.getServer().createInventory(player, 27, ChatUtils.translateToColor("&8&lArrow Trails"));
		} else if (aranarthPlayer.getSaintStatus() == 3) {
			arrows = TrailUtils.getSaint3Trails();
			gui = Bukkit.getServer().createInventory(player, 45, ChatUtils.translateToColor("&8&lArrow Trails"));
		}

		List<Trail> trailItems = new ArrayList<>(arrows.keySet());
		Collections.sort(trailItems, new TrailNameComparator());

		for (int i = 0; i < trailItems.size(); i++) {
			gui.setItem(i, arrows.get(trailItems.get(i)));
		}

		return gui;
	}

}