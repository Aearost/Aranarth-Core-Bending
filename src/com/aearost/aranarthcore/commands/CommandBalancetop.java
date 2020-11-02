package com.aearost.aranarthcore.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.BalanceComparator;
import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandBalancetop implements CommandExecutor {

	/**
	 * All logic behind the /baltop command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> lines = baltopSetup();
		
		double generalPageAmount = lines.size() / 10.0;
		int totalPageNumber = (int) generalPageAmount;
		
		// Adds one if there isn't exactly 10 players on the last page
		if (generalPageAmount % 1 != 0) {
			totalPageNumber++;
		}
		
		sender.sendMessage(ChatUtils.translateToColor("&8      - - - &6&lAranarth Balances &8- - -"));
		int page = 1;
		
		if (args.length > 0) {
			try {
				page = Integer.parseInt(args[0]);
				
				// If it's an invalid input
				if (page <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatUtils.translateToColor("&cPlease enter a valid page number!"));
				return false;
			}
			
			if (page > totalPageNumber) {
				page = totalPageNumber;
			}
		}
		
		sender.sendMessage(ChatUtils.translateToColor("&7Showing page &6" + page + " &7of &6" + totalPageNumber));
			
		int line = (page * 10) - 10;
		page++;
		
		for (int i = 0; i < 10; i++) {
			sender.sendMessage(ChatUtils.translateToColor(lines.get(line)));
			line++;
			if (lines.size() == line) {
				break;
			}
		}
		
		return true;
	}

	private List<String> baltopSetup() {
		HashMap<UUID, AranarthPlayer> players = AranarthPlayerUtils.getPlayers();
		List<AranarthPlayer> playersAsList = new ArrayList<>(players.values());
		List<String> lines = new ArrayList<>();

		Collections.sort(playersAsList, new BalanceComparator());
		DecimalFormat formatter = new DecimalFormat("#.##");

		int counter = 1;
		for (AranarthPlayer aranarthPlayer : playersAsList) {
			lines.add("&7" + counter + ". &e" + aranarthPlayer.getUsername() + ", &6"
					+ formatter.format(aranarthPlayer.getBalance()));
			counter++;
		}

		return lines;
	}
}