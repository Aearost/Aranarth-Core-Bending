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
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.AranarthCore;
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
		boolean isClickedBlockChest = block.getType() == Material.CHEST;
		boolean isClickedBlockWallSign = AranarthShopUtils.isWallSign(block.getType());
		boolean hasProperShopFormat = false;
		if (isClickedBlockWallSign) {
			hasProperShopFormat = AranarthShopUtils.isProperShopFormat((Sign) block.getState(), player.getUniqueId(), false);
		}
		Location locationIfBlockBreaksSign = AranarthShopUtils.getLocationIfBlockBreaksSign(block);
		if (isClickedBlockChest || hasProperShopFormat || locationIfBlockBreaksSign != null) {
			Location shopLocation = null;

			if (isClickedBlockChest) {
				shopLocation = block.getLocation();
			} else if (hasProperShopFormat) {
				shopLocation = block.getLocation();
				shopLocation.setY(block.getLocation().getY() - 1);
				if (shopLocation.getBlock().getType() != Material.CHEST) {
					return;
				}
			} else {
				shopLocation = AranarthShopUtils.getLocationIfBlockBreaksSign(block);
				if (shopLocation == null) {
					return;
				}
			}

			if (AranarthShopUtils.isAlreadyShop(shopLocation)) {

				// If player isn't owner
				if (!AranarthShopUtils.isShopOwner(player.getUniqueId(), shopLocation)) {
					player.sendMessage(ChatUtils.translateToColor("&cYou cannot destroy a shop that is not yours!"));
					e.setCancelled(true);
					return;
				}

				ItemStack item = AranarthShopUtils.getShop(player.getUniqueId(), shopLocation).getItem();
				AranarthShopUtils.removeShop(player.getUniqueId(), shopLocation);
				shopLocation.getWorld().dropItemNaturally(shopLocation, item);
				player.sendMessage(ChatUtils.translateToColor("&cYou have destroyed the shop!"));
			}
		}

	}

}
