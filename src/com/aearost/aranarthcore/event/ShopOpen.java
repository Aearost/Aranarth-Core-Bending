package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.objects.AranarthShop;
import com.aearost.aranarthcore.utils.AranarthShopUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class ShopOpen implements Listener {

	public ShopOpen(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Prevents a user from opening a shop chest if they are not its owner.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopSignClick(final InventoryOpenEvent e) {
		
		if (e.getInventory().getType().equals(InventoryType.CHEST))
        {
			if (AranarthShopUtils.isAlreadyShop(e.getInventory().getLocation())) {
				AranarthShop shop = AranarthShopUtils.getShop(e.getPlayer().getUniqueId(), e.getInventory().getLocation());
				if (shop == null) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatUtils.translateToColor("&cThis is not your shop!"));
				}
			}
       
        }
		
	}

}
