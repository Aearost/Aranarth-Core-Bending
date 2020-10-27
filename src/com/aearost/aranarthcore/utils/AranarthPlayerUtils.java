package com.aearost.aranarthcore.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthPlayer;

public class AranarthPlayerUtils {

	private static HashMap<String, AranarthPlayer> players = new HashMap<>();
	
	public AranarthPlayerUtils(boolean isServerStarting) {
		if (isServerStarting) {
			readFromFile();
		} else {
			writeToFile();
		}
	}
	
	public static void addPlayer(String playerName, AranarthPlayer aranarthPlayer) {
		// Assumes male player by default
		players.put(playerName.toLowerCase(), aranarthPlayer);
	}
	
	private void readFromFile() {
		String currentPath = System.getProperty("user.dir");
		String filePath = currentPath + File.separator + "plugins" + File.separator + "AranarthCore" + File.separator
				+ "players.json";
		File file = new File(filePath);

		// First run of plugin
		if (!file.exists()) {
			return;
		}

		Scanner reader;
		try {
			reader = new Scanner(file);
			int fieldCount = 0;

			String playerName = "";
			String rank = "";
			boolean isMale = true;
			int saintStatus = 0;
			String avatarStatus = "none";

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String fieldName = null;
				String fieldValue = null;

				if (line.endsWith(": {")) {
					String[] wrapperFields = new String[3];
					if (wrapperFields[1].equals("player")) {
						playerName = wrapperFields[1];
						continue;
					}
				} else if (line.endsWith("\",")) {
					String[] parts = line.split("\"");
					fieldName = parts[1];
					fieldValue = parts[3];
				} else {
					continue;
				}

				if (fieldName.equals("rank")) {
					rank = fieldValue;
				} else if (fieldName.equals("isMale")) {
					isMale = Boolean.parseBoolean(fieldValue);
					// If they have either the saintStatus or avatarStatus field
					if (reader.next().startsWith("\"a") || reader.next().startsWith("\"s")) {
						continue;
					} else {
						// If only two fields, will not add the rest
						fieldCount = 3;
					}
					
				} else if (fieldName.equals("avatarStatus")) {
					avatarStatus = fieldValue;
				} else if (fieldName.equals("saintStatus")) {
					saintStatus = Integer.parseInt(fieldValue);
				} else {
					reader.close();
					throw new FileNotFoundException();
				}

				fieldCount++;

				if (fieldCount == 4) {
					boolean hasAvatarStatus = false;
					boolean hasSaintStatus = false;
					if (!avatarStatus.equals("none")) {
						hasAvatarStatus = true;
					}
					if (saintStatus != 0) {
						hasSaintStatus = true;
					}
					if (hasAvatarStatus && hasSaintStatus) {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale, saintStatus, avatarStatus));
					} else if (hasAvatarStatus) {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale, avatarStatus));
					} else if (hasSaintStatus) {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale, saintStatus));
					} else {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale));
					}
					// Reset these as rank and isMale will always be overwritten
					saintStatus = 0;
					avatarStatus = "none";
				}
			}
			reader.close();
			file.delete();
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().info("Something went wrong with reading the kettles!");
			e.printStackTrace();
		}
	}
	
	private void writeToFile() {
		
	}
	
}
