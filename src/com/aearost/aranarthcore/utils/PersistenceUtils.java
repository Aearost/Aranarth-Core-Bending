package com.aearost.aranarthcore.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;

import com.aearost.aranarthcore.objects.AranarthPlayer;

public class PersistenceUtils {

	/**
	 * Initializes the players HashMap based on the contents of players.json.
	 */
	public static void readFromFile() {
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
			int rank = 0;
			boolean isMale = true;
			double balance = 0.00;
			int saintStatus = 0;
			String avatarStatus = "none";

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String fieldName = null;
				String fieldValue = null;
				
				String[] parts = line.split("\"");
				
				if (line.endsWith(": {")) {
					if (!parts[1].equals("players")) {
						playerName = parts[1];
						continue;
					} else {
						continue;
					}
				} else if (line.endsWith("\",")) {
					fieldName = parts[1];
					fieldValue = parts[3];
				} else {
					continue;
				}
				
				if (fieldName.equals("rank")) {
					rank = Integer.parseInt(fieldValue);
				} else if (fieldName.equals("isMale")) {
					isMale = Boolean.parseBoolean(fieldValue);
				} else if (fieldName.equals("balance")) {
					balance = Double.parseDouble(fieldValue);
					// If they have either the saintStatus or avatarStatus field
					if (reader.next().startsWith("\"a") || reader.next().startsWith("\"s")) {
						continue;
					} else {
						// If only three fields, will not add the rest
						fieldCount = 5;
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

				if (fieldCount == 5) {
					boolean hasAvatarStatus = false;
					boolean hasSaintStatus = false;
					if (!avatarStatus.equals("none")) {
						hasAvatarStatus = true;
					}
					if (saintStatus != 0) {
						hasSaintStatus = true;
					}
					if (hasAvatarStatus && hasSaintStatus) {
						AranarthPlayerUtils.addPlayer(playerName,
								new AranarthPlayer(rank, isMale, balance, saintStatus, avatarStatus));
					} else if (hasAvatarStatus) {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale, balance, avatarStatus));
					} else if (hasSaintStatus) {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale, balance, saintStatus));
					} else {
						AranarthPlayerUtils.addPlayer(playerName, new AranarthPlayer(rank, isMale, balance));
					}
					// Reset these as rank, isMale, and balance are always overwritten
					saintStatus = 0;
					avatarStatus = "none";
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().info("Something went wrong with reading the players!");
			e.printStackTrace();
		}
	}

	/**
	 * Saves the contents of the players HashMap to the players.json file.
	 */
	public static void writeToFile() {
		HashMap<String, AranarthPlayer> players = AranarthPlayerUtils.getPlayers();
		if (players.size() > 0) {

			String currentPath = System.getProperty("user.dir");
			String filePath = currentPath + File.separator + "plugins" + File.separator + "AranarthCore" + File.separator
					+ "players.json";
			File pluginDirectory = new File(currentPath + File.separator + "plugins" + File.separator + "AranarthCore");
			File file = new File(filePath);

			// If the directory exists
			boolean isDirectoryCreated = true;
			if (!pluginDirectory.isDirectory()) {
				isDirectoryCreated = pluginDirectory.mkdir();
			}
			if (isDirectoryCreated) {
				try {
					// If the file isn't already there
					if (file.createNewFile()) {
						Bukkit.getLogger().info("A new players.json file has been generated");
					} else {
						throw new IOException();
					}
				} catch (IOException e) {
					Bukkit.getLogger().info("An error occured in the creation of players.json");
					e.printStackTrace();
				}

				try {
					FileWriter writer = new FileWriter(filePath);
					writer.write("{\n");
					writer.write("\"players\": {\n");
					int counter = 1;

					for (Map.Entry<String, AranarthPlayer> entry : players.entrySet()) {
						String playerName = entry.getKey();
						AranarthPlayer aranarthPlayer = entry.getValue();

						writer.write("    \"" + playerName + "\": {\n");
						writer.write("        \"rank\": \"" + aranarthPlayer.getRank() + "\",\n");
						writer.write("        \"isMale\": \"" + aranarthPlayer.getIsMale() + "\",\n");
						writer.write("        \"balance\": \"" + aranarthPlayer.getBalance() + "\",\n");
						if (!aranarthPlayer.getAvatarStatus().equals("none")) {
							writer.write("        \"avatarStatus\": \"" + aranarthPlayer.getAvatarStatus() + "\",\n");
						}
						if (aranarthPlayer.getSaintStatus() != 0) {
							writer.write("        \"saintStatus\": \"" + aranarthPlayer.getSaintStatus() + "\",\n");
						}

						// If it's the last entry
						if (players.size() == counter) {
							writer.write("    }\n");
						} else {
							writer.write("    },\n");
						}
						counter++;
					}
					writer.write("}\n");
					writer.close();
				} catch (IOException e) {
					Bukkit.getLogger().info("There was an error in saving the players");
					e.printStackTrace();
				}
			}
		}
	}
	
}
