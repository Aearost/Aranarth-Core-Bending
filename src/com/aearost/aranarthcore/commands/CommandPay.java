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

public class CommandPay implements CommandExecutor {

	/**
	 * All logic behind the /pay command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length > 0) {
				
				if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
					sender.sendMessage(ChatUtils.translateToColor("&cYou cannot pay yourself!"));
					return false;
				}

				if (AranarthPlayerUtils.getUUID(args[0]) != null) {
					boolean isPlayerOnline = false;
					if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
						isPlayerOnline = true;
					}

					OfflinePlayer offlinePlayerToPay = Bukkit.getOfflinePlayer(AranarthPlayerUtils.getUUID(args[0]));

					AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
					AranarthPlayer aranarthPlayerToPay = AranarthPlayerUtils.getPlayer(offlinePlayerToPay);

					if (args.length > 1) {
						NumberFormat formatter = NumberFormat.getCurrencyInstance();
						String formattedAmount = "";
						double amount = 0.00;

						try {
							amount = Double.parseDouble(args[1]);
							formattedAmount = formatter.format(amount);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatUtils.translateToColor("&cThat is not a valid number!"));
							return false;
						}

						if (amount < 0.00) {
							player.sendMessage(ChatUtils.translateToColor("&cYou must use a positive number!"));
							return false;
						} else if (amount == 0.00) {
							player.sendMessage(ChatUtils.translateToColor("&cYou cannot pay someone &6&l$0.00&c!"));
							return false;
						} else {
							if (aranarthPlayer.getBalance() < amount) {
								player.sendMessage(
										ChatUtils.translateToColor("&cYou do not have enough money for this!"));
								return false;
							} else {
								if (amount >= 250) {
									PersistenceUtils.logTransaction(aranarthPlayer.getUsername() + " ("
											+ formatter.format(aranarthPlayer.getBalance()) + ") has sent "
											+ aranarthPlayerToPay.getUsername() + " ("
											+ formatter.format(aranarthPlayerToPay.getBalance()) + ") " + formattedAmount);
								}
								aranarthPlayer.setBalance(aranarthPlayer.getBalance() - amount);
								AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
								player.sendMessage(ChatUtils.translateToColor("&aYou have sent &6&l" + formattedAmount
										+ " &ato &e" + aranarthPlayerToPay.getUsername()));
								aranarthPlayerToPay.setBalance(aranarthPlayerToPay.getBalance() + amount);
								if (isPlayerOnline) {
									Player playerToPlayOnline = Bukkit.getPlayer(args[0]);
									playerToPlayOnline.sendMessage(ChatUtils.translateToColor("&aYou have received &6&l"
											+ formattedAmount + " &afrom &e" + player.getName()));
								}
								return true;
							}
						}
					}
				}
			}
			sender.sendMessage(ChatUtils.translateToColor("&7Proper Usage: &e/pay <player> <amount>"));
		} else {
			sender.sendMessage(ChatUtils.translateToColor("&cYou must be a player to use this command!"));
		}
		return false;
	}
}
