package com.aearost.aranarthcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aearost.aranarthcore.gui.TrailsGui;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandTrails implements CommandExecutor {

	/**
	 * All logic behind the /trails command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (AranarthPlayerUtils.getPlayer(player).getSaintStatus() == 0) {
				player.sendMessage(ChatUtils.translateToColor("&cYou must be a Saint to use this command!"));
			}
			
			TrailsGui trailsGui = new TrailsGui(player);
			trailsGui.openGui();
			return true;
		}
		return false;
		
	}

}
