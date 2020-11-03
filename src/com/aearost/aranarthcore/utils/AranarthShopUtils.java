package com.aearost.aranarthcore.utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.aearost.aranarthcore.objects.AranarthShop;

public class AranarthShopUtils {

	private static HashMap<UUID, List<AranarthShop>> shops = new HashMap<>();
	
	public static List<AranarthShop> getPlayerShopList(UUID uuid) {
		return shops.get(uuid);
	}
	
	public static void addShop(UUID uuid, AranarthShop shop) {
		List<AranarthShop> playerShops = getPlayerShopList(uuid);
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
	
	public static HashMap<UUID, List<AranarthShop>> getShops() {
		return shops;
	}
	
}
