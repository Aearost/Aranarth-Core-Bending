package com.aearost.aranarthcore.event;

import java.text.NumberFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.objects.AranarthShop;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.AranarthShopUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;

public class ShopSignClick implements Listener {

	public ShopSignClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles buying and selling items through shop signs.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onShopSignClick(final PlayerInteractEvent e) {

		if (e.getHand() == EquipmentSlot.HAND
				&& (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
			if (AranarthShopUtils.isWallSign(e.getClickedBlock().getType())) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				Player player = e.getPlayer();
				UUID signUuid = AranarthPlayerUtils.getUUID(sign.getLine(0));

				Location signLocation = e.getClickedBlock().getLocation();
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				// If it's a player shop
				if (AranarthShopUtils.isProperShopFormat(sign, player.getUniqueId(), false)) {
					Location chestLocation = new Location(signLocation.getWorld(), signLocation.getBlockX(),
							signLocation.getBlockY() - 1, signLocation.getBlockZ());
					
					AranarthShop shop = AranarthShopUtils.getShop(signUuid, chestLocation);

					// Shop exists and the owner isn't the one clicking
					if (shop != null && !shop.getUUID().equals(player.getUniqueId())) {
						Chest chest = (Chest) chestLocation.getBlock().getState();
						Inventory chestInventory = chest.getInventory();

						// Buying from the shop
						if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (shop.getBuyAmount() >= 0) {
								if (AranarthShopUtils.hasEnoughItemsToBuy(chestInventory, shop.getItem(),
										shop.getTransactionQuantity())) {
									AranarthPlayer buyer = AranarthPlayerUtils.getPlayer(player.getUniqueId());
									if (buyer.getBalance() >= shop.getBuyAmount()) {
										if (AranarthShopUtils.hasInventorySpace(
												player.getInventory().getStorageContents(), shop.getItem(),
												shop.getTransactionQuantity()) == 0) {
											AranarthPlayer seller = AranarthPlayerUtils.getPlayer(shop.getUUID());
											if (shop.getBuyAmount() >= 250) {
												PersistenceUtils.logTransaction(buyer.getUsername() + " ("
														+ formatter.format(buyer.getBalance()) + ") has bought "
														+ shop.getTransactionQuantity() + " "
														+ shop.getItem().getType().name() + " from " + seller.getUsername()
														+ " (" + formatter.format(seller.getBalance()) + ") for $"
														+ shop.getBuyAmount());
											}
											buyer.setBalance(buyer.getBalance() - shop.getBuyAmount());
											seller.setBalance(seller.getBalance() + shop.getBuyAmount());
											AranarthShopUtils.purchaseItems(chestInventory, player, shop.getItem(),
													shop.getTransactionQuantity(), true);
											player.sendMessage(ChatUtils
													.translateToColor("&7The items have been added to your inventory"));
											String formattedAmount = formatter.format(shop.getSellAmount());
											if (Bukkit.getOnlinePlayers()
													.contains(Bukkit.getPlayer(seller.getUsername()))) {
												Bukkit.getPlayer(seller.getUsername()).sendMessage(
														ChatUtils.translateToColor("&e" + buyer.getUsername()
																+ " &7has bought &6" + formattedAmount
																+ " &7worth of items from your shop"));
											}
										} else {
											player.sendMessage(ChatUtils.translateToColor(
													"&cYou do not have enough inventory space for this!"));
										}
									} else {
										player.sendMessage(
												ChatUtils.translateToColor("&cYou do not have enough money for this!"));
									}
								} else {
									player.sendMessage(ChatUtils
											.translateToColor("&cThis shop does not have enough items to sell!"));
								}
							}
						}
						// Selling to the shop
						else if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.LEFT_CLICK_BLOCK) {
							if (shop.getSellAmount() >= 0) {
								if (player.getGameMode() != GameMode.CREATIVE) {
									if (AranarthShopUtils.hasEnoughSpaceToSell(chestInventory, shop.getItem(),
											shop.getTransactionQuantity())) {
										AranarthPlayer buyer = AranarthPlayerUtils.getPlayer(shop.getUUID());
										if (buyer.getBalance() > shop.getSellAmount()) {
											if (AranarthShopUtils.hasItemsToSell(player, shop.getItem(),
													shop.getTransactionQuantity())) {
												AranarthPlayer seller = AranarthPlayerUtils
														.getPlayer(player.getUniqueId());
												if (shop.getBuyAmount() >= 250) {
													PersistenceUtils.logTransaction(seller.getUsername() + " ("
															+ formatter.format(seller.getBalance()) + ") has sold "
															+ shop.getTransactionQuantity() + " "
															+ shop.getItem().getType().name() + " to " + buyer.getUsername()
															+ " (" + formatter.format(buyer.getBalance()) + ") for $"
															+ shop.getBuyAmount());
												}
												buyer.setBalance(buyer.getBalance() - shop.getSellAmount());
												seller.setBalance(seller.getBalance() + shop.getSellAmount());
												AranarthShopUtils.sellItems(chestInventory, player, shop.getItem(),
														shop.getTransactionQuantity(), true);
												String formattedAmount = formatter.format(shop.getSellAmount());
												player.sendMessage(ChatUtils
														.translateToColor("&7You have earned &6" + formattedAmount));
												if (Bukkit.getOnlinePlayers()
														.contains(Bukkit.getPlayer(buyer.getUsername()))) {
													Bukkit.getPlayer(buyer.getUsername()).sendMessage(
															ChatUtils.translateToColor("&e" + seller.getUsername()
																	+ " &7has sold &6" + formattedAmount
																	+ " &7worth of items to your shop"));
												}
											} else {
												player.sendMessage(ChatUtils
														.translateToColor("&cYou do not have enough of this item!"));
											}
										} else {
											player.sendMessage(ChatUtils
													.translateToColor("&cThis shop owner cannot afford this!"));
										}
									} else {
										player.sendMessage(ChatUtils
												.translateToColor("&cThis shop does not have enough free space!"));
									}
								} else {
									e.setCancelled(true);
									player.sendMessage(
											ChatUtils.translateToColor("&cYou cannot do this while in creative!"));
								}
							}
						}
					}
				}
				// If it's the server shop
				else if (AranarthShopUtils.isProperShopFormat(sign, null, false)) {
					AranarthShop serverShop = AranarthShopUtils.getServerShop(signLocation);
					// If shop does not exist
					if (serverShop == null) {
						return;
					}

					// Buying from the shop
					if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (serverShop.getBuyAmount() >= 0) {
							AranarthPlayer buyer = AranarthPlayerUtils.getPlayer(player.getUniqueId());
							if (buyer.getBalance() >= serverShop.getBuyAmount()) {
								if (AranarthShopUtils.hasInventorySpace(player.getInventory().getStorageContents(),
										serverShop.getItem(), serverShop.getTransactionQuantity()) == 0) {
									if (serverShop.getBuyAmount() >= 250) {
										PersistenceUtils.logTransaction(
												buyer.getUsername() + " (" + formatter.format(buyer.getBalance())
														+ ") has bought " + serverShop.getTransactionQuantity() + " "
														+ serverShop.getItem().getType().name()
														+ " from the server shop for $" + serverShop.getBuyAmount());
									}
									buyer.setBalance(buyer.getBalance() - serverShop.getBuyAmount());
									AranarthShopUtils.purchaseItems(null, player, serverShop.getItem(),
											serverShop.getTransactionQuantity(), false);
									player.sendMessage(ChatUtils
											.translateToColor("&7The items have been added to your inventory"));
								} else {
									player.sendMessage(ChatUtils
											.translateToColor("&cYou do not have enough inventory space for this!"));
								}
							} else {
								player.sendMessage(
										ChatUtils.translateToColor("&cYou do not have enough money for this!"));
							}
						}
					}
					// Selling to the shop
					else if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.LEFT_CLICK_BLOCK) {
						if (serverShop.getSellAmount() >= 0) {
							if (player.getGameMode() != GameMode.CREATIVE) {
								if (AranarthShopUtils.hasItemsToSell(player, serverShop.getItem(),
										serverShop.getTransactionQuantity())) {
									AranarthPlayer seller = AranarthPlayerUtils.getPlayer(player.getUniqueId());
									if (serverShop.getSellAmount() >= 250) {
										PersistenceUtils.logTransaction(
												seller.getUsername() + " (" + formatter.format(seller.getBalance())
														+ ") has sold " + serverShop.getTransactionQuantity() + " "
														+ serverShop.getItem().getType().name()
														+ " to the server shop for $" + serverShop.getSellAmount());
									}
									seller.setBalance(seller.getBalance() + serverShop.getSellAmount());
									AranarthShopUtils.sellItems(null, player, serverShop.getItem(),
											serverShop.getTransactionQuantity(), false);
									player.sendMessage(ChatUtils.translateToColor(
											"&7You have earned &6" + formatter.format(serverShop.getSellAmount())));
								} else {
									player.sendMessage(
											ChatUtils.translateToColor("&cYou do not have enough of this item!"));
								}
							} else {
								e.setCancelled(true);
								player.sendMessage(
										ChatUtils.translateToColor("&cYou cannot do this while in creative!"));
							}
						}
					}
				}
			}
		}
	}
}
