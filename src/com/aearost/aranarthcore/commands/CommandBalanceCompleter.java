package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandBalanceCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /balance command.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();
		
		if (args.length == 1) {
			Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
			Bukkit.getOnlinePlayers().toArray(onlinePlayers);
			for (int i = 0; i < onlinePlayers.length; i++) {
				if (onlinePlayers[i].getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					displayedOptions.add(onlinePlayers[i].getName());
				}
			}
		}
		
		return displayedOptions;
	}
}
