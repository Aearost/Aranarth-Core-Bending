package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandBalancetopCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /balancetop command.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();
		
		if (args.length > 0) {
			displayedOptions.add("1");
			displayedOptions.add("2");
			displayedOptions.add("3");
		}
		
		return displayedOptions;
	}
}
