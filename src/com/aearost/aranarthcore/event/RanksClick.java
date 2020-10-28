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
			String clickedName = e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName();

			boolean isRankup = false;
			boolean isClickedRankSameAsCurrent = false;
			boolean isClickedRankLowerThanCurrent = false;
			boolean isClickedRankHigherThanCurrent = false;

			String[] ranks = new String[] { "&a&l&nPeasant", "&d&l&nEsquire", "&7&l&nKnight", "&5&l&nBaron",
					"&8&l&nCount", "&6&l&nDuke", "&b&l&nPrince", "&9&l&nKing", "&4&l&nEmperor" };
			int[] positions = new int[] { 4, 12, 14, 20, 22, 24, 30, 32, 40 };
			int position = 0;

			while (position < positions.length) {
				if (slot == positions[position]) {
					// If they clicked on the same one
					if (rank == position) {
						isClickedRankSameAsCurrent = true;
						break;
					}
					// If they clicked on a previous rank
					else if (rank > position) {
						isClickedRankLowerThanCurrent = true;
						break;
					}
					// If they clicked on a rank ahead of the rank above their own
					else if (position < rank + 1) {
						isClickedRankHigherThanCurrent = true;
					}
					// If they clicked on the rank above their own
					else if (position == rank + 1) {
						isRankup = true;
						break;
					}
				}
				position++;
			}
			
			// Feedback Messages and Functionality
			if (isClickedRankSameAsCurrent) {
				player.sendMessage(ChatUtils.translateToColor("&cYou are already a " + clickedName + "&c!"));
			} else if (isClickedRankLowerThanCurrent) {
				player.sendMessage(
						ChatUtils.translateToColor("&cYou cannot rank back down to a " + clickedName + "&c!"));
				return;
			} else if (isClickedRankHigherThanCurrent) {
				player.sendMessage(ChatUtils.translateToColor("&cYou must rankup to " + ranks[rank + 1] + " &cfirst!"));
			} else if (isRankup) {
				// Make new Inventory holder
				// "Are you sure you would like to rankup?"
				// Single-line GUI with a lime and red wool named "&a&lRankup to + ranks[position] + for x money" and "&c&lCancel Rankup"
			}

		}

	}

}
