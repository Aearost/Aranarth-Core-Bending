package com.aearost.aranarthcore.commands;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;

public class CommandEco implements CommandExecutor {

	/**
	 * All logic behind the /eco command, as well as all of its sub-commands.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("aranarthcore.eco")) {
			if (args.length >= 2) {
				if (args[0].toLowerCase().equals("give") || args[0].toLowerCase().equals("reset")
						|| args[0].toLowerCase().equals("set") || args[0].toLowerCase().equals("take")) {
					// If they can be found in the file
					if (AranarthPlayerUtils.getUUID(args[1]) != null) {

						Player player = null;
						boolean isPlayerOnline = false;
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							player = Bukkit.getPlayer(args[1]);
							isPlayerOnline = true;
						}
						NumberFormat formatter = NumberFormat.getCurrencyInstance();
						OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(AranarthPlayerUtils.getUUID(args[1]));

						if (args.length >= 3 && args[0].toLowerCase().equals("give")
								|| args[0].toLowerCase().equals("set") || args[0].toLowerCase().equals("take")) {
							double amount = 0.00;
							
							String amountAsMoney = "0.00";
							try {
								amount = Double.parseDouble(args[2]);
								amountAsMoney = formatter.format(amount);
							} catch (NumberFormatException e) {
								sender.sendMessage(ChatUtils.translateToColor("&cThat is not a valid number!"));
								return false;
							}

							if (amount == 0.00) {
								sender.sendMessage(
										ChatUtils.translateToColor("&Please supply a number greater than 0!"));
								return false;
							} else if (amount < 0) {
								sender.sendMessage(ChatUtils.translateToColor("&cYou must supply a positive number"));
								return false;
							} else {
								AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(offlinePlayer);
								if (args[0].toLowerCase().equals("give")) {
									double balance = aranarthPlayer.getBalance();
									balance += amount;
									PersistenceUtils.logTransaction(aranarthPlayer.getUsername() + " (" + formatter.format(balance)
											+ ") has been given " + amountAsMoney);
									aranarthPlayer.setBalance(balance);
									sender.sendMessage(
											ChatUtils.translateToColor("&6" + amountAsMoney + " &7has been added to &6"
													+ aranarthPlayer.getUsername() + "'s &7account"));
									if (isPlayerOnline) {
										player.sendMessage(ChatUtils.translateToColor(
												"&6" + amountAsMoney + " &7 has been added to your account!"));
									}
									return true;
								} else if (args[0].toLowerCase().equals("set")) {
									PersistenceUtils.logTransaction(
											aranarthPlayer.getUsername() + "'s (" + formatter.format(aranarthPlayer.getBalance())
													+ ") balance has been set to " + amountAsMoney);
									aranarthPlayer.setBalance(amount);
									sender.sendMessage(ChatUtils.translateToColor("&6" + aranarthPlayer.getUsername()
											+ "'s &7balance has been set to &6" + amountAsMoney));
									if (isPlayerOnline) {
										player.sendMessage(ChatUtils.translateToColor(
												"&7Your balance has been set to &6" + amountAsMoney + "&7!"));
									}
									return true;
								}
								// If the option was take
								else {
									double balance = aranarthPlayer.getBalance();
									if (balance == 0) {
										sender.sendMessage(ChatUtils
												.translateToColor("&cThis player does not have any money to take!"));
										return false;
									} else if (balance > amount) {
										balance -= amount;
										PersistenceUtils.logTransaction(
												amountAsMoney + " has been taken from " + aranarthPlayer.getUsername()
														+ " (" + formatter.format(aranarthPlayer.getBalance()) + ")");
										aranarthPlayer.setBalance(balance);
										sender.sendMessage(ChatUtils.translateToColor("&6" + amountAsMoney
												+ " &7has been taken from &6" + aranarthPlayer.getUsername()));
										if (isPlayerOnline) {
											player.sendMessage(ChatUtils.translateToColor(
													"&6" + amountAsMoney + " &7has been taken from you!"));
										}
										return true;
									} else {
										PersistenceUtils.logTransaction("The rest of " + aranarthPlayer.getUsername()
												+ "'s (" + formatter.format(aranarthPlayer.getBalance()) + ") balance has been taken");
										aranarthPlayer.setBalance(0);
										sender.sendMessage(ChatUtils.translateToColor("&7The rest of &6"
												+ aranarthPlayer.getUsername() + "'s &7money has been taken"));
										if (isPlayerOnline) {
											player.sendMessage(ChatUtils
													.translateToColor("&7The rest of your money has been taken away!"));
										}
										return true;
									}
								}
							}
						}
						// If the option was reset
						else {
							AranarthPlayer aranarthPlayer = AranarthPlayerUtils
									.getPlayer(AranarthPlayerUtils.getUUID(args[1]));
							PersistenceUtils.logTransaction(aranarthPlayer.getUsername() + "'s ("
									+ formatter.format(aranarthPlayer.getBalance()) + ") balance has been reset");
							aranarthPlayer.setBalance(50);

							sender.sendMessage(ChatUtils.translateToColor(
									"&6" + aranarthPlayer.getUsername() + "'s &7balance has been reset"));
							if (isPlayerOnline) {
								player.sendMessage(ChatUtils.translateToColor("&7Your balance has been reset!"));
							}
							return true;
						}
					} else {
						sender.sendMessage(ChatUtils.translateToColor("&cThat player could not be found"));
						return false;
					}
				}
			}
			sender.sendMessage(ChatUtils.translateToColor("&7Proper Usage: &e/eco <argument> <player> [amount]"));
		}
		return false;

	}
}
