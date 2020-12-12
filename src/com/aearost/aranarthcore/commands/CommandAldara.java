package com.aearost.aranarthcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandAldara implements CommandExecutor {

	/**
	 * All logic behind the /map command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatUtils.translateToColor("&8[&6Aranarth&8] &7Aldara: &ehttps://tinyurl.com/y385zucc"));
		return true;
	}

}
