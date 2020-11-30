package com.aearost.aranarthcore.event;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.objects.AranarthShop;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
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
				Player player = e.getPlayer();
				if (player.isSneaking()) {
					Sign sign = (Sign) e.getClickedBlock().getState();

					if (AranarthShopUtils.isProperShopFormat(sign, player.getUniqueId(), true)) {

						if (AranarthShopUtils.isItemWithoutMeta(player.getInventory().getItemInMainHand())) {
							e.setCancelled(true);

							UUID uuid = player.getUniqueId();
							if (!player.hasPermission("aranarthcore.shop.create.player")) {
								player.sendMessage(ChatUtils
										.translateToColor("&cYou must rank up to Baron to create player shops!"));
								return;
							} else {
								AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(uuid);
								final int BARON_SHOP_AMOUNT = 3;
								final int COUNT_SHOP_AMOUNT = 7;
								final int DUKE_SHOP_AMOUNT = 15;
								final int KING_SHOP_AMOUNT = 30;

								// Only a Baron
								if (aranarthPlayer.getRank() < 4) {
									if (AranarthShopUtils.getShopCount(uuid) == BARON_SHOP_AMOUNT) {
										if (aranarthPlayer.getIsMale()) {
											player.sendMessage(
													ChatUtils.translateToColor("&cA Baron can only create up to "
															+ BARON_SHOP_AMOUNT + " shops!"));
										} else {
											player.sendMessage(
													ChatUtils.translateToColor("&cA Baroness can only create up to "
															+ BARON_SHOP_AMOUNT + " shops!"));
										}
										return;
									}
								}
								// Only a Count
								else if (aranarthPlayer.getRank() < 5) {
									if (AranarthShopUtils.getShopCount(uuid) == COUNT_SHOP_AMOUNT) {
										if (aranarthPlayer.getIsMale()) {
											player.sendMessage(
													ChatUtils.translateToColor("&cA Count can only create up to "
															+ COUNT_SHOP_AMOUNT + " shops!"));
										} else {
											player.sendMessage(
													ChatUtils.translateToColor("&cA Countess can only create up to "
															+ COUNT_SHOP_AMOUNT + " shops!"));
										}
										return;
									}
								}
								// Either a Duke or a Prince
								else if (aranarthPlayer.getRank() < 7) {
									if (AranarthShopUtils.getShopCount(uuid) == DUKE_SHOP_AMOUNT) {
										if (aranarthPlayer.getRank() == 5) {
											if (aranarthPlayer.getIsMale()) {
												player.sendMessage(ChatUtils.translateToColor(
														"&cA Duke can only create up to " + DUKE_SHOP_AMOUNT + " shops!"));
											} else {
												player.sendMessage(
														ChatUtils.translateToColor("&cA Duchess can only create up to "
																+ DUKE_SHOP_AMOUNT + " shops!"));
											}
										} else {
											if (aranarthPlayer.getIsMale()) {
												player.sendMessage(
														ChatUtils.translateToColor("&cA Prince can only create up to "
																+ DUKE_SHOP_AMOUNT + " shops!"));
											} else {
												player.sendMessage(
														ChatUtils.translateToColor("&cA Princess can only create up to "
																+ DUKE_SHOP_AMOUNT + " shops!"));
											}
										}
										return;
									}
								}
								// Only a King
								else if (aranarthPlayer.getRank() < 8) {
									if (AranarthShopUtils.getShopCount(uuid) == KING_SHOP_AMOUNT) {
										if (aranarthPlayer.getIsMale()) {
											player.sendMessage(ChatUtils.translateToColor(
													"&cA King can only create up to " + KING_SHOP_AMOUNT + " shops!"));
										} else {
											player.sendMessage(ChatUtils.translateToColor(
													"&cA Queen can only create up to " + KING_SHOP_AMOUNT + " shops!"));
										}
										return;
									}
								}
							}

							Location signLocation = e.getClickedBlock().getLocation();
							Location chestLocation = new Location(player.getWorld(), signLocation.getBlockX(),
									signLocation.getBlockY() - 1, signLocation.getBlockZ());

							if (AranarthShopUtils.isAlreadyShop(chestLocation)) {
								if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
									return;
								}
								player.sendMessage(ChatUtils.translateToColor("&cThis is already a player shop!"));
								e.setCancelled(true);
								return;
							}

							if (chestLocation.getBlock().getType() != Material.CHEST) {
								player.sendMessage(ChatUtils.translateToColor("&cThere is no chest for this shop!"));
								return;
							}

							int transactionAmount = Integer.parseInt(sign.getLine(1));
							ItemStack item = new ItemStack(player.getInventory().getItemInMainHand().getType(), 1);
							if (item.getType() == Material.AIR) {
								player.sendMessage(ChatUtils.translateToColor("&cPlease select an item"));
								return;
							}
							player.getInventory().getItemInMainHand()
									.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

							String[] line3 = sign.getLine(2).split(" ");
							AranarthShop shop = null;

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
							player.sendMessage(ChatUtils.translateToColor("&aA player shop has been created!"));
							AranarthShopUtils.displayPlayerShopHologram(shop);
						} else {
							player.sendMessage(ChatUtils.translateToColor("&cYou cannot make a shop out of this!"));
						}
					} else if (canMakeAdminShop(sign, player)) {
						if (AranarthShopUtils.isItemWithoutMeta(player.getInventory().getItemInMainHand())) {
							
							Location signLocation = e.getClickedBlock().getLocation();
							// If the shop does not yet exist
							if (AranarthShopUtils.getServerShop(signLocation) == null) {
								if (player.getGameMode() != GameMode.CREATIVE) {
									player.sendMessage(ChatUtils.translateToColor("&cYou must be in creative to make a server shop!"));
									return;
								}
								
								AranarthShop shop = null;

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
										shop = new AranarthShop(null, transactionAmount, buyAmount, item, sellAmount,
												signLocation);
									} else {
										shop = new AranarthShop(null, transactionAmount, buyAmount, item, signLocation);
									}
								} else {
									double sellAmount = Double.parseDouble(line3[2]);
									shop = new AranarthShop(null, transactionAmount, item, sellAmount, signLocation);
								}
								AranarthShopUtils.addServerShop(shop);
								player.sendMessage(ChatUtils.translateToColor("&aA server shop has been created!"));
								e.setCancelled(true);
							} else {
								if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
									return;
								}
								player.sendMessage(ChatUtils.translateToColor("&cThis is already a server shop!"));
							}
						}
					} else if (!player.getName().equals(sign.getLine(0))) {
						player.sendMessage(ChatUtils.translateToColor("&cYou cannot create a shop that is not yours!"));
						return;
					} 

				}
			}
		}

	}

	private boolean canMakeAdminShop(Sign sign, Player player) {

		if (AranarthShopUtils.isProperShopFormat(sign, null, true)) {
			if (sign.getLine(0).equals("[Aranarth]") && player.hasPermission("aranarthcore.shop.create.admin")) {
				return true;
			} else {
				player.sendMessage(ChatUtils.translateToColor("&cYou cannot create server shops!"));
			}
		}
		return false;
	}

}
