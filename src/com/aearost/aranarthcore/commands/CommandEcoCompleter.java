package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandEcoCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /eco command.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();
		
		if (args.length == 1) {
			if (!args[0].equals("") && "give".startsWith(args[0])) {
				displayedOptions.add("give");
			} else if (!args[0].equals("") && "reset".startsWith(args[0])) {
				displayedOptions.add("reset");
			} else if (!args[0].equals("") && "set".startsWith(args[0])) {
				displayedOptions.add("set");
			} else if (!args[0].equals("") && "take".startsWith(args[0])) {
				displayedOptions.add("take");
			} else {
				displayedOptions.add("give");
				displayedOptions.add("reset");
				displayedOptions.add("set");
				displayedOptions.add("take");
			}
		} else if (args.length == 2) {
			Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
			Bukkit.getOnlinePlayers().toArray(onlinePlayers);
			for (int i = 0; i < onlinePlayers.length; i++) {
				if (onlinePlayers[i].getName().toLowerCase().startsWith(args[1].toLowerCase())) {
					displayedOptions.add(onlinePlayers[i].getName());
				}
			}
		} else if (args.length >= 3
				&& (args[0].equals("give") || args[0].equals("set") || args[0].equals("take"))) {
			if (args[2].equals("")) {
				displayedOptions.add("1");
				displayedOptions.add("100");
				displayedOptions.add("1000");
			}
		}
		
		return displayedOptions;
	}

}


