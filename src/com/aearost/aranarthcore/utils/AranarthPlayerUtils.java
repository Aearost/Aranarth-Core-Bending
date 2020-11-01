package com.aearost.aranarthcore.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;

public class AranarthPlayerUtils {

	private static HashMap<UUID, AranarthPlayer> players = new HashMap<>();

	public AranarthPlayerUtils(boolean isServerStarting) {
		if (isServerStarting) {
			PersistenceUtils.readFromFile();
		} else {
			PersistenceUtils.writeToFile();
		}
	}
	
	public static UUID getUUID(String username) {
		HashMap<UUID, AranarthPlayer> players = getPlayers();
		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			if (entry.getValue().getUsername().equals(username)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static String getUsername(Player player) {
		return players.get(player.getUniqueId()).getUsername();
	}

	public static void setUsername(Player player) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setUsername(player.getName());
		players.put(player.getUniqueId(), aranarthPlayer);
	}
	
	public static int getRank(Player player) {
		return players.get(player.getUniqueId()).getRank();
	}

	public static void setRank(Player player, int rank) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setRank(rank);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	public static double getBalance(Player player) {
		return players.get(player.getUniqueId()).getBalance();
	}

	public static void setBalance(Player player, double newBalance) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setBalance(newBalance);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	public static void setIsMale(Player player, boolean isMale) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setIsMale(isMale);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	public static String replaceAvatar(Player player) {
		String currentAvatarName = "";
		boolean isCurrentMadePrevious = false;
		boolean isPreviousRemoved = false;

		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			UUID uuid = entry.getKey();
			String playerName = Bukkit.getPlayer(uuid).getName();
			if (entry.getValue().getAvatarStatus().equals("current")) {
				AranarthPlayer currentAvatar = getPlayer(uuid);
				currentAvatarName = playerName;
				currentAvatar.setAvatarStatus("previous");
				players.put(uuid, currentAvatar);
				isCurrentMadePrevious = true;
			} else if (entry.getValue().getAvatarStatus().equals("previous")) {
				AranarthPlayer previousAvatar = getPlayer(uuid);
				previousAvatar.setAvatarStatus("none");
				players.put(uuid, previousAvatar);
				isPreviousRemoved = true;
			}
			// Skips the rest as they will not have the field
			if (isCurrentMadePrevious && isPreviousRemoved) {
				break;
			}
		}
		AranarthPlayer newAvatar = getPlayer(player.getUniqueId());
		newAvatar.setAvatarStatus("current");
		addPlayer(player.getUniqueId(), newAvatar);
		
		return currentAvatarName;
	}

	public static void addPlayer(UUID uuid, AranarthPlayer aranarthPlayer) {
		// Assumes male player by default
		players.put(uuid, aranarthPlayer);
	}

	public static boolean hasPlayedBefore(Player player) {
		return players.containsKey(player.getUniqueId());
	}

	public static AranarthPlayer getPlayer(UUID uuid) {
		return players.get(uuid);
	}
	
	public static AranarthPlayer getPlayer(Player player) {
		return players.get(player.getUniqueId());
	}

	public static HashMap<UUID, AranarthPlayer> getPlayers() {
		return players;
	}

}
