package com.aearost.aranarthcore.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
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

			Bukkit.getLogger().info("Attempting to read the players file...");

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
			Bukkit.getLogger().info("All players have been initialized");
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
		Bukkit.getLogger().info("Checking if there are any players...");
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
					Bukkit.getLogger().info("Writing players to the players.json file");
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
					Bukkit.getLogger().info("All players have been written to the players.json file");
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

			// UUID must not be reset each time
			int fieldCount = 1;
			String fieldName = null;
			String fieldValue = null;

			boolean areServerShopsBeingIterated = false;
			UUID uuid = null;
			int transactionQuantity = 0;
			double buyAmount = 0.0;
			ItemStack item = null;
			double sellAmount = 0.0;
			World world = null;
			int x = 0;
			int y = 0;
			int z = 0;

			Bukkit.getLogger().info("Attempting to read the shops file...");

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] parts = line.split("\"");

				if (line.endsWith(": {") && !parts[1].equals("shops") && !isRegularNumber(parts[1])) {
					if (parts[1].equals("ARANARTH_SERVER_SHOPS")) {
						areServerShopsBeingIterated = true;
						continue;
					} else {
						areServerShopsBeingIterated = false;
						uuid = UUID.fromString(parts[1]);
						continue;
					}
				} else if (parts[parts.length - 1].equals(",") || isRegularNumber(parts[parts.length - 1])) {
					fieldName = parts[1];
					fieldValue = parts[3];
				} else {
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
					if (areServerShopsBeingIterated) {

						if (sellAmount == -1) {
							AranarthShopUtils.addServerShop(
									new AranarthShop(null, transactionQuantity, buyAmount, item, location));
						} else if (buyAmount == -1) {
							AranarthShopUtils.addServerShop(
									new AranarthShop(null, transactionQuantity, item, sellAmount, location));
						} else {
							AranarthShopUtils.addServerShop(
									new AranarthShop(null, transactionQuantity, buyAmount, item, sellAmount, location));
						}
					} else {
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
					}
					fieldCount = 1;
				}
			}
			Bukkit.getLogger().info("All shops have been initialized");
			reader.close();
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().info("Something went wrong with reading the shops!");
			e.printStackTrace();
		}
	}

	/**
	 * Saves the contents of the shops HashMap to the shops.json file.
	 */
	public static void writeShopSignsToFile() {
		HashMap<UUID, List<AranarthShop>> shops = AranarthShopUtils.getShops();
		List<AranarthShop> serverShops = AranarthShopUtils.getServerShops();
		Bukkit.getLogger().info("Checking if there are any shops...");
		if (shops.size() > 0 || serverShops.size() > 0) {
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

				Bukkit.getLogger().info("Writing shops to the shops.json file");
				try {
					FileWriter writer = new FileWriter(filePath);
					writer.write("{\n");
					writer.write("\"shops\": {\n");
					int playerCounter = 1;

					int shopCounter = 1;

					if (serverShops.size() > 0) {
						writer.write("    \"ARANARTH_SERVER_SHOPS\": {\n");

						for (AranarthShop shop : serverShops) {
							writer.write("        \"" + shopCounter + "\": {\n");
							writer.write("            \"transactionQuantity\": \"" + shop.getTransactionQuantity()
									+ "\",\n");
							writer.write("            \"buyAmount\": \"" + shop.getBuyAmount() + "\",\n");
							writer.write("            \"item\": \"" + shop.getItem().getType().name() + "\",\n");
							writer.write("            \"sellAmount\": \"" + shop.getSellAmount() + "\",\n");
							writer.write("            \"worldName\": \"" + shop.getShopLocation().getWorld().getName()
									+ "\",\n");
							writer.write("            \"x\": \"" + shop.getShopLocation().getBlockX() + "\",\n");
							writer.write("            \"y\": \"" + shop.getShopLocation().getBlockY() + "\",\n");
							writer.write("            \"z\": \"" + shop.getShopLocation().getBlockZ() + "\"\n");

							if (shopCounter == serverShops.size()) {
								writer.write("        }\n");
							} else {
								writer.write("        },\n");
								shopCounter++;
							}
						}
					}

					if (shops.size() > 0) {
						writer.write("    },\n");
						for (Map.Entry<UUID, List<AranarthShop>> entry : shops.entrySet()) {
							UUID uuid = entry.getKey();

							writer.write("    \"" + uuid.toString() + "\": {\n");
							List<AranarthShop> playerShops = entry.getValue();

							shopCounter = 1;
							for (AranarthShop shop : playerShops) {

								writer.write("        \"" + shopCounter + "\": {\n");
								writer.write("            \"transactionQuantity\": \"" + shop.getTransactionQuantity()
										+ "\",\n");
								writer.write("            \"buyAmount\": \"" + shop.getBuyAmount() + "\",\n");
								writer.write("            \"item\": \"" + shop.getItem().getType().name() + "\",\n");
								writer.write("            \"sellAmount\": \"" + shop.getSellAmount() + "\",\n");
								writer.write("            \"worldName\": \""
										+ shop.getShopLocation().getWorld().getName() + "\",\n");
								writer.write("            \"x\": \"" + shop.getShopLocation().getBlockX() + "\",\n");
								writer.write("            \"y\": \"" + shop.getShopLocation().getBlockY() + "\",\n");
								writer.write("            \"z\": \"" + shop.getShopLocation().getBlockZ() + "\"\n");

								if (shopCounter == playerShops.size()) {
									writer.write("        }\n");
								} else {
									writer.write("        },\n");
									shopCounter++;
								}
							}

							if (playerCounter == shops.size()) {
								writer.write("    }\n");
							} else {
								writer.write("    },\n");
							}

							playerCounter++;
						}
					} else {
						writer.write("    }\n");
					}

					writer.write("}\n");
					writer.close();
					Bukkit.getLogger().info("All shops have been written to the shops.json file");
				} catch (IOException e) {
					Bukkit.getLogger().info("There was an error in saving the shops");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Called to log a transaction to the transactions.txt file.
	 * 
	 * @param transaction
	 */
	public static void logTransaction(String transaction) {
		String currentPath = System.getProperty("user.dir");
		String filePath = currentPath + File.separator + "plugins" + File.separator + "AranarthCore" + File.separator
				+ "transactions.txt";
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
					FileWriter writer = new FileWriter(filePath);
					writer.write("# NOTE: All balances are listed in parentheses i.e ($420.69)\n");
					writer.write("# The amount gained/spent in a transaction is listed as well\n");
					writer.write("# This is used to verify whether or not a transaction properly occurred\n");
					writer.close();
					Bukkit.getLogger().info("A new transactions.txt file has been generated");
				}
			} catch (IOException e) {
				Bukkit.getLogger().info("An error occured in the creation of transactions.txt");
				e.printStackTrace();
			}

			try {
				Files.write(Paths.get(filePath),
						("[" + new Timestamp(System.currentTimeMillis()) + "] " + transaction + "\n").getBytes(),
						StandardOpenOption.APPEND);
				Bukkit.getLogger().info("[TRANSACTION] " + transaction);
			} catch (IOException e) {
				Bukkit.getLogger().info("There was an error in logging a transaction");
				e.printStackTrace();
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
