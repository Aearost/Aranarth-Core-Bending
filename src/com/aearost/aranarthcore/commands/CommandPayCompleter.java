package com.aearost.aranarthcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandPayCompleter implements TabCompleter {

	/**
	 * Handles the auto complete functionality while using the /ac command, and all
	 * of its sub-commands.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> displayedOptions = new ArrayList<>();
		
		return displayedOptions;
	}
}
