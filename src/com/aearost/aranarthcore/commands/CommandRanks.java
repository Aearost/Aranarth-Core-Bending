package com.aearost.aranarthcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.gui.RanksGui;

public class CommandRanks implements CommandExecutor {

	/**
	 * All logic behind the /ranks command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			RanksGui ranksGui = new RanksGui(player);
			ranksGui.openGui();
			return true;
		}
		return false;
		
	}
	
}