package com.aearost.aranarthcore.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;

public class AranarthPlayerUtils {

	private static HashMap<UUID, AranarthPlayer> players = new HashMap<>();

	public AranarthPlayerUtils(boolean isServerStarting) {
		if (isServerStarting) {
			PersistenceUtils.readPlayersFromFile();
			PersistenceUtils.readShopSignsFromFile();
		} else {
			PersistenceUtils.writePlayersToFile();
			PersistenceUtils.writeShopSignsToFile();
		}
	}
	
	public static UUID getUUID(String username) {
		HashMap<UUID, AranarthPlayer> players = getPlayers();
		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			if (entry.getValue().getUsername().toLowerCase().equals(username.toLowerCase())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static String getUsername(OfflinePlayer player) {
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
//		CommandSender commandSender = Bukkit.getServer().getConsoleSender();

		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			UUID uuid = entry.getKey();
			String playerName = getUsername(Bukkit.getOfflinePlayer(uuid));
			if (entry.getValue().getAvatarStatus().equals("current")) {
				AranarthPlayer currentAvatar = getPlayer(uuid);
				currentAvatarName = playerName;
				currentAvatar.setAvatarStatus("previous");
				addPlayer(uuid, currentAvatar);
				isCurrentMadePrevious = true;
//				Bukkit.dispatchCommand(commandSender, "bending remove " + player.getName() + " air");
//				Bukkit.dispatchCommand(commandSender, "bending remove " + player.getName() + " water");
//				Bukkit.dispatchCommand(commandSender, "bending remove " + player.getName() + " earth");
//				Bukkit.dispatchCommand(commandSender, "bending remove " + player.getName() + " fire");
			} else if (entry.getValue().getAvatarStatus().equals("previous")) {
				AranarthPlayer previousAvatar = getPlayer(uuid);
				previousAvatar.setAvatarStatus("none");
				addPlayer(uuid, previousAvatar);
				isPreviousRemoved = true;
			}
			// Skips the rest as they will not have the field
			if (isCurrentMadePrevious && isPreviousRemoved) {
				break;
			}
		}
		if (player != null) {
//			Bukkit.dispatchCommand(commandSender, "bending remove " + player.getName() + " chi");
//			Bukkit.dispatchCommand(commandSender, "bending add air " + player.getName());
//			Bukkit.dispatchCommand(commandSender, "bending add water " + player.getName());
//			Bukkit.dispatchCommand(commandSender, "bending add earth " + player.getName());
//			Bukkit.dispatchCommand(commandSender, "bending add fire " + player.getName());
			AranarthPlayer newAvatar = getPlayer(player.getUniqueId());
			newAvatar.setAvatarStatus("current");
		}
		
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
	
	public static AranarthPlayer getPlayer(OfflinePlayer player) {
		return players.get(player.getUniqueId());
	}

	public static HashMap<UUID, AranarthPlayer> getPlayers() {
		return players;
	}

}
