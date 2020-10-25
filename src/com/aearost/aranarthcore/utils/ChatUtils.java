package com.aearost.aranarthcore.utils;

import org.bukkit.ChatColor;

public class ChatUtils {
	
	/**
	 * Allows the formatting of messages to contain Minecraft colors
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
	
	public static String stripColor(String msg) {
		String colorStripped = ChatColor.stripColor(msg);
		while (colorStripped.startsWith("&")) {
			colorStripped = colorStripped.substring(2);
		}
		return colorStripped;
	}
	
}
