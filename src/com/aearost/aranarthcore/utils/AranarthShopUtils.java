package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthShop;

public class AranarthShopUtils {

	private static HashMap<UUID, List<AranarthShop>> shops = new HashMap<>();

	public static List<AranarthShop> getPlayerShopList(UUID uuid) {
		return shops.get(uuid);
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

	public static void addShop(UUID uuid, AranarthShop shop) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		if (playerShops == null) {
			playerShops = new ArrayList<AranarthShop>();
		}
		playerShops.add(shop);
		shops.put(uuid, playerShops);
	}

	public static void removeShop(UUID uuid, AranarthShop shopToRemove) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
		int counter = 0;
		for (AranarthShop shop : playerShops) {
			if (shop.getChestLocation().equals(shopToRemove.getChestLocation())) {
				break;
			}
			counter++;
		}
		playerShops.remove(counter);
		shops.put(uuid, playerShops);
	}

	public static boolean isWallSign(Material clickedBlock) {
		return clickedBlock == Material.ACACIA_WALL_SIGN || clickedBlock == Material.BIRCH_WALL_SIGN
				|| clickedBlock == Material.CRIMSON_WALL_SIGN || clickedBlock == Material.DARK_OAK_WALL_SIGN
				|| clickedBlock == Material.JUNGLE_WALL_SIGN || clickedBlock == Material.OAK_WALL_SIGN
				|| clickedBlock == Material.SPRUCE_WALL_SIGN || clickedBlock == Material.WARPED_WALL_SIGN;
	}

	public static boolean isProperShopFormat(Sign sign, UUID uuid) {
		String owner = sign.getLine(0);
		int transactionAmount = 0;
		String line3 = sign.getLine(2);
		String line4 = sign.getLine(3);

		if (!AranarthPlayerUtils.getUUID(owner).equals(uuid)) {
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

	public static HashMap<UUID, List<AranarthShop>> getShops() {
		return shops;
	}

}
