package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthShop;

public class AranarthShopUtils {

	private static HashMap<UUID, List<AranarthShop>> shops = new HashMap<>();

	public static AranarthShop getShop(UUID uuid, Location chestLocation) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);

		if (playerShops == null) {
			return null;
		}

		for (AranarthShop shop : playerShops) {
			if (shop.getChestLocation().equals(chestLocation)) {
				return shop;
			}
		}
		return null;
	}

	public static List<AranarthShop> getPlayerShopList(UUID uuid) {
		return shops.get(uuid);
	}

	public static void addShop(UUID uuid, AranarthShop shop) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		if (playerShops == null) {
			playerShops = new ArrayList<AranarthShop>();
		}
		playerShops.add(shop);
		shops.put(uuid, playerShops);
	}

	public static void removeShop(UUID uuid, Location chestLocation) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		int counter = 0;
		for (AranarthShop shop : playerShops) {
			if (shop.getChestLocation().equals(chestLocation)) {
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
	 * Determines whether or not the shop chest has enough of the item to sell.
	 * 
	 * @param chestInventory
	 * @param item
	 * @param transactionQuantity
	 * @return
	 */
	public static boolean hasEnoughMoneyToBuy(Inventory chestInventory, ItemStack item, int transactionQuantity) {
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
	 * Transports the items from the shop chest to the buyer's inventory.
	 * 
	 * @param chestInventory
	 * @param player
	 * @param item
	 * @param transactionQuantity
	 */
	public static void purchaseItems(Inventory chestInventory, Player player, ItemStack item, int transactionQuantity) {
		ItemStack[] contents = chestInventory.getContents();
		int addedAmount = 0;
		for (ItemStack stack : contents) {
			if (stack == null) {
				continue;
			}

			int stackAmount = stack.getAmount();

			if (stack.getType() == item.getType()) {
				while (stackAmount > 0 && addedAmount < transactionQuantity) {
					stackAmount--;
					stack.setAmount(stack.getAmount() - 1);
					addedAmount++;
				}
			}
		}
		player.getInventory().addItem(new ItemStack(item.getType(), transactionQuantity));
	}

	/**
	 * Determines whether or not there is enough inventory space for a given item.
	 * 
	 * Returns 0 if the entire item can be added.
	 * Returns -1 if there is no space for any of the item.
	 * Returns a the remainder of what could not be fit if only some could be added.
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

	public static boolean isShopOwner(UUID uuid, Location chestLocation) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		if (playerShops == null) {
			return false;
		}
		for (AranarthShop shop : playerShops) {
			if (shop.getChestLocation().equals(chestLocation)) {
				return true;
			}
		}
		return false;
	}

	public static void removePlayerShops(UUID uuid) {
		shops.remove(uuid);
	}

	public static boolean isWallSign(Material block) {
		return block == Material.ACACIA_WALL_SIGN || block == Material.BIRCH_WALL_SIGN
				|| block == Material.CRIMSON_WALL_SIGN || block == Material.DARK_OAK_WALL_SIGN
				|| block == Material.JUNGLE_WALL_SIGN || block == Material.OAK_WALL_SIGN
				|| block == Material.SPRUCE_WALL_SIGN || block == Material.WARPED_WALL_SIGN;
	}

	public static Location getLocationIfBlockBreaksSign(Block clickedBlock) {
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
			locationPlusX.setY(locationPlusX.getY() - 1);
			if (locationPlusX.getBlock().getType() == Material.CHEST) {
				return locationPlusX;
			}
		} else if (isWallSign(locationMinusX.getBlock().getType())) {
			locationMinusX.setY(locationMinusX.getY() - 1);
			if (locationMinusX.getBlock().getType() == Material.CHEST) {
				return locationMinusX;
			}
		} else if (isWallSign(locationPlusZ.getBlock().getType())) {
			locationPlusZ.setY(locationPlusZ.getY() - 1);
			if (locationPlusZ.getBlock().getType() == Material.CHEST) {
				return locationPlusZ;
			}
		} else if (isWallSign(locationMinusZ.getBlock().getType())) {
			locationMinusZ.setY(locationMinusZ.getY() - 1);
			if (locationMinusZ.getBlock().getType() == Material.CHEST) {
				return locationMinusZ;
			}
		}
		return null;
	}

	public static HashMap<UUID, List<AranarthShop>> getShops() {
		return shops;
	}

	public static boolean isProperShopFormat(Sign sign, UUID uuid, boolean isCreating) {
		String owner = sign.getLine(0);
		int transactionAmount = 0;
		String line3 = sign.getLine(2);
		String line4 = sign.getLine(3);

		UUID ownerUuid = AranarthPlayerUtils.getUUID(owner);

		if (!ownerUuid.equals(uuid) && isCreating) {
			return false;
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

	public static boolean isItemWithoutMeta(ItemStack item) {
		return !item.hasItemMeta();
	}

	public static boolean isAlreadyShop(Location chestLocation) {
		for (Map.Entry<UUID, List<AranarthShop>> entry : shops.entrySet()) {
			for (AranarthShop shop : entry.getValue()) {
				if (shop.getChestLocation().equals(chestLocation)) {
					return true;
				}
			}
		}
		return false;
	}

}
