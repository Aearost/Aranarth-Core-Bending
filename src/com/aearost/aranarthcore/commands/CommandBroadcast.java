package com.aearost.aranarthcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandBroadcast implements CommandExecutor {

	/**
	 * All logic behind the /broadcast command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatUtils.translateToColor("&cYou must enter a message to broadcast first!"));
			return false;
		} else {
			String message = args[0];
			if (args.length > 1) {
				message += " ";
			}
			for (int i = 1; i < args.length; i++) {
				if (i != args.length - 1) {
					message += args[i] + " ";
				} else {
					message += args[i];
				}
			}
			Bukkit.broadcastMessage(ChatUtils.chatMessage(message));
			return true;
		}
	}

}
