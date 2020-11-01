package com.aearost.aranarthcore.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

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
			String fieldName = null;
			String fieldValue = null;

			UUID uuid = null;
			String username = null;
			int rank = 0;
			boolean isMale = true;
			double balance = 0.00;
			int saintStatus = 0;
			String avatarStatus = "none";
			int councilStatus = 0;

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] parts = line.split("\"");

				if ((line.endsWith("},") || line.endsWith("}")) && fieldCount >= 4) {
					fieldCount = 8;
					fieldName = "none";
					fieldValue = "none";
				}

				if (line.endsWith(": {")) {
					if (!parts[1].equals("players")) {
						uuid = UUID.fromString(parts[1]);
						fieldCount++;
						continue;
					} else {
						continue;
					}
				} else if (line.endsWith("\",")) {
					fieldName = parts[1];
					fieldValue = parts[3];
				} else if (line.equals("{")) {
					continue;
				}
				
				if (fieldName.equals("username")) {
					username = fieldValue;
					fieldCount++;
				} else if (fieldName.equals("rank")) {
					rank = Integer.parseInt(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("isMale")) {
					isMale = Boolean.parseBoolean(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("balance")) {
					balance = Double.parseDouble(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("avatarStatus")) {
					avatarStatus = fieldValue;
					fieldCount++;
				} else if (fieldName.equals("saintStatus")) {
					saintStatus = Integer.parseInt(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("councilStatus")) {
					councilStatus = Integer.parseInt(fieldValue);
					fieldCount++;
				}

				if (fieldCount == 8) {
					boolean hasAvatarStatus = false;
					boolean hasSaintStatus = false;
					boolean hasCouncilStatus = false;
					if (saintStatus != 0) {
						hasSaintStatus = true;
					}
					if (!avatarStatus.equals("none")) {
						hasAvatarStatus = true;
					}
					if (councilStatus != 0) {
						hasCouncilStatus = true;
					}

					if (hasSaintStatus && hasAvatarStatus && hasCouncilStatus) {
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, saintStatus, avatarStatus, councilStatus));
					} else if (hasSaintStatus && hasAvatarStatus) {
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, saintStatus, avatarStatus));
					} else if (hasAvatarStatus && hasCouncilStatus) {
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, councilStatus + 3, avatarStatus));
					} else if (hasSaintStatus && hasCouncilStatus) {
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, saintStatus, councilStatus));
					} else if (hasAvatarStatus) {
						AranarthPlayerUtils.addPlayer(uuid, new AranarthPlayer(username, rank, isMale, balance, avatarStatus));
					} else if (hasSaintStatus) {
						AranarthPlayerUtils.addPlayer(uuid, new AranarthPlayer(username, rank, isMale, balance, saintStatus));
					} else if (hasCouncilStatus) {
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, councilStatus + 3));
					} else {
						AranarthPlayerUtils.addPlayer(uuid, new AranarthPlayer(username, rank, isMale, balance));
					}

					// Reset these as rank, isMale, and balance are always overwritten
					saintStatus = 0;
					avatarStatus = "none";
					councilStatus = 0;
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
		HashMap<UUID, AranarthPlayer> players = AranarthPlayerUtils.getPlayers();
		if (players.size() > 0) {

			String currentPath = System.getProperty("user.dir");
			String filePath = currentPath + File.separator + "plugins" + File.separator + "AranarthCore"
					+ File.separator + "players.json";
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

					for (Map.Entry<UUID, AranarthPlayer> entry : players.entrySet()) {
						UUID uuid = entry.getKey();
						AranarthPlayer aranarthPlayer = entry.getValue();

						writer.write("    \"" + uuid.toString() + "\": {\n");
						writer.write("        \"username\": \"" + aranarthPlayer.getUsername() + "\",\n");
						writer.write("        \"rank\": \"" + aranarthPlayer.getRank() + "\",\n");
						writer.write("        \"isMale\": \"" + aranarthPlayer.getIsMale() + "\",\n");
						writer.write("        \"balance\": \"" + aranarthPlayer.getBalance() + "\",\n");
						if (aranarthPlayer.getSaintStatus() != 0) {
							writer.write("        \"saintStatus\": \"" + aranarthPlayer.getSaintStatus() + "\",\n");
						}
						if (!aranarthPlayer.getAvatarStatus().equals("none")) {
							writer.write("        \"avatarStatus\": \"" + aranarthPlayer.getAvatarStatus() + "\",\n");
						}
						if (aranarthPlayer.getCouncilStatus() != 0) {
							writer.write("        \"councilStatus\": \"" + aranarthPlayer.getCouncilStatus() + "\",\n");
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
