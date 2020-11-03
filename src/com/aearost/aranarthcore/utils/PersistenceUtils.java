package com.aearost.aranarthcore.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.objects.AranarthShop;

public class PersistenceUtils {

	/**
	 * Initializes the players HashMap based on the contents of players.json.
	 */
	public static void readPlayersFromFile() {
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
						AranarthPlayerUtils.addPlayer(uuid, new AranarthPlayer(username, rank, isMale, balance,
								saintStatus, avatarStatus, councilStatus));
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
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, avatarStatus));
					} else if (hasSaintStatus) {
						AranarthPlayerUtils.addPlayer(uuid,
								new AranarthPlayer(username, rank, isMale, balance, saintStatus));
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
	public static void writePlayersToFile() {
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

	/**
	 * Initializes the shops HashMap based on the contents of shop.json.
	 */
	public static void readShopSignsFromFile() {
		String currentPath = System.getProperty("user.dir");
		String filePath = currentPath + File.separator + "plugins" + File.separator + "AranarthCore" + File.separator
				+ "shops.json";
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
			int transactionQuantity = 0;
			double buyAmount = 0.0;
			ItemStack item = null;
			double sellAmount = 0.0;
			World world = null;
			int x = 0;
			int y = 0;
			int z = 0;

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] parts = line.split("\"");

				if (line.endsWith(": {") && !parts[1].equals("shops") && !isRegularNumber(parts[1])) {
					uuid = UUID.fromString(parts[1]);
					fieldCount++;
					continue;
				} else if (line.endsWith("\",")) {
					fieldName = parts[1];
					fieldValue = parts[3];
				} else if (line.equals("{") || line.equals("}")) {
					continue;
				}

				if (fieldName.equals("transactionQuantity")) {
					transactionQuantity = Integer.parseInt(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("buyAmount")) {
					buyAmount = Double.parseDouble(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("item")) {
					item = new ItemStack(Material.valueOf(fieldValue), 1);
					fieldCount++;
				} else if (fieldName.equals("sellAmount")) {
					sellAmount = Double.parseDouble(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("worldName")) {
					world = Bukkit.getWorld(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("x")) {
					x = Integer.parseInt(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("y")) {
					y = Integer.parseInt(fieldValue);
					fieldCount++;
				} else if (fieldName.equals("z")) {
					z = Integer.parseInt(fieldValue);
					fieldCount++;
				}

				if (fieldCount == 9) {
					Location location = new Location(world, x, y, z);
					if (sellAmount == -1) {
						AranarthShopUtils.addShop(uuid,
								new AranarthShop(uuid, transactionQuantity, buyAmount, item, location));
					} else if (buyAmount == -1) {
						AranarthShopUtils.addShop(uuid,
								new AranarthShop(uuid, transactionQuantity, item, sellAmount, location));
					} else {
						AranarthShopUtils.addShop(uuid,
								new AranarthShop(uuid, transactionQuantity, buyAmount, item, sellAmount, location));
					}
					fieldCount = 0;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().info("Something went wrong with reading the players!");
			e.printStackTrace();
		}
	}

	/**
	 * Saves the contents of the shops HashMap to the shops.json file.
	 */
	public static void writeShopSignsToFile() {
		HashMap<UUID, List<AranarthShop>> shops = AranarthShopUtils.getShops();
		if (shops.size() > 0) {

			String currentPath = System.getProperty("user.dir");
			String filePath = currentPath + File.separator + "plugins" + File.separator + "AranarthCore"
					+ File.separator + "shops.json";
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
						Bukkit.getLogger().info("A new shops.json file has been generated");
					}
				} catch (IOException e) {
					Bukkit.getLogger().info("An error occured in the creation of shops.json");
					e.printStackTrace();
				}

				try {
					FileWriter writer = new FileWriter(filePath);
					writer.write("{\n");
					writer.write("\"shops\": {\n");
					int playerCounter = 1;

					for (Map.Entry<UUID, List<AranarthShop>> entry : shops.entrySet()) {
						UUID uuid = entry.getKey();

						writer.write("    \"" + uuid.toString() + "\": {\n");
						List<AranarthShop> playerShops = entry.getValue();

						int shopCounter = 1;
						for (AranarthShop shop : playerShops) {

							writer.write("        \"" + shopCounter + "\": {\n");
							writer.write("            \"transactionQuantity\": \"" + shop.getTransactionQuantity()
									+ "\",\n");
							writer.write("            \"buyAmount\": \"" + shop.getBuyAmount() + "\",\n");
							writer.write("            \"item\": \"" + shop.getItem().getType().name() + "\",\n");
							writer.write("            \"sellAmount\": \"" + shop.getSellAmount() + "\",\n");
							writer.write("            \"worldName\": \"" + shop.getChestLocation().getWorld().getName()
									+ "\",\n");
							writer.write("            \"x\": \"" + shop.getChestLocation().getBlockX() + "\",\n");
							writer.write("            \"y\": \"" + shop.getChestLocation().getBlockY() + "\",\n");
							writer.write("            \"z\": \"" + shop.getChestLocation().getBlockZ() + "\"\n");

							if (shopCounter == playerShops.size()) {
								writer.write("        }\n");
							} else {
								writer.write("        },\n");
							}
							shopCounter++;
						}

						if (playerCounter == shops.size()) {
							writer.write("    }\n");
						} else {
							writer.write("    },\n");
						}

						playerCounter++;
					}
					writer.write("}\n");
					writer.close();
				} catch (IOException e) {
					Bukkit.getLogger().info("There was an error in saving the shops");
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean isRegularNumber(String part) {
		try {
			@SuppressWarnings("unused")
			int partAsNumber = Integer.parseInt(part);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
