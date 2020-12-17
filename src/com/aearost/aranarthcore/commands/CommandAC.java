package com.aearost.aranarthcore.commands;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent.Result;
import com.projectkorra.projectkorra.event.PlayerChangeSubElementEvent;

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
				
				// chooseavatar
				else if (args[0].toLowerCase().equals("chooseavatar")) {
					Random random = new Random();
					Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
					Bukkit.getOnlinePlayers().toArray(onlinePlayers);
					Player avatar = onlinePlayers[random.nextInt(onlinePlayers.length)];
					
					AranarthPlayerUtils.replaceAvatar(avatar);
					Bukkit.broadcastMessage(
							ChatUtils.translateToColor("&5&lWelcome the first Avatar, &d&l" + avatar.getName() + "&5&l!"));
					for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F, 2.0F);
					}
					ChatUtils.updatePlayerGroupsAndPrefix(avatar);
					return true;
				}
				
				// element
				else if (args[0].toLowerCase().equals("element")) {
					if (args.length > 2) {
						Player player = Bukkit.getPlayer(args[2]);
						AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
						if (aranarthPlayer.getAvatarStatus().equals("current")) {
							player.sendMessage(ChatUtils.chatMessage("&cYou cannot change elements as the Avatar!"));
							return false;
						}
						BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);
						if (bendingPlayer == null) {
							GeneralMethods.createBendingPlayer(player.getUniqueId(), player.getName());
							bendingPlayer = BendingPlayer.getBendingPlayer(player);
						}
						if (args[1].toLowerCase().equals("change")) {

							if (aranarthPlayer.getIsAbleToChangeElement()) {
								player.sendMessage(ChatUtils.chatMessage("&7You can already change elements!"));
								return false;
							} else {
								if (aranarthPlayer.getSaintStatus() > 0 || aranarthPlayer.getBalance() >= 250.00) {
									
									if (aranarthPlayer.getSaintStatus() == 0) {
										aranarthPlayer.setBalance(aranarthPlayer.getBalance() - 250.00);
									}
									aranarthPlayer.setIsAbleToChangeElement(true);

									for (Element e : Element.getAllElements()) {
										if (bendingPlayer.hasElement(e)) {
											bendingPlayer.getElements().remove(e);
										}
									}
									bendingPlayer.getElements().clear();
									bendingPlayer.getSubElements().clear();
									GeneralMethods.saveElements(bendingPlayer);
									GeneralMethods.saveSubElements(bendingPlayer);
									GeneralMethods.removeUnusableAbilities(aranarthPlayer.getUsername());
									player.sendMessage(ChatUtils.chatMessage("&7You can now change elements!"));
									Bukkit.getServer().getPluginManager().callEvent(
											new PlayerChangeElementEvent(sender, player, null, Result.REMOVE));
									return true;
								} else {
									player.sendMessage(
											ChatUtils.chatMessage("&cYou don't have enough money to change elements!"));
									return false;
								}
							}
						} else {
							if (aranarthPlayer.getIsAbleToChangeElement()) {
								if (bendingPlayer == null) {
									GeneralMethods.createBendingPlayer(player.getUniqueId(), player.getName());
								}

								Element element = Element.fromString(args[1]);
								bendingPlayer.addElement(element);
								aranarthPlayer.setIsAbleToChangeElement(false);

								int rank = aranarthPlayer.getRank();
								List<SubElement> subElements = new ArrayList<>();
								if (element == Element.AIR) {
									player.sendMessage(ChatUtils.chatMessage("&7You are now an Airbender!"));
								} else if (element == Element.CHI) {
									player.sendMessage(ChatUtils.chatMessage("&6You are now a Chiblocker!"));
									bendingPlayer.setElement(Element.CHI);
								} else if (element == Element.EARTH) {
									player.sendMessage(ChatUtils.chatMessage("&aYou are now an Earthbender!"));
									bendingPlayer.setElement(Element.EARTH);
									bendingPlayer.addSubElement(SubElement.SAND);
									subElements.add(SubElement.SAND);
									if (rank >= 2) {
										bendingPlayer.addSubElement(Element.METAL);
										subElements.add(SubElement.METAL);
										player.sendMessage(ChatUtils.chatMessage("&2You are also a Metalbender!"));
									}
									if (rank >= 7) {
										bendingPlayer.addSubElement(Element.LAVA);
										subElements.add(SubElement.LAVA);
										player.sendMessage(ChatUtils.chatMessage("&2As well as a Lavabender!"));
									}
								} else if (element == Element.FIRE) {
									player.sendMessage(ChatUtils.chatMessage("&cYou are now a Firebender!"));
									bendingPlayer.setElement(Element.FIRE);
									if (rank >= 6) {
										bendingPlayer.addSubElement(Element.LIGHTNING);
										subElements.add(SubElement.LIGHTNING);
										player.sendMessage(ChatUtils.chatMessage("&4You are also a Lightningbender!"));
									}
									if (rank >= 8) {
										bendingPlayer.addSubElement(Element.COMBUSTION);
										subElements.add(SubElement.COMBUSTION);
										player.sendMessage(ChatUtils.chatMessage("&4As well as a Combustionbender!"));
									}
								} else if (element == Element.WATER) {
									player.sendMessage(ChatUtils.chatMessage("&bYou are now a Waterbender!"));
									bendingPlayer.setElement(Element.WATER);
									bendingPlayer.addSubElement(SubElement.ICE);
									subElements.add(SubElement.ICE);
									if (rank >= 1) {
										bendingPlayer.addSubElement(Element.HEALING);
										subElements.add(SubElement.HEALING);
										player.sendMessage(ChatUtils.chatMessage("&3You are also a Healer!"));
										bendingPlayer.addSubElement(Element.PLANT);
										subElements.add(SubElement.PLANT);
										player.sendMessage(ChatUtils.chatMessage("&3As well as a Plantbender!"));
									}
								}
								GeneralMethods.saveElements(bendingPlayer);
								GeneralMethods.saveSubElements(bendingPlayer);
								Bukkit.getServer().getPluginManager()
										.callEvent(new PlayerChangeElementEvent(sender, player, element, Result.ADD));

								for (SubElement sub : subElements) {
									Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeSubElementEvent(
											sender, player, sub,
											com.projectkorra.projectkorra.event.PlayerChangeSubElementEvent.Result.ADD));
								}
								return true;
							} else {
								player.sendMessage(
										ChatUtils.chatMessage("&cYou must purchase an element change first!"));
								return false;
							}
						}
					}
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
							sender.sendMessage(ChatUtils.translateToColor("&aSub-Groups: &e" + aranarthPlayer.getSubGroupsString()));
							sender.sendMessage(ChatUtils.translateToColor("&aMale: &e" + aranarthPlayer.getIsMale()));
							sender.sendMessage(ChatUtils
									.translateToColor("&aBalance: &e" + formatter.format(aranarthPlayer.getBalance())));
							sender.sendMessage(ChatUtils.translateToColor(
									"&aCan Change Element: &e" + aranarthPlayer.getIsAbleToChangeElement()));
							sender.sendMessage(
									ChatUtils.translateToColor("&aSaint Status: &e" + aranarthPlayer.getSaintStatus()));
							sender.sendMessage(ChatUtils
									.translateToColor("&aAvatar Status: &e" + aranarthPlayer.getAvatarStatus()));
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
		AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);

		if (args[2].toLowerCase().equals("rank")) {
			if (args.length >= 4) {
				if (args[3].toLowerCase().equals("peasant")) {
					aranarthPlayer.setRank(0);
				} else if (args[3].toLowerCase().equals("esquire")) {
					aranarthPlayer.setRank(1);
				} else if (args[3].toLowerCase().equals("knight")) {
					aranarthPlayer.setRank(2);
				} else if (args[3].toLowerCase().equals("baron")) {
					aranarthPlayer.setRank(3);
				} else if (args[3].toLowerCase().equals("count")) {
					aranarthPlayer.setRank(4);
				} else if (args[3].toLowerCase().equals("duke")) {
					aranarthPlayer.setRank(5);
				} else if (args[3].toLowerCase().equals("prince")) {
					aranarthPlayer.setRank(6);
				} else if (args[3].toLowerCase().equals("king")) {
					aranarthPlayer.setRank(7);
				} else if (args[3].toLowerCase().equals("emperor")) {
					aranarthPlayer.setRank(8);
				} else {
					sender.sendMessage(ChatUtils.chatMessage("&cThat is not a valid rank!"));
					return false;
				}
			} else {
				player.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <variable> [rank]"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("avatar")) {
			String previousAvatar = AranarthPlayerUtils.replaceAvatar(player);
			if (previousAvatar == null) {
				Bukkit.broadcastMessage(
						ChatUtils.translateToColor("&5&lWelcome the first Avatar, &d&l" + player.getName() + "&5&l!"));
			} else {
				Bukkit.broadcastMessage(
						ChatUtils.translateToColor("&5&lAvatar &d&l" + previousAvatar + " &5&lhas passed away..."));
				Bukkit.broadcastMessage(ChatUtils
						.translateToColor("&5&lWelcome their successor, Avatar &d&l" + player.getName() + "&5&l!"));
			}

			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F, 2.0F);
			}
			if (previousAvatar != null) {
				ChatUtils.updatePlayerGroupsAndPrefix(
						Bukkit.getOfflinePlayer(AranarthPlayerUtils.getUUID(previousAvatar)));
			}
		}
		// Saint
		else if (args[2].toLowerCase().equals("saint1")) {
			aranarthPlayer.setSaintStatus(1);
		} else if (args[2].toLowerCase().equals("saint2")) {
			aranarthPlayer.setSaintStatus(2);
		} else if (args[2].toLowerCase().equals("saint3")) {
			aranarthPlayer.setSaintStatus(3);
		}
		// Council
		else if (args[2].toLowerCase().equals("council1")) {
			aranarthPlayer.setCouncilStatus(1);
		} else if (args[2].toLowerCase().equals("council2")) {
			aranarthPlayer.setCouncilStatus(2);
		} else if (args[2].toLowerCase().equals("council3")) {
			aranarthPlayer.setCouncilStatus(3);
		} else {
			player.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac set <player> <variable> [rank]"));
			return false;
		}
		AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
		return true;
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
		AranarthPlayer aranarthPlayer = AranarthPlayerUtils.getPlayer(player);
		if (args[2].toLowerCase().equals("avatar")) {
			if (aranarthPlayer.getAvatarStatus().equals("current")) {
				AranarthPlayerUtils.replaceAvatar(null);
				Bukkit.broadcastMessage(
						ChatUtils.translateToColor("&5&lAvatar &d&l" + player.getName() + " &5&lhas passed away..."));

				for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F, 2.0F);
				}
				return true;
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not the avatar!"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("saint1")) {
			if (aranarthPlayer.getSaintStatus() == 1) {
				aranarthPlayer.setSaintStatus(0);
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not Saint 1!"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("saint2")) {
			if (aranarthPlayer.getSaintStatus() == 2) {
				aranarthPlayer.setSaintStatus(0);
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not Saint 2!"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("saint3")) {
			if (aranarthPlayer.getSaintStatus() == 3) {
				aranarthPlayer.setSaintStatus(0);
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not Saint 3!"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("council1")) {
			if (aranarthPlayer.getCouncilStatus() == 1) {
				aranarthPlayer.setCouncilStatus(0);
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not Council 1!"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("council2")) {
			if (aranarthPlayer.getCouncilStatus() == 2) {
				aranarthPlayer.setCouncilStatus(0);
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not Council 2!"));
				return false;
			}
		} else if (args[2].toLowerCase().equals("council3")) {
			if (aranarthPlayer.getCouncilStatus() == 3) {
				aranarthPlayer.setCouncilStatus(0);
			} else {
				sender.sendMessage(ChatUtils.chatMessage("&cThis player is not Council 3!"));
				return false;
			}
		} else {
			player.sendMessage(ChatUtils.chatMessage("&7Proper Usage: &e/ac unset <player> <rank>"));
			return false;
		}
		AranarthPlayerUtils.addPlayer(player.getUniqueId(), aranarthPlayer);
		return true;
	}

}
