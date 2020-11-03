package com.aearost.aranarthcore.commands;

import java.text.NumberFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandBalance implements CommandExecutor {

	/**
	 * All logic behind the /balance command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
				player.sendMessage(ChatUtils.translateToColor(
						"&7Your current balance is &6&l" + formatter.format(aranarthPlayer.getBalance())));
				return true;
			} else {
				sender.sendMessage(ChatUtils.translateToColor("&cYou must be a player to use this command!"));
				return false;
			}
		} else {
			if (AranarthPlayerUtils.getUUID(args[0]) != null) {
				
				AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(AranarthPlayerUtils.getUUID(args[0]));
				
				sender.sendMessage(ChatUtils.translateToColor("&e" + aranarthPlayer.getUsername() + "'s &acurrent balance is &6&l"
						+ formatter.format(aranarthPlayer.getBalance())));
				return true;
			} else {
				sender.sendMessage(ChatUtils.translateToColor("&cThis player could not be found!"));
				return false;
			}
		}
	}
}
