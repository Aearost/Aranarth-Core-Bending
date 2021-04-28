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
		} else if ((args[0].startsWith("s") || "unset".startsWith(args[0])) && args[0].length() > 0) {
			if (args[0].length() == 1) {
				if (args[0].startsWith("s")) {
					displayedOptions.add("set");
					displayedOptions.add("stats");
				} else {
					displayedOptions.add("unset");
				}
			} else if (("set".startsWith(args[0]) || "unset".startsWith(args[0])) && args[0].length() > 0) {
				if (args.length == 1) {
					if ("set".startsWith(args[0])) {
						displayedOptions.add("set");
					} else {
						displayedOptions.add("unset");
					}
				} else if (args.length == 2) {
					Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
					Bukkit.getOnlinePlayers().toArray(onlinePlayers);
					for (int i = 0; i < onlinePlayers.length; i++) {
						if (onlinePlayers[i].getName().toLowerCase().startsWith(args[1].toLowerCase())) {
							displayedOptions.add(onlinePlayers[i].getName());
						}
					}
				} else if (args.length == 3) {
					if (!args[2].equals("") && "rank".startsWith(args[2].toLowerCase())) {
						displayedOptions.add("rank");
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
						displayedOptions.add("council1");
						displayedOptions.add("council2");
						displayedOptions.add("council3");
						displayedOptions.add("rank");
					}
				} else if (args.length == 4 && args[2].equals("rank")) {
					if (!args[3].equals("") && "fix".startsWith(args[3].toLowerCase())) {
						displayedOptions.add("fix");
					} else if (!args[3].equals("") && "these".startsWith(args[3].toLowerCase())) {
						displayedOptions.add("these");
					} else if (!args[3].equals("") && "options".startsWith(args[3].toLowerCase())) {
						displayedOptions.add("options");
					} else {
						displayedOptions.add("fix");
						displayedOptions.add("these");
						displayedOptions.add("options");
					}
				}
			} else if ("stats".startsWith(args[0]) && args[0].length() > 0) {
				if (args.length == 1) {
					displayedOptions.add("stats");
				} else {
					Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
					Bukkit.getOnlinePlayers().toArray(onlinePlayers);
					for (int i = 0; i < onlinePlayers.length; i++) {
						if (onlinePlayers[i].getName().toLowerCase().startsWith(args[1].toLowerCase())) {
							displayedOptions.add(onlinePlayers[i].getName());
						}
					}
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
			if (sender.hasPermission("aranarthcore.admin.*")) {
				displayedOptions.add("arenaarmor");
				displayedOptions.add("set");
				displayedOptions.add("stats");
			}

			displayedOptions.add("title");

			if (sender.hasPermission("aranarthcore.admin.*")) {
				displayedOptions.add("unset");
			}
		}

		return displayedOptions;
	}

}
