package com.aearost.aranarthcore.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;

public class AranarthPlayerUtils {

	private static HashMap<String, AranarthPlayer> players = new HashMap<>();

	public AranarthPlayerUtils(boolean isServerStarting) {
		if (isServerStarting) {
			PersistenceUtils.readFromFile();
		} else {
			PersistenceUtils.writeToFile();
		}
	}
	
	public static double getBalance(Player player) {
		return players.get(player.getName().toLowerCase()).getBalance();
	}
	
	public static int getRank(Player player) {
		return players.get(player.getName().toLowerCase()).getRank();
	}
	
	public static void setIsMale(Player player, boolean isMale) {
		AranarthPlayer aranarthPlayer = getPlayer(player);
		aranarthPlayer.setIsMale(isMale);
		players.put(player.getName().toLowerCase(), aranarthPlayer);
	}

	public static void addPlayer(String playerName, AranarthPlayer aranarthPlayer) {
		// Assumes male player by default
		players.put(playerName.toLowerCase(), aranarthPlayer);
	}
	
	public static void addPlayer(Player player, AranarthPlayer aranarthPlayer) {
		// Assumes male player by default
		players.put(player.getName().toLowerCase(), aranarthPlayer);
	}
	
	public static boolean hasPlayedBefore(Player player) {
		return players.containsKey(player.getName().toLowerCase());
	}
	
	public static AranarthPlayer getPlayer(Player player) {
		return players.get(player.getName().toLowerCase());
	}
	
	public static HashMap<String, AranarthPlayer> getPlayers() {
		return players;
	}

}
