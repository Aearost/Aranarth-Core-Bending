package com.aearost.aranarthcore.commands;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthPlayer;
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
				// arenaarmor
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
				}

				// arenareset
				else if (args[0].toLowerCase().equals("arenareset")) {
					if (args.length > 1 && args[1].toLowerCase().startsWith("arena")) {
						Bukkit.broadcastMessage(ChatUtils.chatMessage("&e" + args[1] + " &7is being reset"));
						return true;
					}
					return false;
				}

				// set
				else if (args[0].toLowerCase().equals("set")) {
					if (args.length > 1) {
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							Player player = Bukkit.getPlayer(args[1]);
							if (args.length >= 3) {

								boolean isSuccess = setPlayerGroups(sender, player, args);
								if (isSuccess) {
									ChatUtils.updatePlayerGroupsAndPrefix(player);
									return true;
								} else {
									return false;
								}
							}
						} else {
							sender.sendMessage(ChatUtils.chatMessage("&7" + args[1] + " &ccould not be found!"));
							return false;
						}
					} else {
						sender.sendMessage(
								ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <variable> [rank]"));
						return false;
					}
				}

				// stats
				else if (args[0].toLowerCase().equals("stats")) {
					if (args.length > 1) {
						if (AranarthPlayerUtils.getPlayer(AranarthPlayerUtils.getUUID(args[1])) != null) {
							AranarthPlayer aranarthPlayer = AranarthPlayerUtils
									.getPlayer(Bukkit.getOfflinePlayer(AranarthPlayerUtils.getUUID(args[1])));
							NumberFormat formatter = NumberFormat.getCurrencyInstance();
							sender.sendMessage(ChatUtils.translateToColor("&6&l&n" + args[1] + "'s Aranarth Stats"));
							sender.sendMessage(ChatUtils.translateToColor("&aRank: &e" + aranarthPlayer.getRank()));
							sender.sendMessage(ChatUtils.translateToColor("&aMale: &e" + aranarthPlayer.getIsMale()));
							sender.sendMessage(ChatUtils
									.translateToColor("&aBalance: &e" + formatter.format(aranarthPlayer.getBalance())));
							sender.sendMessage(ChatUtils
									.translateToColor("&aCouncil Status: &e" + aranarthPlayer.getCouncilStatus()));
							return true;
						}
					}
				}

				// unset
				else if (args[0].toLowerCase().equals("unset")) {
					if (args.length > 1) {
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
							Player player = Bukkit.getPlayer(args[1]);
							if (args.length >= 3) {

								boolean isSuccess = unsetPlayerGroups(sender, player, args);
								if (isSuccess) {
									ChatUtils.updatePlayerGroupsAndPrefix(player);
									return true;
								} else {
									return true;
								}
							}
						} else {
							sender.sendMessage(ChatUtils.chatMessage("&7" + args[1] + " &ccould not be found!"));
							return false;
						}
					} else {
						sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <rank>"));
						return false;
					}
				}
			}
		}

		// Non-restricted commands
		if (args.length > 0) {
			// title
			if (args[0].toLowerCase().equals("title")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length >= 2) {
						if (args[1].toLowerCase().equals("male")) {
							if (AranarthPlayerUtils.getPlayer(player).getIsMale()) {
								player.sendMessage(
										ChatUtils.chatMessage("&cYour titles are already displayed as male!"));
								return false;
							}
							AranarthPlayerUtils.setIsMale(player, true);
							player.sendMessage(
									ChatUtils.chatMessage("&7Your rank titles will now be displayed as a male."));
						} else if (args[1].toLowerCase().equals("female")) {
							if (!AranarthPlayerUtils.getPlayer(player).getIsMale()) {
								player.sendMessage(
										ChatUtils.chatMessage("&cYour titles are already displayed as female!"));
								return false;
							}
							AranarthPlayerUtils.setIsMale(player, false);
							player.sendMessage(
									ChatUtils.chatMessage("&7Your rank titles will now be displayed as a female."));
						}
						ChatUtils.updatePlayerGroupsAndPrefix(player);
						return true;
					}
					sender.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac title <male | female>"));
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
		}
		sender.sendMessage(ChatUtils.translateToColor("&7/ac &ehelp"));
		if (sender.hasPermission("aranarthcore.admin.*")) {
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &eset <player> <variable> [rank]"));
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &estats <player>"));
		}
		sender.sendMessage(ChatUtils.translateToColor("&7/ac &etitle <male | female>"));
		if (sender.hasPermission("aranarthcore.admin.*")) {
			sender.sendMessage(ChatUtils.translateToColor("&7/ac &eunset <player> <rank>"));
		}
		return true;
	}

	/**
	 * Sets a player's group and subgroup, based on the fields of their
	 * AranarthPlayer object.
	 * 
	 * @param sender
	 * @param player
	 * @param args
	 * @return
	 */
	private boolean setPlayerGroups(CommandSender sender, Player player, String[] args) {
		return false;
	}

	/**
	 * Unsets a player's group and subgroup, based on the fields of their
	 * AranarthPlayer object.
	 * 
	 * @param sender
	 * @param player
	 * @param args
	 * @return
	 */
	private boolean unsetPlayerGroups(CommandSender sender, Player player, String[] args) {
		return false;
	}

}
