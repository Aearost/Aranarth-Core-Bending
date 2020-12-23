package com.aearost.aranarthcore.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent.Result;

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
	public static void setGender(Player player, Gender gender) {
		AranarthPlayer aranarthPlayer = getPlayer(player.getUniqueId());
		aranarthPlayer.setPersonalGender(gender);
		players.put(player.getUniqueId(), aranarthPlayer);
	}

	/**
	 * Replaces the current Avatar with a new input player. This only affects the
	 * AranarthPlayer object, as well as their elements.
	 * 
	 * @param playerNewAvatar
	 * @return
	 */
	public static String replaceAvatar(Player playerNewAvatar) {
		String currentAvatarName = null;
		boolean isCurrentMadePrevious = false;
		boolean isPreviousRemoved = false;

		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			UUID uuid = entry.getKey();
			String playerName = getUsername(Bukkit.getOfflinePlayer(uuid));
			if (entry.getValue().getAvatarStatus().equals("current")) {
				AranarthPlayer currentAvatar = getPlayer(uuid);
				currentAvatarName = playerName;
				currentAvatar.setAvatarStatus("previous");
				addPlayer(uuid, currentAvatar);
				isCurrentMadePrevious = true;

				// Removes old avatar's elements and allows them to return to an element for
				// free
				Player player = Bukkit.getPlayer(uuid);
				BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);
				if (bendingPlayer == null) {
					GeneralMethods.createBendingPlayer(uuid, player.getName());
					bendingPlayer = BendingPlayer.getBendingPlayer(player);
				}
				for (Element e : Element.getElements()) {
					if (bendingPlayer.hasElement(e)) {
						bendingPlayer.getElements().remove(e);
					}
				}
				bendingPlayer.getElements().clear();
				bendingPlayer.getSubElements().clear();
				GeneralMethods.saveElements(bendingPlayer);
				GeneralMethods.saveSubElements(bendingPlayer);
				GeneralMethods.removeUnusableAbilities(player.getName());
				Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, Bukkit.getPlayer(uuid), null, Result.REMOVE));
				currentAvatar.setIsAbleToChangeElement(true);

				if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
					Bukkit.getPlayer(uuid).sendMessage(ChatUtils.translateToColor("&7You are no longer the avatar!"));
					Bukkit.getPlayer(uuid).sendMessage(ChatUtils.translateToColor("&7Go to &e/warp info &7and re-select an element."));
				}
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
		if (playerNewAvatar != null) {
			AranarthPlayer newAvatar = getPlayer(playerNewAvatar.getUniqueId());
			newAvatar.setAvatarStatus("current");

			// Initializes the new avatar and gives them all elements for free
			newAvatar.setIsAbleToChangeElement(false);
			addPlayer(getUUID(newAvatar.getUsername()), newAvatar);
			BendingPlayer bendingPlayer = BendingPlayer
					.getBendingPlayer(Bukkit.getOfflinePlayer(playerNewAvatar.getUniqueId()));
			if (bendingPlayer == null) {
				GeneralMethods.createBendingPlayer(playerNewAvatar.getUniqueId(), playerNewAvatar.getName());
				bendingPlayer = BendingPlayer.getBendingPlayer(playerNewAvatar);
			}
			for (Element e : Element.getElements()) {
				if (bendingPlayer.hasElement(e)) {
					bendingPlayer.getElements().remove(e);
				}
			}
			// Add main elements
			bendingPlayer.addElement(Element.AIR);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, Element.AIR, Result.ADD));
			bendingPlayer.addElement(Element.EARTH);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, Element.EARTH, Result.ADD));
			bendingPlayer.addElement(Element.FIRE);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, Element.FIRE, Result.ADD));
			bendingPlayer.addElement(Element.WATER);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, Element.WATER, Result.ADD));
			
			GeneralMethods.saveElements(bendingPlayer);
			// Add sub-elements
			bendingPlayer.addSubElement(SubElement.SAND);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.SAND, Result.ADD));
			bendingPlayer.addSubElement(SubElement.METAL);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.METAL, Result.ADD));
			bendingPlayer.addSubElement(SubElement.LAVA);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.LAVA, Result.ADD));
			bendingPlayer.addSubElement(SubElement.LIGHTNING);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.LIGHTNING, Result.ADD));
			bendingPlayer.addSubElement(SubElement.COMBUSTION);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.COMBUSTION, Result.ADD));
			bendingPlayer.addSubElement(SubElement.ICE);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.ICE, Result.ADD));
			bendingPlayer.addSubElement(SubElement.PLANT);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.PLANT, Result.ADD));
			bendingPlayer.addSubElement(SubElement.HEALING);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeElementEvent(null, playerNewAvatar, SubElement.HEALING, Result.ADD));
			
			GeneralMethods.saveSubElements(bendingPlayer);
		}
		PersistenceUtils.writeDateToFile();
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
	
	/**
	 * Compares dates and begins avatar choosing process after 7 days
	 * 
	 * @param readDate
	 * 
	 */
	public static boolean isAvatarTimeLimit(Date readDate)
	{
		Date currDate = new Date();
		long diffInMillies = Math.abs(currDate.getTime() - readDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		if(diff >= 7)
		{
			return true;
		}
		return false;
	}
	/**
	 * Odd function need: a find avatar function lol i promise i tried to avoid this
	 * 
	 * 
	 */
	public static UUID zukoSearch()
	{
		UUID toReturn = null;
		for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
			if (entry.getValue().getAvatarStatus().equals("current"))
			{
				toReturn = entry.getKey();
				return toReturn;
			}
		}
		return toReturn;
	}
}
