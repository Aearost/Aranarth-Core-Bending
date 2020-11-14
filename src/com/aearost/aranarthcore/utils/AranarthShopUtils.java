package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthShop;

/**
 * Provides utility methods to facilitate the manipulation of server and player
 * shops.
 * 
 * @author Aearost
 *
 */
public class AranarthShopUtils {

	private static HashMap<UUID, List<AranarthShop>> shops = new HashMap<>();
	private static List<AranarthShop> serverShops = new ArrayList<>();

	/**
	 * Gets an AranarthShop if it corresponds to a sign location.
	 * 
	 * @param signLocation
	 * @return
	 */
	public static AranarthShop getServerShop(Location signLocation) {
		if (serverShops == null) {
			return null;
		}

		for (AranarthShop shop : serverShops) {
			if (shop.getShopLocation().equals(signLocation)) {
				return shop;
			}
		}
		return null;
	}

	/**
	 * Gets an AranarthShop if it corresponds to both a player's UUID and the
	 * chest's location.
	 * 
	 * @param uuid
	 * @param chestLocation
	 * @return
	 */
	public static AranarthShop getShop(UUID uuid, Location chestLocation) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);

		if (playerShops == null) {
			return null;
		}

		for (AranarthShop shop : playerShops) {
			if (shop.getShopLocation().equals(chestLocation)) {
				return shop;
			}
		}
		return null;
	}

	/**
	 * Gets a list of all player shops corresponding to a player's UUID.
	 * 
	 * @param uuid
	 * @return
	 */
	public static List<AranarthShop> getPlayerShopList(UUID uuid) {
		return shops.get(uuid);
	}

	/**
	 * Adds a shop to a player's list of shops.
	 * 
	 * @param uuid
	 * @param shop
	 */
	public static void addShop(UUID uuid, AranarthShop shop) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		if (playerShops == null) {
			playerShops = new ArrayList<AranarthShop>();
		}
		playerShops.add(shop);
		shops.put(uuid, playerShops);
	}

	/**
	 * Removes a shop from a player's list of shops, provided the chest's location.
	 * 
	 * @param uuid
	 * @param chestLocation
	 */
	public static void removeShop(UUID uuid, Location chestLocation) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		int counter = 0;
		for (AranarthShop shop : playerShops) {
			if (shop.getShopLocation().equals(chestLocation)) {
				break;
			}
			counter++;
		}
		playerShops.remove(counter);
		if (playerShops.size() > 0) {
			shops.put(uuid, playerShops);
		} else {
			removePlayerShops(uuid);
		}
	}

	/**
	 * Adds a server shop.
	 * 
	 * @param shop
	 */
	public static void addServerShop(AranarthShop shop) {
		serverShops.add(shop);
	}

	/**
	 * Removes a server shop.
	 * 
	 * @param shopLocation
	 */
	public static void removeServerShop(Location shopLocation) {
		serverShops.remove(getServerShop(shopLocation));
	}

	/**
	 * Determines whether or not the shop chest has enough of the item to sell.
	 * 
	 * @param chestInventory
	 * @param item
	 * @param transactionQuantity
	 * @return
	 */
	public static boolean hasEnoughItemsToBuy(Inventory chestInventory, ItemStack item, int transactionQuantity) {
		ItemStack[] contents = chestInventory.getContents();
		int totalAmount = 0;
		for (ItemStack stack : contents) {
			if (stack == null) {
				continue;
			}

			if (stack.isSimilar(item)) {
				totalAmount += stack.getAmount();
			}
		}
		return totalAmount >= transactionQuantity;
	}

	/**
	 * Determines whether or not the shop has enough space for the player to sell
	 * to.
	 * 
	 * @param chestInventory
	 * @param item
	 * @param transactionQuantity
	 * @return
	 */
	public static boolean hasEnoughSpaceToSell(Inventory chestInventory, ItemStack item, int transactionQuantity) {
		ItemStack[] contents = chestInventory.getContents();
		for (ItemStack stack : contents) {
			if (stack == null) {
				return true;
			}

			int stackSize = stack.getAmount();
			if (stack.isSimilar(item)) {
				while (stackSize < 64 && transactionQuantity > 0) {
					stackSize++;
					transactionQuantity--;
					if (transactionQuantity == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Determines whether or not the player has enough of the item to sell to the
	 * shop.
	 * 
	 * @param player
	 * @param item
	 * @param transactionQuantity
	 * @return
	 */
	public static boolean hasItemsToSell(Player player, ItemStack item, int transactionQuantity) {
		ItemStack[] contents = player.getInventory().getStorageContents();
		int amountInInventory = 0;
		for (ItemStack stack : contents) {
			if (stack == null) {
				continue;
			}

			if (stack.isSimilar(item)) {
				amountInInventory += stack.getAmount();
			}
		}

		return amountInInventory >= transactionQuantity;
	}

	/**
	 * Transports the items from the buyer's inventory to the shop chest.
	 * 
	 * @param chestInventory
	 * @param player
	 * @param item
	 * @param transactionQuantity
	 * @param isPlayerShop
	 */
	public static void sellItems(Inventory chestInventory, Player player, ItemStack item, int transactionQuantity,
			boolean isPlayerShop) {
		ItemStack[] contents = player.getInventory().getStorageContents();
		int amountTransferred = 0;
		for (ItemStack stack : contents) {
			if (stack == null) {
				continue;
			}

			if (stack.isSimilar(item) && amountTransferred < transactionQuantity) {
				while (amountTransferred < transactionQuantity) {
					stack.setAmount(stack.getAmount() - 1);
					amountTransferred++;
				}
			} else if (amountTransferred == transactionQuantity) {
				break;
			}
		}
		if (isPlayerShop) {
			chestInventory.addItem(new ItemStack(item.getType(), transactionQuantity));
		}
	}

	/**
	 * Transports the items from the shop chest to the buyer's inventory.
	 * 
	 * @param chestInventory
	 * @param player
	 * @param item
	 * @param transactionQuantity
	 * @param isPlayerShop
	 */
	public static void purchaseItems(Inventory chestInventory, Player player, ItemStack item, int transactionQuantity,
			boolean isPlayerShop) {
		if (isPlayerShop) {
			ItemStack[] contents = chestInventory.getContents();
			int addedAmount = 0;
			for (ItemStack stack : contents) {
				if (stack == null) {
					continue;
				}

				int stackAmount = stack.getAmount();

				if (stack.isSimilar(item)) {
					while (stackAmount > 0 && addedAmount < transactionQuantity) {
						stackAmount--;
						stack.setAmount(stack.getAmount() - 1);
						addedAmount++;
					}
				}
			}
		}

		player.getInventory().addItem(new ItemStack(item.getType(), transactionQuantity));
	}

	/**
	 * Determines whether or not there is enough inventory space for a given item.
	 * 
	 * Returns 0 if the entire item can be added. Returns -1 if there is no space
	 * for any of the item. Returns a the remainder of what could not be fit if only
	 * some could be added.
	 * 
	 * @param inventory
	 * @param item
	 * @param transactionQuantity
	 * @return
	 */
	public static int hasInventorySpace(ItemStack[] inventory, ItemStack item, int transactionQuantity) {

		int originalTransactionQuantity = transactionQuantity;

		// Prioritizes filling up non-full stacks of the item in the player's inventory
		for (ItemStack stack : inventory) {
			if (stack != null && stack.getType() == item.getType()) {
				// Fill up an empty stack until it's full while removing one amount each
				// iteration
				while (transactionQuantity > 0) {
					int stackSize = stack.getAmount();
					if (stackSize < 64) {
						stackSize++;
						transactionQuantity--;
					} else {
						break;
					}
				}
			}
		}

		// Prioritizes filling up empty inventory slots
		while (transactionQuantity > 0) {
			boolean hasEmptySlot = false;
			for (ItemStack stack : inventory) {
				if (stack == null) {
					hasEmptySlot = true;
					break;
				}
			}

			// When there is inventory space
			if (hasEmptySlot) {
				if (transactionQuantity > 64) {
					transactionQuantity -= 64;
				} else {
					return 0;
				}
				// Some was placed in the inventory, but not all
			} else if (transactionQuantity < originalTransactionQuantity) {
				return transactionQuantity;
				// No space in the inventory
			} else {
				return -1;
			}
		}
		return 0;
	}

	/**
	 * Determines whether or not the input UUID is the owner of the player shop.
	 * 
	 * @param uuid
	 * @param chestLocation
	 * @return
	 */
	public static boolean isShopOwner(UUID uuid, Location chestLocation) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		if (playerShops == null) {
			return false;
		}
		for (AranarthShop shop : playerShops) {
			if (shop.getShopLocation().equals(chestLocation)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove all of a player's shops.
	 * 
	 * @param uuid
	 */
	public static void removePlayerShops(UUID uuid) {
		shops.remove(uuid);
	}

	/**
	 * Determines if the input material is a wall sign.
	 * 
	 * @param block
	 * @return
	 */
	public static boolean isWallSign(Material block) {
		return block == Material.ACACIA_WALL_SIGN || block == Material.BIRCH_WALL_SIGN
				|| block == Material.CRIMSON_WALL_SIGN || block == Material.DARK_OAK_WALL_SIGN
				|| block == Material.JUNGLE_WALL_SIGN || block == Material.OAK_WALL_SIGN
				|| block == Material.SPRUCE_WALL_SIGN || block == Material.WARPED_WALL_SIGN;
	}

	/**
	 * Returns the location of the shop if breaking the input block would also break
	 * the shop sign.
	 * 
	 * @param clickedBlock
	 * @param isPlayerShop
	 * @return
	 */
	public static Location getLocationIfBlockBreaksShop(Block clickedBlock, boolean isPlayerShop) {
		Location location = clickedBlock.getLocation();

		Location locationPlusX = new Location(location.getWorld(), location.getBlockX() + 1, location.getBlockY(),
				location.getBlockZ());
		Location locationMinusX = new Location(location.getWorld(), location.getBlockX() - 1, location.getBlockY(),
				location.getBlockZ());
		Location locationPlusZ = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(),
				location.getBlockZ() + 1);
		Location locationMinusZ = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(),
				location.getBlockZ() - 1);

		if (isWallSign(locationPlusX.getBlock().getType())) {
			if (isPlayerShop) {
				locationPlusX.setY(locationPlusX.getY() - 1);
				if (locationPlusX.getBlock().getType() == Material.CHEST) {
					return locationPlusX;
				}
			} else {
				return locationPlusX;
			}
		} else if (isWallSign(locationMinusX.getBlock().getType())) {
			if (isPlayerShop) {
				locationMinusX.setY(locationMinusX.getY() - 1);
				if (locationMinusX.getBlock().getType() == Material.CHEST) {
					return locationMinusX;
				}
			} else {
				return locationMinusX;
			}
		} else if (isWallSign(locationPlusZ.getBlock().getType())) {
			if (isPlayerShop) {
				locationPlusZ.setY(locationPlusZ.getY() - 1);
				if (locationPlusZ.getBlock().getType() == Material.CHEST) {
					return locationPlusZ;
				}
			} else {
				return locationPlusZ;
			}
		} else if (isWallSign(locationMinusZ.getBlock().getType())) {
			if (isPlayerShop) {
				locationMinusZ.setY(locationMinusZ.getY() - 1);
				if (locationMinusZ.getBlock().getType() == Material.CHEST) {
					return locationMinusZ;
				}
			} else {
				return locationMinusZ;
			}
		}
		return null;
	}

	/**
	 * Gets all player shops.
	 * 
	 * @return
	 */
	public static HashMap<UUID, List<AranarthShop>> getShops() {
		return shops;
	}

	/**
	 * Gets all server shops.
	 * 
	 * @return
	 */
	public static List<AranarthShop> getServerShops() {
		return serverShops;
	}

	/**
	 * Determines if the sign is the proper shop format. This works for both player
	 * shops as well as server shops.
	 * 
	 * @param sign
	 * @param uuid
	 * @param isCreating
	 * @return
	 */
	public static boolean isProperShopFormat(Sign sign, UUID uuid, boolean isCreating) {
		String owner = sign.getLine(0);
		int transactionAmount = 0;
		String line3 = sign.getLine(2);
		String line4 = sign.getLine(3);

		// If it's not a server shop
		if (uuid != null) {
			UUID ownerUuid = AranarthPlayerUtils.getUUID(owner);
			if (ownerUuid == null || !ownerUuid.equals(uuid) && isCreating) {
				return false;
			}
		}

		try {
			transactionAmount = Integer.parseInt(sign.getLine(1));
			if (transactionAmount < 1 || transactionAmount > 64) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		String[] line3Parts = null;
		String[] line4Parts = null;

		if (line3.equals("")) {
			return false;
		}

		if (line4.equals("")) {
			line3Parts = line3.split(" ");
		} else {
			line3Parts = line3.split(" ");
			line4Parts = line4.split(" ");
		}

		if (line3Parts.length == 3 && line4Parts == null) {
			if (line3Parts[0].equals("Buy") && line3Parts[1].equals(":")) {
				double buyAmount = 0;

				try {
					buyAmount = Double.parseDouble(line3Parts[2]);
					if (buyAmount < 0) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			} else if (line3Parts[0].equals("Sell") && line3Parts[1].equals(":")) {
				double sellAmount = 0;

				try {
					sellAmount = Double.parseDouble(line3Parts[2]);
					if (sellAmount < 0) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			} else {
				return false;
			}
		} else if (line3Parts.length == 3 && line4Parts.length == 3) {
			if (line3Parts[0].equals("Buy") && line3Parts[1].equals(":") && line4Parts[0].equals("Sell")
					&& line4Parts[1].equals(":")) {
				double buyAmount = 0;
				double sellAmount = 0;

				try {
					buyAmount = Double.parseDouble(line3Parts[2]);
					sellAmount = Double.parseDouble(line4Parts[2]);

					if (buyAmount < 0 || sellAmount < 0) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Determines whether or not the item has meta.
	 * 
	 * @param item
	 * @return
	 */
	public static boolean isItemWithoutMeta(ItemStack item) {
		return !item.hasItemMeta();
	}

	/**
	 * Determines if there is already a player shop at the chest location.
	 * 
	 * @param chestLocation
	 * @return
	 */
	public static boolean isAlreadyShop(Location chestLocation) {
		for (Map.Entry<UUID, List<AranarthShop>> entry : shops.entrySet()) {
			for (AranarthShop shop : entry.getValue()) {
				if (shop.getShopLocation().equals(chestLocation)) {
					return true;
				}
			}
		}
		return false;
	}

}
