package com.aearost.aranarthcore.utils;

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
		boolean isCouncil = aranarthPlayer.getCouncilStatus() > 0;

		CommandSender commandSender = Bukkit.getServer().getConsoleSender();

		String prefix = "";

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

		// Execute in Aldara
		Bukkit.dispatchCommand(commandSender, "manselect world");

		Bukkit.dispatchCommand(commandSender, "manudelv " + aranarthPlayer.getUsername() + " prefix");
		Bukkit.dispatchCommand(commandSender, "manuaddv " + aranarthPlayer.getUsername() + " prefix " + prefix);
		// Another line to set their primary group and their sub groups if we use those
	}
}
