package com.aearost.aranarthcore.commands;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandPay implements CommandExecutor {

	/**
	 * All logic behind the /pay command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 1) {
				
				if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
					Player playerToPay = Bukkit.getPlayer(args[0]);
					
					AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
					AranarthPlayer aranarthPlayerToPay = AranarthPlayerUtils.getPlayer(playerToPay);

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
								aranarthPlayer.setBalance(aranarthPlayer.getBalance() - amount);
								AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
								player.sendMessage(ChatUtils.translateToColor(
										"&aYou have sent &6&l" + formattedAmount + " &ato &e" + playerToPay.getName()));
								aranarthPlayerToPay.setBalance(aranarthPlayerToPay.getBalance() + amount);
								AranarthPlayerUtils.addPlayer(playerToPay.getUniqueId(), aranarthPlayerToPay);
								playerToPay.sendMessage(ChatUtils.translateToColor(
										"&aYou have received &6&l" +formattedAmount + " &afrom &e" + player.getName()));
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
