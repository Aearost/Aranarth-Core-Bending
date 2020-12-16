package com.aearost.aranarthcore.utils;

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
		boolean isSaint = aranarthPlayer.getSaintStatus() > 0;
		boolean isCouncil = aranarthPlayer.getCouncilStatus() > 0;

		CommandSender commandSender = Bukkit.getServer().getConsoleSender();

		// Execute in Aldara
		Bukkit.dispatchCommand(commandSender, "manselect world");

		String prefix = "";

		// First part if applicable
		if (isAvatar) {
			prefix += "&8[&5✦&8] ";
		}
		
		// A saint
		if (isSaint) {
			if (aranarthPlayer.getSaintStatus() == 1) {
				prefix += "&8[&b✵&8] ";
			} else if (aranarthPlayer.getSaintStatus() == 2) {
				prefix += "&8[&e✵&8] ";
			} else {
				prefix += "&8[&d✵&8] ";
			}
		}

		// Second part if applicable
		if (isCouncil) {
			if (aranarthPlayer.getCouncilStatus() == 1) {
				prefix += "&8[&3۞&8] ";
			} else if (aranarthPlayer.getCouncilStatus() == 2) {
				prefix += "&8[&6۞&8] ";
			} else {
				prefix += "&8[&4۞&8] ";
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
		
		List<SubGroup> subGroups = aranarthPlayer.getSubGroups();
		
		if (subGroups.contains(SubGroup.AVATAR) && !isAvatar) {
			Bukkit.dispatchCommand(commandSender, "manudelsub " + offlinePlayer.getName() + " Avatar");
			subGroups.remove(SubGroup.AVATAR);
		}

		SubGroup subGroupToRemove = null;
		for (SubGroup sub : subGroups) {
			if (subGroupToRemove == null) {
				subGroupToRemove = sub;
			} else {
				if (sub.compareTo(subGroupToRemove) <= 0) {
					subGroupToRemove = sub;
				}
			}
		}
		if (subGroupToRemove != null) {
			subGroups.remove(subGroupToRemove);
			Bukkit.dispatchCommand(commandSender, "manudelsub " + offlinePlayer.getName() + " " + subGroupToRemove.name());
		}

		if (isCouncil) {
			aranarthPlayer = updateGroupsAsCouncil(commandSender, aranarthPlayer, isSaint, isAvatar);
		} else if (isSaint) {
			aranarthPlayer = updateGroupsAsSaint(commandSender, aranarthPlayer, isAvatar);
		} else if (isAvatar) {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " Avatar");
		}

		if (isCouncil || isSaint || isAvatar || !isMalePlayer) {
			if (isCouncil || isSaint || isAvatar) {
				if (!subGroups.contains(SubGroup.valueOf(rankName.toUpperCase()))) {
					Bukkit.dispatchCommand(commandSender,
							"manuaddsub " + aranarthPlayer.getUsername() + " " + rankName);
					subGroups.add(SubGroup.valueOf(rankName.toUpperCase()));
				}
			} else {
				Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " " + rankName);
			}
			Bukkit.dispatchCommand(commandSender, "manuaddv " + aranarthPlayer.getUsername() + " prefix " + prefix);
		} else {
			Bukkit.dispatchCommand(commandSender, "manudelv " + aranarthPlayer.getUsername() + " prefix");
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " " + rankName);
		}
		// Updates with new sub-groups
		aranarthPlayer.setSubGroups(subGroups);
		AranarthPlayerUtils.addPlayer(AranarthPlayerUtils.getUUID(aranarthPlayer.getUsername()), aranarthPlayer);
	}

	private static AranarthPlayer updateGroupsAsCouncil(CommandSender commandSender, AranarthPlayer aranarthPlayer,
			boolean isSaint, boolean isAvatar) {
		List<SubGroup> subGroups = aranarthPlayer.getSubGroups();		
		// Sub-groups
		if (isSaint) {
			if (aranarthPlayer.getSaintStatus() == 1 && !subGroups.contains(SubGroup.SAINT1)) {
				Bukkit.dispatchCommand(commandSender, "manuaddsub " + aranarthPlayer.getUsername() + " Saint1");
				aranarthPlayer.addSubGroup(SubGroup.SAINT1);
			} else if (aranarthPlayer.getSaintStatus() == 2 && !subGroups.contains(SubGroup.SAINT2)) {
				Bukkit.dispatchCommand(commandSender, "manuaddsub " + aranarthPlayer.getUsername() + " Saint2");
				aranarthPlayer.addSubGroup(SubGroup.SAINT2);
			} else if (aranarthPlayer.getSaintStatus() == 3 && !subGroups.contains(SubGroup.SAINT3)) {
				Bukkit.dispatchCommand(commandSender, "manuaddsub " + aranarthPlayer.getUsername() + " Saint3");
				aranarthPlayer.addSubGroup(SubGroup.SAINT3);
			}
		}
		if (isAvatar && !subGroups.contains(SubGroup.AVATAR)) {
			Bukkit.dispatchCommand(commandSender, "manuaddsub " + aranarthPlayer.getUsername() + " Avatar");
			aranarthPlayer.addSubGroup(SubGroup.AVATAR);
		}

		// Primary group
		if (aranarthPlayer.getCouncilStatus() == 1) {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " CouncilHelper");
		} else if (aranarthPlayer.getCouncilStatus() == 2) {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " CouncilModerator");
		} else {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " CouncilAdmin");
		}
		return aranarthPlayer;
	}

	private static AranarthPlayer updateGroupsAsSaint(CommandSender commandSender, AranarthPlayer aranarthPlayer,
			boolean isAvatar) {
		List<SubGroup> subGroups = aranarthPlayer.getSubGroups();	
		
		// Sub-group
		if (isAvatar && !subGroups.contains(SubGroup.AVATAR)) {
			Bukkit.dispatchCommand(commandSender, "manuaddsub " + aranarthPlayer.getUsername() + " Avatar");
			aranarthPlayer.addSubGroup(SubGroup.AVATAR);
		}

		// Primary group
		if (aranarthPlayer.getSaintStatus() == 1) {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " Saint1");
		} else if (aranarthPlayer.getSaintStatus() == 2) {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " Saint2");
		} else {
			Bukkit.dispatchCommand(commandSender, "manuadd " + aranarthPlayer.getUsername() + " Saint3");
		}
		return aranarthPlayer;
	}
}
