package com.aearost.aranarthcore.utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthShop;

public class AranarthShopUtils {

	private static HashMap<UUID, List<AranarthShop>> shops = new HashMap<>();
	
	public static List<AranarthShop> getPlayerShopList(Player player) {
		return shops.get(player.getUniqueId());
	}
	
	public static void addShop(Player player, AranarthShop shop) {
		List<AranarthShop> playerShops = getPlayerShopList(player);
		playerShops.add(shop);
		shops.put(player.getUniqueId(), playerShops);
	}
	
	public static void removeShop(Player player, AranarthShop shopToRemove) {
		List<AranarthShop> playerShops = getPlayerShopList(player);
		int counter = 0;
		for (AranarthShop shop : playerShops) {
			if (shop.getChestLocation().equals(shopToRemove.getChestLocation())) {
				break;
			}
			counter++;
		}
		playerShops.remove(counter);
		shops.put(player.getUniqueId(), playerShops);
	}
	
	public static HashMap<UUID, List<AranarthShop>> getShops() {
		return shops;
	}
	
}
