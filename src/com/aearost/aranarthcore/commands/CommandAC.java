package com.aearost.aranarthcore.commands;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandAC implements CommandExecutor {

	/**
	 * All logic behind the /ac command, and all of its sub-commands as well.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("aranarthcore.admin.*")) {
			if (args.length > 0) {
				if (args[0].toLowerCase().equals("arenaarmor")) {
					if (args.length > 1) {
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							Player player = Bukkit.getPlayer(args[1]);
							if (player.getWorld().getName().toLowerCase().equals("arena")) {
								player.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
								player.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
								player.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
								player.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
								return true;
							} else {
								sender.sendMessage(ChatUtils.chatMessage("&cThat player is not in the arena world!"));
								return false;
							}
						} else {
							sender.sendMessage(ChatUtils.chatMessage("&7" + args[1] + " &ccould not be found!"));
							return false;
						}
					} else {
						sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac givearmor <player>"));
						return false;
					}
				} else if (args[0].toLowerCase().equals("eco")) {
					if (args.length > 1) {
						if (args[1].toLowerCase().equals("give") || args[1].toLowerCase().equals("reset")
								|| args[1].toLowerCase().equals("set") || args[1].toLowerCase().equals("take")) {
							if (args.length > 2) {
								// If they can be found in the file
								if (AranarthPlayerUtils.getUUID(args[2]) != null) {

									Player player = null;
									boolean isPlayerOnline = false;
									if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))) {
										player = Bukkit.getPlayer(args[2]);
										isPlayerOnline = true;
									}

									OfflinePlayer offlinePlayer = Bukkit
											.getOfflinePlayer(AranarthPlayerUtils.getUUID(args[2]));

									if (args.length == 4 && args[1].toLowerCase().equals("give")
											|| args[1].toLowerCase().equals("set")
											|| args[1].toLowerCase().equals("take")) {
										double amount = 0.00;
										NumberFormat formatter = NumberFormat.getCurrencyInstance();
										String amountAsMoney = "0.00";
										try {
											amount = Double.parseDouble(args[3]);
											amountAsMoney = formatter.format(amount);
										} catch (NumberFormatException e) {
											sender.sendMessage(ChatUtils.chatMessage("&cThat is not a valid number!"));
											return false;
										}

										if (amount == 0.00) {
											sender.sendMessage(
													ChatUtils.chatMessage("&Please supply a number greater than 0!"));
											return false;
										} else if (amount < 0) {
											sender.sendMessage(
													ChatUtils.chatMessage("&cYou must supply a positive number"));
											return false;
										} else {
											AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(offlinePlayer);
											if (args[1].toLowerCase().equals("give")) {
												double balance = aranarthPlayer.getBalance();
												balance += amount;
												aranarthPlayer.setBalance(balance);
												sender.sendMessage(ChatUtils
														.chatMessage("&6" + amountAsMoney + " &7has been added to &6"
																+ aranarthPlayer.getUsername() + "'s &7account"));
												if (isPlayerOnline) {
													player.sendMessage(ChatUtils.chatMessage(
															"&6" + amountAsMoney + " &7 has been added to your account!"));
												}
												return true;
											} else if (args[1].toLowerCase().equals("set")) {
												aranarthPlayer.setBalance(amount);
												sender.sendMessage(ChatUtils.chatMessage("&6" + aranarthPlayer.getUsername()
														+ "'s &7balance has been set to &6" + amountAsMoney));
												if (isPlayerOnline) {
													player.sendMessage(ChatUtils.chatMessage(
															"&7Your balance has been set to &6" + amountAsMoney + "&7!"));
												}
												return true;
											}
											// If the option was take
											else {
												double balance = aranarthPlayer.getBalance();
												if (balance == 0) {
													sender.sendMessage(ChatUtils
															.chatMessage("&cThis player does not have any money to take!"));
													return false;
												} else if (balance > amount) {
													balance -= amount;
													aranarthPlayer.setBalance(balance);
													sender.sendMessage(ChatUtils.chatMessage("&6" + amountAsMoney
															+ " &7has been taken from &6" + aranarthPlayer.getUsername()));
													if (isPlayerOnline) {
														player.sendMessage(ChatUtils.chatMessage(
																"&6" + amountAsMoney + " &7has been taken from you!"));
													}
													return true;
												} else {
													aranarthPlayer.setBalance(0);

													sender.sendMessage(ChatUtils.chatMessage("&7The rest of &6"
															+ aranarthPlayer.getUsername() + "'s &7money has been taken"));
													if (isPlayerOnline) {
														player.sendMessage(ChatUtils.chatMessage(
																"&7The rest of your money has been taken away!"));
													}
													return true;
												}
											}
										}
									}
									// If the option was reset
									else {
										AranarthPlayer aranarthPlayer = AranarthPlayerUtils
												.getPlayer(AranarthPlayerUtils.getUUID(args[2]));
										aranarthPlayer.setBalance(50);

										sender.sendMessage(ChatUtils.chatMessage(
												"&6" + aranarthPlayer.getUsername() + "'s &7balance has been reset"));
										if (isPlayerOnline) {
											player.sendMessage(ChatUtils.chatMessage("&7Your balance has been reset!"));
										}
										return true;
									}
								} else {
									sender.sendMessage(ChatUtils.chatMessage("&cThat player could not be found"));
									return false;
								}
							}
						}
					}
					sender.sendMessage(ChatUtils
							.chatMessage("&7Proper Usage: &e/ac eco <give | reset | set | take> <player> [amount]"));
					return false;
				} else if (args[0].toLowerCase().equals("set")) {
					if (args.length > 1) {
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							Player player = Bukkit.getPlayer(args[1]);
							if (args.length >= 3) {
								AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
								CommandSender commandSender = Bukkit.getServer().getConsoleSender();
								if (args[2].toLowerCase().equals("avatar")) {
									String previousAvatar = AranarthPlayerUtils.replaceAvatar(player);
									Bukkit.broadcastMessage(ChatUtils.translateToColor(
											"&5&lAvatar &d&l" + previousAvatar + " &5&lhas passed away..."));
									Bukkit.broadcastMessage(ChatUtils.translateToColor(
											"&5&lWelcome their successor, Avatar &d&l" + player.getName() + " &5&l!"));

									for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
										onlinePlayer.playSound(onlinePlayer.getLocation(),
												Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F, 2.0F);
									}
									Bukkit.dispatchCommand(commandSender,
											"bending remove " + player.getName() + " chi");
									Bukkit.dispatchCommand(commandSender, "bending add air " + player.getName());
									Bukkit.dispatchCommand(commandSender, "bending add water " + player.getName());
									Bukkit.dispatchCommand(commandSender, "bending add earth " + player.getName());
									Bukkit.dispatchCommand(commandSender, "bending add fire " + player.getName());

									ChatUtils.updatePlayerPrefixAndRank(player);
									ChatUtils.updatePlayerPrefixAndRank(Bukkit.getPlayer(previousAvatar));
									return true;
								} else if (args[2].toLowerCase().equals("saint1")) {
									aranarthPlayer.setSaintStatus(1);
									AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
									Bukkit.dispatchCommand(commandSender, "manselect Theia");
									Bukkit.dispatchCommand(commandSender, "manuaddsub " + player.getName() + "saint1");
									Bukkit.dispatchCommand(commandSender, "manselect Arena");
									Bukkit.dispatchCommand(commandSender, "manuaddsub " + player.getName() + "saint1");
									ChatUtils.updatePlayerPrefixAndRank(player);
									return true;
								} else if (args[2].toLowerCase().equals("saint2")) {
									aranarthPlayer.setSaintStatus(2);
									AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
									Bukkit.dispatchCommand(commandSender, "manselect Theia");
									Bukkit.dispatchCommand(commandSender, "manuaddsub " + player.getName() + "saint2");
									Bukkit.dispatchCommand(commandSender, "manselect Arena");
									Bukkit.dispatchCommand(commandSender, "manuaddsub " + player.getName() + "saint2");
									ChatUtils.updatePlayerPrefixAndRank(player);
									return true;
								} else if (args[2].toLowerCase().equals("saint3")) {
									aranarthPlayer.setSaintStatus(3);
									AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
									Bukkit.dispatchCommand(commandSender, "manselect Theia");
									Bukkit.dispatchCommand(commandSender, "manuaddsub " + player.getName() + "saint3");
									Bukkit.dispatchCommand(commandSender, "manselect Arena");
									Bukkit.dispatchCommand(commandSender, "manuaddsub " + player.getName() + "saint3");
									ChatUtils.updatePlayerPrefixAndRank(player);
									return true;
								} else if (args[2].toLowerCase().equals("council1")) {
									aranarthPlayer.setCouncilStatus(1);
									AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
									Bukkit.dispatchCommand(commandSender, "manselect Theia");
									Bukkit.dispatchCommand(commandSender,
											"manuaddsub " + player.getName() + "councilhelper");
									Bukkit.dispatchCommand(commandSender, "manselect Arena");
									Bukkit.dispatchCommand(commandSender,
											"manuaddsub " + player.getName() + "councilhelper");
									ChatUtils.updatePlayerPrefixAndRank(player);
									return true;
								} else if (args[2].toLowerCase().equals("council2")) {
									aranarthPlayer.setCouncilStatus(2);
									AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
									Bukkit.dispatchCommand(commandSender, "manselect Theia");
									Bukkit.dispatchCommand(commandSender,
											"manuaddsub " + player.getName() + "councilmoderator");
									Bukkit.dispatchCommand(commandSender, "manselect Arena");
									Bukkit.dispatchCommand(commandSender,
											"manuaddsub " + player.getName() + "councilmoderator");
									ChatUtils.updatePlayerPrefixAndRank(player);
								} else if (args[2].toLowerCase().equals("council3")) {
									aranarthPlayer.setCouncilStatus(3);
									AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
									Bukkit.dispatchCommand(commandSender, "manselect Theia");
									Bukkit.dispatchCommand(commandSender,
											"manuaddsub " + player.getName() + "counciladmin");
									Bukkit.dispatchCommand(commandSender, "manselect Arena");
									Bukkit.dispatchCommand(commandSender,
											"manuaddsub " + player.getName() + "counciladmin");
								} else {
									player.sendMessage(
											ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <rank>"));
									return false;
								}
							}
						} else {
							sender.sendMessage(ChatUtils.chatMessage("&7" + args[1] + " &ccould not be found!"));
							return false;
						}
					} else {
						sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <rank>"));
						return false;
					}
				}
			}
		}

		// Non-restricted commands
		if (args.length > 0) {
			if (args[0].toLowerCase().equals("title")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length >= 2) {
						if (args[1].toLowerCase().equals("male")) {
							AranarthPlayerUtils.setIsMale(player, true);
							player.sendMessage(
									ChatUtils.chatMessage("&7Your rank titles will now be displayed as a male."));
							return true;
						} else if (args[1].toLowerCase().equals("female")) {
							AranarthPlayerUtils.setIsMale(player, false);
							player.sendMessage(
									ChatUtils.chatMessage("&7Your rank titles will now be displayed as a female."));
							return true;
						}
					}
					sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac title <male | female>"));
					return false;
				} else {
					sender.sendMessage(ChatUtils.chatMessage("&c&lYou must be a player to use this command!"));
					return false;
				}
			}
		}

		sender.sendMessage(ChatUtils.translateToColor("&8      - - - &6&lAranarth Core &8- - -"));
		if (sender.hasPermission("aranarthcore.admin.*")) {
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &earenaarmor <player>"));
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &eeco <give | reset | set | take> <player> [amount]"));
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &eset <player> <rank>"));
		}
		sender.sendMessage(ChatUtils.translateToColor("&7/ac &etitle <male | female>"));
		return true;
	}
}
