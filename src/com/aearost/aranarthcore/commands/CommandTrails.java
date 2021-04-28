package com.aearost.aranarthcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandTrails implements CommandExecutor {

	/**
	 * All logic behind the /trails command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatUtils.chatMessage("&cThis command is not implemented yet!"));
		return true;
//		if (sender instanceof Player) {
//			Player player = (Player) sender;
//			if (AranarthPlayerUtils.getPlayer(player).getSaintStatus() == 0) {
//				player.sendMessage(ChatUtils.translateToColor("&cYou must be a Saint to use this command!"));
//				return false;
//			}
//			
//			TrailsGui trailsGui = new TrailsGui(player);
//			trailsGui.openGui();
//			return true;
//		}
//		return false;
		
	}

}
