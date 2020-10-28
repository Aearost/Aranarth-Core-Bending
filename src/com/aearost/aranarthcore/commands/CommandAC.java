package com.aearost.aranarthcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandAC implements CommandExecutor {

	/**
	 * All logic behind the /ac command, and all of its sub-commands as well.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("aranarthcore.admin.*")) {
			if (args.length > 0) {
				if (args[0].toLowerCase().equals("arenaarmor")) {
					if (args.length > 1) {
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							Player player = Bukkit.getPlayer(args[1]);
							if (player.getWorld().getName().toLowerCase().equals("arena")) {
								player.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
								player.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
								player.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
								player.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
								return true;
							} else {
								sender.sendMessage(ChatUtils.chatMessage("&cThat player is not in the arena world!"));
								return false;
							}
						} else {
							sender.sendMessage(ChatUtils.chatMessage("&7" + args[1] + " &ccould not be found!"));
							return false;
						}
					} else {
						sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac givearmor <player>"));
						return false;
					}
				} else if (args[0].toLowerCase().equals("set")) {
					if (args.length > 1) {
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							Player player = Bukkit.getPlayer(args[1]);
							if (args.length >= 3) {
								if (args[2].toLowerCase().equals("avatar")) {
									String previousAvatar = AranarthPlayerUtils.setAvatar(player);
									Bukkit.broadcastMessage(ChatUtils.translateToColor("&5&lAvatar &d&l" + previousAvatar
											+ " &5&lhas passed away, and &d&l" + player.getName() + " &5&lhas inherited their powers!"));
									for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
										onlinePlayer.playSound(onlinePlayer.getLocation(),
												Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F, 2.0F);
									}
									return true;
								} else if (args[2].toLowerCase().equals("saint1")) {

								} else if (args[2].toLowerCase().equals("saint2")) {

								} else if (args[2].toLowerCase().equals("saint3")) {

								} else {
									player.sendMessage(
											ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <rank>"));
								}
							}
						} else {
							sender.sendMessage(ChatUtils.chatMessage("&7" + args[1] + " &ccould not be found!"));
							return false;
						}
					}
				}
			}
		}

		// Non-restricted commands
		if (args.length > 0) {
			if (args[0].toLowerCase().equals("title")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length >= 2) {
						if (args[1].toLowerCase().equals("male")) {
							AranarthPlayerUtils.setIsMale(player, true);
							return true;
						} else if (args[1].toLowerCase().equals("female")) {
							AranarthPlayerUtils.setIsMale(player, false);
							return true;
						}
					}
					sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac title {male | female}"));
					return false;
				} else {
					sender.sendMessage(ChatUtils.chatMessage("&c&lYou must be a player to use this command!"));
					return false;
				}
			}
		}

		sender.sendMessage(ChatUtils.translateToColor("&8      - - - &6&lAranarth Core &8- - -"));
		if (sender.hasPermission("aranarthcore.admin.*")) {
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &earenaarmor <player>"));
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &eset <player> <rank>"));
		}
		sender.sendMessage(ChatUtils.translateToColor("&7/ac &etitle {male | female}"));
		return true;
	}

}
