package com.aearost.aranarthcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandBuy implements CommandExecutor {

	/**
	 * All logic behind the /buy command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Bukkit.broadcastMessage(ChatUtils.translateToColor("&8[&6Aranarth&8] &7Shop URL: &ehttp://aranarth.craftingstore.net/"));
		return true;
	}

}
