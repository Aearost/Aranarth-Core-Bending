package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.objects.AranarthPlayer;

/**
 * Provides utility methods to facilitate the formatting of all chat related
 * content.
 * 
 * @author Aearost
 *
 */
public class ChatUtils {

	/**
	 * Allows the formatting of messages to contain Minecraft colors, and begin with
	 * the AranarthCore prefix.
	 * 
	 * @param msg
	 * @return
	 */
	public static String chatMessage(String msg) {
		return ChatColor.translateAlternateColorCodes('&', "&8[&6AranarthCore&8] &r" + msg);
	}

	/**
	 * Allows the formatting of messages to contain Minecraft colors
	 * 
	 * @param msg
	 * @return
	 */
	public static String translateToColor(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	/**
	 * Removes the styling from Strings.
	 * 
	 * @param msg
	 * @return
	 */
	public static String stripColor(String msg) {
		String colorStripped = ChatColor.stripColor(msg);
		while (colorStripped.startsWith("&")) {
			colorStripped = colorStripped.substring(2);
		}
		return colorStripped;
	}

	/**
	 * Handles updating a player's group, sub-group, and prefix according to the
	 * ranks they are.
	 * 
	 * @param offlinePlayer
	 */
	public static void updatePlayerGroupsAndPrefix(OfflinePlayer offlinePlayer) {
		AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(offlinePlayer);
		int rank = aranarthPlayer.getRank();
		boolean isAvatar = aranarthPlayer.getAvatarStatus().equals("current");
		boolean isSaint1 = aranarthPlayer.getSaintStatus() == 1;
		boolean isSaint2 = aranarthPlayer.getSaintStatus() == 2;
		boolean isSaint3 = aranarthPlayer.getSaintStatus() == 3;
		boolean isSaint = isSaint1 || isSaint2 || isSaint3;
		boolean isCouncil1 = aranarthPlayer.getCouncilStatus() == 1;
		boolean isCouncil2 = aranarthPlayer.getCouncilStatus() == 2;
		boolean isCouncil3 = aranarthPlayer.getCouncilStatus() == 3;
		boolean isCouncil = isCouncil1 || isCouncil2 || isCouncil3;

		CommandSender commandSender = Bukkit.getServer().getConsoleSender();

		// Execute in Theia
		Bukkit.dispatchCommand(commandSender, "manselect Theia");

		String prefix = "";

		// First part if applicable
		if (isAvatar) {
			prefix += "&8[&5✦&8] ";
		}

		// Second part if applicable
		if (isCouncil) {
			if (isCouncil1) {
				prefix += "&8[&3۞&8] ";
			} else if (isCouncil2) {
				prefix += "&8[&6۞&8] ";
			} else {
				prefix += "&8[&4۞&8] ";
			}
		}

		// A saint but not a council member
		if (isSaint && !isCouncil) {
			if (isSaint1) {
				prefix += "&8[&b✵&8] ";
			} else if (isSaint2) {
				prefix += "&8[&e✵&8] ";
			} else {
				prefix += "&8[&d✵&8] ";
			}
		}

		String rankName;
		boolean isMalePlayer = aranarthPlayer.getIsMale();
		if (rank == 0) {
			rankName = "Peasant";
			prefix += "&8[&aPeasant&8] &r";
		} else if (rank == 1) {
			rankName = "Esquire";
			prefix += "&d[&aEsquire&d] &r";
		} else if (rank == 2) {
			rankName = "Knight";
			prefix += "&7[&fKnight&7] &r";
		} else if (rank == 3) {
			rankName = "Baron";
			if (isMalePlayer) {
				prefix += "&5[&dBaron&5] &r";
			} else {
				prefix += "&5[&dBaroness&5] &r";
			}
		} else if (rank == 4) {
			rankName = "Count";
			if (isMalePlayer) {
				prefix += "&8[&7Count&8] &r";
			} else {
				prefix += "&8[&7Countess&8] &r";
			}
		} else if (rank == 5) {
			rankName = "Duke";
			if (isMalePlayer) {
				prefix += "&6[&eDuke&6] &r";
			} else {
				prefix += "&6[&eDuchess&6] &r";
			}
		} else if (rank == 6) {
			rankName = "Prince";
			if (isMalePlayer) {
				prefix += "&6[&bPrince&6] &r";
			} else {
				prefix += "&6[&bPrincess&6] &r";
			}
		} else if (rank == 7) {
			rankName = "King";
			if (isMalePlayer) {
				prefix += "&6[&9King&6] &r";
			} else {
				prefix += "&6[&9Queen&6] &r";
			}
		} else {
			rankName = "Emperor";
			if (isMalePlayer) {
				prefix += "&6[&4Emperor&6] &r";
			} else {
				prefix += "&6[&4Empress&6] &r";
			}
		}

		// If they are not a regular player
		if (isCouncil || isSaint || isAvatar || !isMalePlayer) {
			List<SubGroup> subGroups = new ArrayList<>(aranarthPlayer.getSubGroups());
			for (SubGroup subGroup : aranarthPlayer.getSubGroups()) {
				Bukkit.dispatchCommand(commandSender, "manudelsub " + offlinePlayer.getName() + " " + subGroup.name());
				subGroups.remove(subGroup);
			}
			aranarthPlayer.setSubGroups(subGroups);
			
			if (isCouncil) {
				if (isAvatar) {
					Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " Avatar");
					aranarthPlayer.addSubGroup(SubGroup.AVATAR);
				}
				if (isSaint) {
					if (isSaint1) {
						Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " Saint1");
						aranarthPlayer.addSubGroup(SubGroup.SAINT1);
					} else if (isSaint2) {
						Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " Saint2");
						aranarthPlayer.addSubGroup(SubGroup.SAINT2);
					} else {
						Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " Saint3");
						aranarthPlayer.addSubGroup(SubGroup.SAINT3);
					}
				}

				if (isCouncil1) {
					Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " CouncilHelper");
				} else if (isCouncil2) {
					Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " CouncilModerator");
				} else {
					Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " CouncilAdmin");
				}
				Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " " + rankName);
				aranarthPlayer.addSubGroup(SubGroup.valueOf(rankName.toUpperCase()));
			} else if (isSaint) {
				if (isAvatar) {
					Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " Avatar");
					aranarthPlayer.addSubGroup(SubGroup.AVATAR);
				}

				if (isSaint1) {
					Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " Saint1");
				} else if (isSaint2) {
					Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " Saint2");
				} else {
					Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " Saint3");
				}
				Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " " + rankName);
				aranarthPlayer.addSubGroup(SubGroup.valueOf(rankName.toUpperCase()));
			} else if (isAvatar) {
				Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " Avatar");
				Bukkit.dispatchCommand(commandSender, "manuaddsub " + offlinePlayer.getName() + " " + rankName);
				aranarthPlayer.addSubGroup(SubGroup.valueOf(rankName.toUpperCase()));
			} else {
				Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " " + rankName);
			}

			Bukkit.dispatchCommand(commandSender, "manuaddv " + offlinePlayer.getName() + " prefix " + prefix);
		} else {
			Bukkit.dispatchCommand(commandSender, "manudelv " + offlinePlayer.getName() + " prefix");
			Bukkit.dispatchCommand(commandSender, "manuadd " + offlinePlayer.getName() + " " + rankName);
		}
	}

}
