package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandRanksCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /ranks command.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();
		displayedOptions.add("ranks");
		return displayedOptions;
	}
}
