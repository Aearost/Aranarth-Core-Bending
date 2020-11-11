package com.aearost.aranarthcore.event;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.gui.RankupGui;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;

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
			int rank = AranarthPlayerUtils.getRank(player);

			int slot = e.getSlot();
			boolean isRankup = false;
			boolean isClickedRankSameAsCurrent = false;
			boolean isClickedRankLowerThanCurrent = false;
			boolean isClickedRankHigherThanCurrent = false;

			String[] maleRanks = new String[] { "&a&lPeasant", "&d&lEsquire", "&7&lKnight", "&5&lBaron", "&8&lCount",
					"&6&lDuke", "&b&lPrince", "&9&lKing", "&4&lEmperor" };
			String[] femaleRanks = new String[] { "&a&lPeasant", "&d&lEsquire", "&7&lKnight", "&5&lBaroness",
					"&8&lCountess", "&6&lDuchess", "&b&lPrincess", "&9&lQueen", "&4&lEmpress" };
			String[] rankupCosts = new String[] { "FREE", "$250", "$750", "$1,500", "$3,000", "$7,500", "$12,500",
					"$30,000", "$50,000" };
			int[] positions = new int[] { 4, 12, 14, 20, 22, 24, 30, 32, 40 };
			int clickedPosition = 0;

			while (clickedPosition < positions.length) {

				if (slot == positions[clickedPosition]) {

					// If they clicked on the same one
					if (rank == clickedPosition) {
						isClickedRankSameAsCurrent = true;
						break;
					}
					// If they clicked on a previous rank
					else if (rank > clickedPosition) {
						isClickedRankLowerThanCurrent = true;
						break;
					}
					// If they clicked on a rank ahead of the rank above their own
					else if (clickedPosition > rank + 1) {
						isClickedRankHigherThanCurrent = true;
						break;
					}
					// If they clicked on the rank above their own
					else if (clickedPosition == rank + 1) {
						isRankup = true;
						break;
					}

				}
				clickedPosition++;
			}
			boolean isMalePlayer = AranarthPlayerUtils.getPlayer(player).getIsMale();
			String aOrAn = "a";
			if (slot == 12 || slot == 40) {
				aOrAn = "an";
			}

			// Feedback Messages and Functionality
			if (isClickedRankSameAsCurrent) {
				if (isMalePlayer) {
					player.sendMessage(ChatUtils
							.translateToColor("&cYou are already " + aOrAn + " " + maleRanks[clickedPosition] + "&c!"));
				} else {
					player.sendMessage(ChatUtils.translateToColor(
							"&cYou are already " + aOrAn + " " + femaleRanks[clickedPosition] + "&c!"));
				}
				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 0.5F, 0.5F);
				player.closeInventory();
			} else if (isClickedRankLowerThanCurrent) {
				if (isMalePlayer) {
					player.sendMessage(ChatUtils.translateToColor(
							"&cYou cannot rank back down to " + aOrAn + " " + maleRanks[clickedPosition] + "&c!"));
				} else {
					player.sendMessage(ChatUtils.translateToColor(
							"&cYou cannot rank back down to " + aOrAn + " " + femaleRanks[clickedPosition] + "&c!"));
				}
				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 0.5F, 0.5F);
				player.closeInventory();
			} else if (isClickedRankHigherThanCurrent) {
				if (isMalePlayer) {
					player.sendMessage(
							ChatUtils.translateToColor("&cYou must rankup to " + maleRanks[rank + 1] + " &cfirst!"));
				} else {
					player.sendMessage(
							ChatUtils.translateToColor("&cYou must rankup to " + femaleRanks[rank + 1] + " &cfirst!"));
				}
				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 0.8F, 0.5F);
				player.closeInventory();
			} else if (isRankup) {
				String rankupCost = rankupCosts[clickedPosition];

				String rankName = maleRanks[clickedPosition];
				if (!isMalePlayer) {
					rankName = femaleRanks[clickedPosition];
				}
				new RankupGui(player, rankName, rankupCost).openGui();
			}

		} else if (ChatUtils.stripColor(e.getView().getTitle()).equals("Rankup Confirm")) {
			e.setCancelled(true);
			int slot = e.getSlot();
			// Rankup
			if (slot == 14) {
				Player player = (Player) e.getWhoClicked();
				double balance = AranarthPlayerUtils.getBalance(player);
				String clickedItem = e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName();
				String[] parts = clickedItem.split(" ");

				String priceWithoutDollarSign = ChatUtils.stripColor(parts[parts.length - 1]).substring(1);
				String priceWithoutCommas = priceWithoutDollarSign.replaceAll(",", "");
				double price = Double.parseDouble(priceWithoutCommas);

				if (balance >= price) {
					String rankDisplay = clickedItem.split(" ")[2];
					String aOrAn = "a";

					NumberFormat formatter = NumberFormat.getCurrencyInstance();
					PersistenceUtils.logTransaction(player.getName() + " (" + formatter.format(balance) + ") spent "
							+ price + " and has ranked up to " + rankDisplay);
					AranarthPlayerUtils.setBalance(player, balance - price);
					AranarthPlayerUtils.setRank(player, AranarthPlayerUtils.getRank(player) + 1);

					ChatUtils.updatePlayerPrefixAndRank(player);
					if (ChatUtils.stripColor(rankDisplay).equals("Esquire")
							|| ChatUtils.stripColor(rankDisplay).equals("Emperor")) {
						aOrAn = "an";
					}
					if (AranarthPlayerUtils.getRank(player) < 6) {
						player.sendMessage(
								ChatUtils.chatMessage("&7You have become " + aOrAn + " " + rankDisplay + "&7!"));
					} else {
						Bukkit.broadcastMessage(ChatUtils.chatMessage(
								"&e" + player.getName() + " &7has become " + aOrAn + " " + rankDisplay + "&7!"));
					}
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
					player.closeInventory();
				} else {
					player.sendMessage(ChatUtils.chatMessage("&cYou do not have enough money to rankup!"));
				}
			}
			// Cancel
			else if (slot == 12) {
				Player player = (Player) e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 0.8F, 0.5F);
				e.getWhoClicked().closeInventory();
			}
		}

	}

}
