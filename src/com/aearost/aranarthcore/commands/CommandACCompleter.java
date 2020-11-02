package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandACCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /ac command, and all
	 * of its sub-commands.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();

		if ("help".startsWith(args[0]) && args[0].length() > 0) {
			displayedOptions.add("help");
		} else if ("arenaarmor".startsWith(args[0]) && args[0].length() > 0) {
			if (args.length == 1) {
				displayedOptions.add("arenaarmor");
			} else if (args.length == 2) {
				Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
				Bukkit.getOnlinePlayers().toArray(onlinePlayers);
				for (int i = 0; i < onlinePlayers.length; i++) {
					if (onlinePlayers[i].getName().toLowerCase().startsWith(args[1].toLowerCase())) {
						displayedOptions.add(onlinePlayers[i].getName());
					}
				}
			}
		} else if ("set".startsWith(args[0]) && args[0].length() > 0) {
			if (args.length == 1 && !args[0].equals("set")) {
				displayedOptions.add("set");
			} else if (args.length == 2) {
				Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
				Bukkit.getOnlinePlayers().toArray(onlinePlayers);
				for (int i = 0; i < onlinePlayers.length; i++) {
					if (onlinePlayers[i].getName().toLowerCase().startsWith(args[1].toLowerCase())) {
						displayedOptions.add(onlinePlayers[i].getName());
					}
				}
			} else if (args.length == 3) {
				if (!args[2].equals("") && "avatar".startsWith(args[2].toLowerCase())) {
					displayedOptions.add("avatar");
				} else if (!args[2].equals("") && "saint".startsWith(args[2].toLowerCase())) {
					displayedOptions.add("saint1");
					displayedOptions.add("saint2");
					displayedOptions.add("saint3");
				} else if (!args[2].equals("") && "saint1".equals(args[2].toLowerCase())) {
					displayedOptions.add("saint1");
				} else if (!args[2].equals("") && "saint2".equals(args[2].toLowerCase())) {
					displayedOptions.add("saint2");
				} else if (!args[2].equals("") && "saint3".equals(args[2].toLowerCase())) {
					displayedOptions.add("saint3");
				} else if (!args[2].equals("") && "council".startsWith(args[2].toLowerCase())) {
					displayedOptions.add("council1");
					displayedOptions.add("council2");
					displayedOptions.add("council3");
				} else if (!args[2].equals("") && "council1".equals(args[2].toLowerCase())) {
					displayedOptions.add("council1");
				} else if (!args[2].equals("") && "council2".equals(args[2].toLowerCase())) {
					displayedOptions.add("council2");
				} else if (!args[2].equals("") && "council3".equals(args[2].toLowerCase())) {
					displayedOptions.add("council3");
				} else {
					displayedOptions.add("avatar");
					displayedOptions.add("saint1");
					displayedOptions.add("saint2");
					displayedOptions.add("saint3");
					displayedOptions.add("council1");
					displayedOptions.add("council2");
					displayedOptions.add("council3");
				}
			}
		} else if ("title".startsWith(args[0]) && args[0].length() > 0) {
			if (args.length == 1 && !args[0].equals("title")) {
				displayedOptions.add("title");
			} else if (args.length == 2) {
				if (!args[1].equals("") && "male".startsWith(args[1])) {
					displayedOptions.add("male");
				} else if (!args[1].equals("") && "female".startsWith(args[1])) {
					displayedOptions.add("female");
				} else {
					displayedOptions.add("female");
					displayedOptions.add("male");
				}
			}
		} else if (args.length == 1) {
			displayedOptions.add("arenaarmor");
			displayedOptions.add("eco");
			displayedOptions.add("set");
			displayedOptions.add("title");
		}

		return displayedOptions;
	}

}
