package com.aearost.aranarthcore.utils;

import java.util.HashMap;
import java.util.Map;

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

	public static void setBalance(Player player, int newBalance) {
		AranarthPlayer aranarthPlayer = getPlayer(player);
		aranarthPlayer.setBalance(newBalance);
		players.put(player.getName().toLowerCase(), aranarthPlayer);
	}

	public static int getRank(Player player) {
		return players.get(player.getName().toLowerCase()).getRank();
	}

	public static void setRank(Player player, int rank) {
		AranarthPlayer aranarthPlayer = getPlayer(player);
		aranarthPlayer.setRank(rank);
		players.put(player.getName().toLowerCase(), aranarthPlayer);
	}

	public static void setIsMale(Player player, boolean isMale) {
		AranarthPlayer aranarthPlayer = getPlayer(player);
		aranarthPlayer.setIsMale(isMale);
		players.put(player.getName().toLowerCase(), aranarthPlayer);
	}

	public static String setAvatar(Player player) {
		AranarthPlayer newAvatar = getPlayer(player);
		newAvatar.setAvatarStatus("current");

		String currentAvatarName = "";
		boolean isCurrentMadePrevious = false;
		boolean isPreviousRemoved = false;

		for (Map.Entry<String, AranarthPlayer> entry : players.entrySet()) {
			String playerName = entry.getKey();
			if (entry.getValue().getAvatarStatus().equals("current")) {
				AranarthPlayer currentAvatar = getPlayer(playerName);
				currentAvatarName = playerName;
				currentAvatar.setAvatarStatus("previous");
				players.put(currentAvatarName, currentAvatar);
				isCurrentMadePrevious = true;
			} else if (entry.getValue().getAvatarStatus().equals("previous")) {
				AranarthPlayer previousAvatar = getPlayer(playerName);
				previousAvatar.setAvatarStatus("none");
				players.put(playerName, previousAvatar);
				isPreviousRemoved = true;
			}
			// Skips the rest as they will not have the field
			if (isCurrentMadePrevious && isPreviousRemoved) {
				break;
			}
		}
		return currentAvatarName;
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

	public static AranarthPlayer getPlayer(String playerName) {
		return players.get(playerName.toLowerCase());
	}

	public static AranarthPlayer getPlayer(Player player) {
		return players.get(player.getName().toLowerCase());
	}

	public static HashMap<String, AranarthPlayer> getPlayers() {
		return players;
	}

}
