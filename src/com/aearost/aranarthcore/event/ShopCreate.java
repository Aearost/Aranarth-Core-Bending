package com.aearost.aranarthcore.event;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.objects.AranarthShop;
import com.aearost.aranarthcore.utils.AranarthShopUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class ShopCreate implements Listener {

	public ShopCreate(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles the creation of a shop.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopCreate(final PlayerInteractEvent e) {
		if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (AranarthShopUtils.isWallSign(e.getClickedBlock().getType())) {
				Bukkit.broadcastMessage("A");
				Player player = e.getPlayer();
				if (player.isSneaking()) {
					Bukkit.broadcastMessage("B");
					Sign sign = (Sign) e.getClickedBlock().getState();
					if (AranarthShopUtils.isProperShopFormat(sign, player.getUniqueId(), true)) {
						Bukkit.broadcastMessage("C");
						if (AranarthShopUtils.isItemWithoutMeta(player.getInventory().getItemInMainHand())) {
							Bukkit.broadcastMessage("D");
							Location signLocation = e.getClickedBlock().getLocation();
							Location chestLocation = new Location(player.getWorld(), signLocation.getBlockX(),
									signLocation.getBlockY() - 1, signLocation.getBlockZ());

							if (AranarthShopUtils.isAlreadyShop(chestLocation)) {
								player.sendMessage(ChatUtils.translateToColor("&cThis is already a player shop!"));
								e.setCancelled(true);
								return;
							}

							if (chestLocation.getBlock().getType() != Material.CHEST) {
								player.sendMessage(ChatUtils.translateToColor("&cThere is no chest for this shop!"));
								return;
							}

							AranarthShop shop = null;

							UUID uuid = player.getUniqueId();
							int transactionAmount = Integer.parseInt(sign.getLine(1));
							ItemStack item = new ItemStack(player.getInventory().getItemInMainHand().getType(), 1);
							player.getInventory().getItemInMainHand()
									.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

							String[] line3 = sign.getLine(2).split(" ");

							if (line3[0].equals("Buy")) {
								double buyAmount = Double.parseDouble(line3[2]);
								if (!sign.getLine(3).equals("")) {
									String[] line4 = sign.getLine(3).split(" ");
									double sellAmount = Double.parseDouble(line4[2]);
									shop = new AranarthShop(uuid, transactionAmount, buyAmount, item, sellAmount,
											chestLocation);
								} else {
									shop = new AranarthShop(uuid, transactionAmount, buyAmount, item, chestLocation);
								}
							} else {
								double sellAmount = Double.parseDouble(line3[2]);
								shop = new AranarthShop(uuid, transactionAmount, item, sellAmount, chestLocation);
							}
							AranarthShopUtils.addShop(uuid, shop);
							player.sendMessage(ChatUtils.translateToColor("&aA chest shop has been created!"));
						} else {
							player.sendMessage(ChatUtils.translateToColor("&cYou cannot make a shop out of this!"));
						}
					}

				}
			}
		}
	}

}
