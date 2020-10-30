package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandPayCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /ac command, and all
	 * of its sub-commands.
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
		} else if (args.length == 2) {
			if (args[1].equals("")) {
				displayedOptions.add("1");
				displayedOptions.add("100");
				displayedOptions.add("1000");
			}
		}
		
		return displayedOptions;
	}
}
