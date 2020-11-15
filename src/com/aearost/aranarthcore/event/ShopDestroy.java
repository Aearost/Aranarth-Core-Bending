package com.aearost.aranarthcore.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.objects.AranarthShop;
import com.aearost.aranarthcore.utils.AranarthShopUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class ShopDestroy implements Listener {

	public ShopDestroy(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles the breaking of a shop.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopDestroy(final BlockBreakEvent e) {

		Player player = e.getPlayer();
		Block block = e.getBlock();

		boolean isBlockWallSign = false;
		boolean isBlockShopChest = false;
		Location blockBreaksPlayerShop = AranarthShopUtils.getLocationIfBlockBreaksShop(block, true);
		Location blockBreaksServerShop = AranarthShopUtils.getLocationIfBlockBreaksShop(block, false);

		if (AranarthShopUtils.isWallSign(block.getType())) {
			isBlockWallSign = true;
		} else if (block.getType() == Material.CHEST) {
			Location locationAboveChest = block.getLocation();
			locationAboveChest.setY(locationAboveChest.getY() + 1);
			if (AranarthShopUtils.isWallSign(locationAboveChest.getBlock().getType())) {
				isBlockShopChest = true;
			}
		}

		if (isBlockWallSign || isBlockShopChest) {
			Sign sign = null;
			if (isBlockWallSign) {
				sign = (Sign) block.getState();
			} else if (isBlockShopChest) {
				Location locationAboveChest = block.getLocation();
				locationAboveChest.setY(locationAboveChest.getY() + 1);
				sign = (Sign) locationAboveChest.getBlock().getState();
			}

			// Player shop
			if (AranarthShopUtils.isProperShopFormat(sign, player.getUniqueId(), false)) {
				Location chestLocation = sign.getLocation();
				chestLocation.setY(chestLocation.getY() - 1);
				
				if (!AranarthShopUtils.isAlreadyShop(chestLocation)) {
					return;
				}
				
				// If it's their shop
				AranarthShop shop = AranarthShopUtils.getShop(player.getUniqueId(), chestLocation);
				if (shop != null || player.hasPermission("aranarthcore.shop.destroy.others")) {
					player.sendMessage(ChatUtils.translateToColor("&7You have destroyed this shop."));
					block.getWorld().dropItemNaturally(block.getLocation(), shop.getItem());
					
					AranarthShopUtils.removePlayerShopHologram(shop);
					AranarthShopUtils.removeShop(player.getUniqueId(), shop.getShopLocation());
				} else {
					if (AranarthShopUtils.isAlreadyShop(chestLocation)) {
						player.sendMessage(ChatUtils.translateToColor("&cYou cannot destroy other players' shops!"));
						e.setCancelled(true);
						return;
					}
				}
			}
			// Server shop
			else if (AranarthShopUtils.isProperShopFormat(sign, null, false)) {
				if (player.hasPermission("aranarthcore.shop.destroy.admin")) {
					if (AranarthShopUtils.getServerShop(sign.getLocation()) == null) {
						return;
					}
					player.sendMessage(ChatUtils.translateToColor("&7You have destroyed this server shop"));
					AranarthShopUtils.removeServerShop(block.getLocation());
					return;
				} else {
					player.sendMessage(ChatUtils.translateToColor("&cYou cannot destroy a server shop!"));
					e.setCancelled(true);
					return;
				}
			}
		} else if (blockBreaksPlayerShop != null) {
			Location signLocation = blockBreaksPlayerShop.clone();
			signLocation.setY(signLocation.getY() + 1);
			Sign sign = (Sign) signLocation.getBlock().getState();
			if (AranarthShopUtils.isProperShopFormat(sign, player.getUniqueId(), false)) {
				
				if (!AranarthShopUtils.isAlreadyShop(blockBreaksPlayerShop)) {
					return;
				}
				
				boolean isShopOwner = AranarthShopUtils.isShopOwner(player.getUniqueId(), blockBreaksPlayerShop);
				if (isShopOwner
						|| player.hasPermission("aranarthcore.shop.destroy.admin")) {
					player.sendMessage(ChatUtils.translateToColor("&7You have destroyed this shop"));
					
					AranarthShop shop = AranarthShopUtils.getShop(null, blockBreaksPlayerShop);
					AranarthShopUtils.removePlayerShopHologram(shop);
					
					if (isShopOwner) {
						AranarthShopUtils.removeShop(player.getUniqueId(), blockBreaksPlayerShop);
					} else {
						AranarthShopUtils.removeShop(null, blockBreaksPlayerShop);
					}
					
					return;
				} else {
					player.sendMessage(ChatUtils.translateToColor("&cYou cannot destroy other players' shops!"));
					e.setCancelled(true);
					return;
				}
			}
		} else if (blockBreaksServerShop != null) {
			Sign sign = (Sign) blockBreaksServerShop.getBlock().getState();
			if (AranarthShopUtils.isProperShopFormat(sign, null, false)) {
				if (AranarthShopUtils.getServerShop(blockBreaksServerShop) != null) {
					if (player.hasPermission("aranarthcore.shop.destroy.admin")) {
						player.sendMessage(ChatUtils.translateToColor("&7You have destroyed this server shop"));
						AranarthShopUtils.removeServerShop(blockBreaksServerShop);
						return;
					} else {
						player.sendMessage(ChatUtils.translateToColor("&cYou cannot destroy a server shop!"));
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}
}
