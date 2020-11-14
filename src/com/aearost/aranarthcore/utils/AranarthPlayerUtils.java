package com.aearost.aranarthcore.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;

/**
 * Provides utility methods to facilitate the manipulation of all AranarthPlayer
 * objects.
 * 
 * @author Aearost
 *
 */
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

	/**
	 * Returns the UUID of an input username. This will not work if the player has
	 * changed their name and not yet logged back onto the server.
	 * 
	 * @param username
	 * @return
	 */
	public static UUID getUUID(String username) {
		HashMap<UUID, AranarthPlayer> players = getPlayers();
		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			if (entry.getValue().getUsername().toLowerCase().equals(username.toLowerCase())) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Gets the stored username of a player.
	 * 
	 * @param player
	 * @return
	 */
	public static String getUsername(OfflinePlayer player) {
		return players.get(player.getUniqueId()).getUsername();
	}

	/**
	 * Sets the username of a player. This is used to update a player's username
	 * value in the case that they changed it.
	 * 
	 * @param player
	 */
	public static void setUsername(Player player) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setUsername(player.getName());
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	/**
	 * Gets the rank of the player.
	 * 
	 * @param player
	 * @return
	 */
	public static int getRank(Player player) {
		return players.get(player.getUniqueId()).getRank();
	}

	/**
	 * Sets the player's rank.
	 * 
	 * @param player
	 * @param rank
	 */
	public static void setRank(Player player, int rank) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setRank(rank);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	/**
	 * Gets the player's balance.
	 * 
	 * @param player
	 * @return
	 */
	public static double getBalance(Player player) {
		return players.get(player.getUniqueId()).getBalance();
	}

	/**
	 * Sets the player's balance.
	 * 
	 * @param player
	 * @param newBalance
	 */
	public static void setBalance(Player player, double newBalance) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setBalance(newBalance);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	/**
	 * Sets the player's isMale field.
	 * 
	 * @param player
	 * @param isMale
	 */
	public static void setIsMale(Player player, boolean isMale) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setIsMale(isMale);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	/**
	 * Replaces the current Avatar with a new input player.
	 * 
	 * @param player
	 * @return
	 */
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

	/**
	 * Adds a player to the players HashMap.
	 * 
	 * @param uuid
	 * @param aranarthPlayer
	 */
	public static void addPlayer(UUID uuid, AranarthPlayer aranarthPlayer) {
		// Assumes male player by default
		players.put(uuid, aranarthPlayer);
	}

	/**
	 * Determines if the player has played on the server before.
	 * 
	 * @param player
	 * @return
	 */
	public static boolean hasPlayedBefore(Player player) {
		return players.containsKey(player.getUniqueId());
	}

	/**
	 * Gets the AranarthPlayer corresponding to an input UUID.
	 * 
	 * @param uuid
	 * @return
	 */
	public static AranarthPlayer getPlayer(UUID uuid) {
		return players.get(uuid);
	}

	/**
	 * Gets the AranarthPlayer corresponding to an input OfflinePlayer.
	 * 
	 * @param player
	 * @return
	 */
	public static AranarthPlayer getPlayer(OfflinePlayer player) {
		return players.get(player.getUniqueId());
	}

	/**
	 * Gets the players HashMap.
	 * 
	 * @return
	 */
	public static HashMap<UUID, AranarthPlayer> getPlayers() {
		return players;
	}

}
