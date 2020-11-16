package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandRulesCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /ranks command.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();
		if (args.length == 1) {
			if (!args[0].equals("") && "arena".startsWith(args[0])) {
				displayedOptions.add("arena");
			} else if (!args[0].equals("") && "general".startsWith(args[0])) {
				displayedOptions.add("general");
			} else {
				displayedOptions.add("arena");
				displayedOptions.add("general");
			}
		}
		return displayedOptions;
	}

}
