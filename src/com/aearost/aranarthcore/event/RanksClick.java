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
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.event.PlayerChangeSubElementEvent;

public class RanksClick implements Listener {

	public RanksClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Deals with all clicks of the ranks GUI elements. This includes both the
	 * Aranarth Ranks GUI, as well as the Rankup Confirm GUI.
	 * 
	 * @author Aearost
	 *
	 */
	@EventHandler
	public void onRankClick(final InventoryClickEvent e) {

		if (ChatUtils.stripColor(e.getView().getTitle()).equals("Aranarth Ranks")) {
			e.setCancelled(true);

			Player player = (Player) e.getWhoClicked();
			int rank = AranarthPlayerUtils.getRank(player);

			int slot = e.getSlot();
			
			if (slot == 47) {
				player.sendMessage(ChatUtils.translateToColor("&3Saint I: &bhttps://aranarth.craftingstore.net/package/493265"));
				player.closeInventory();
				return;
			} else if (slot == 49) {
				player.sendMessage(ChatUtils.translateToColor("&6Saint II: &ehttps://aranarth.craftingstore.net/package/493266"));
				player.closeInventory();
				return;
			} else if (slot == 51) {
				player.sendMessage(ChatUtils.translateToColor("&4Saint III: &chttps://aranarth.craftingstore.net/package/493280"));
				player.closeInventory();
				return;
			}
			
			boolean isRankup = false;
			boolean isClickedRankSameAsCurrent = false;
			boolean isClickedRankLowerThanCurrent = false;
			boolean isClickedRankHigherThanCurrent = false;

			String[] maleRanks = new String[] { "&a&lPeasant", "&d&lEsquire", "&7&lKnight", "&5&lBaron", "&8&lCount",
					"&6&lDuke", "&b&lPrince", "&9&lKing", "&4&lEmperor" };
			String[] femaleRanks = new String[] { "&a&lPeasant", "&d&lEsquire", "&7&lKnight", "&5&lBaroness",
					"&8&lCountess", "&6&lDuchess", "&b&lPrincess", "&9&lQueen", "&4&lEmpress" };
			String[] rankupCosts = new String[] { "FREE", "$250", "$1,250", "$5,000", "$10,000", "$25,000", "$100,000",
					"$500,000", "$2,500,000" };
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

					ChatUtils.updatePlayerGroupsAndPrefix(player);
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

					BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);
					if (bendingPlayer == null) {
						GeneralMethods.createBendingPlayer(player.getUniqueId(), player.getName());
						bendingPlayer = BendingPlayer.getBendingPlayer(player);
					}
					AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
					int rank = aranarthPlayer.getRank();
					
					if (!aranarthPlayer.getAvatarStatus().equals("current")) {
						if (bendingPlayer.getElements().size() > 0 && bendingPlayer.getElements().get(0) == Element.EARTH) {
							if (rank == 2) {
								bendingPlayer.addSubElement(Element.METAL);
								player.sendMessage(ChatUtils.chatMessage("&2You are now a Metalbender!"));
								Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
										null, player, SubElement.METAL,
										PlayerChangeSubElementEvent.Result.ADD));
							}
							if (rank == 7) {
								bendingPlayer.addSubElement(Element.LAVA);
								player.sendMessage(ChatUtils.chatMessage("&2You are now a Lavabender!"));
								Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
										null, player, SubElement.LAVA,
										PlayerChangeSubElementEvent.Result.ADD));
							}
						} else if (bendingPlayer.getElements().size() > 0 && bendingPlayer.getElements().get(0) == Element.FIRE) {
							if (rank == 6) {
								bendingPlayer.addSubElement(Element.LIGHTNING);
								Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
										null, player, SubElement.LIGHTNING,
										PlayerChangeSubElementEvent.Result.ADD));
								player.sendMessage(ChatUtils.chatMessage("&4You are now a Lightningbender!"));
							}
							if (rank == 8) {
								bendingPlayer.addSubElement(Element.COMBUSTION);
								Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
										null, player, SubElement.COMBUSTION,
										PlayerChangeSubElementEvent.Result.ADD));
								player.sendMessage(ChatUtils.chatMessage("&4As well as a Combustionbender!"));
							}
						} else if (bendingPlayer.getElements().size() > 0 && bendingPlayer.getElements().get(0) == Element.WATER) {
							if (rank == 1) {
								bendingPlayer.addSubElement(Element.HEALING);
								Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
										null, player, SubElement.HEALING,
										PlayerChangeSubElementEvent.Result.ADD));
								player.sendMessage(ChatUtils.chatMessage("&3You are now a Healer!"));
								bendingPlayer.addSubElement(Element.PLANT);
								Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
										null, player, SubElement.PLANT,
										PlayerChangeSubElementEvent.Result.ADD));
								player.sendMessage(ChatUtils.chatMessage("&3You are now a Plantbender!"));
							}
						}
						GeneralMethods.saveSubElements(bendingPlayer);
					}
					
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
					player.closeInventory();
				} else {
					player.sendMessage(ChatUtils.chatMessage("&cYou do not have enough money to rankup!"));
					player.closeInventory();
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
