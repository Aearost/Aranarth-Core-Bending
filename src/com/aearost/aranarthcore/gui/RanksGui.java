package com.aearost.aranarthcore.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class RanksGui {

	private Player player;
	private Inventory initializedGui;

	public RanksGui(Player player) {
		this.player = player;
		this.initializedGui = initializeGui(player);
	}
	
	public void openGui() {
		player.openInventory(initializedGui);
	}

	private Inventory initializeGui(Player player) {
		Inventory gui = Bukkit.getServer().createInventory(player, 54,
				ChatUtils.translateToColor("&0&lAranarth Ranks"));
		
		boolean isMalePlayer = AranarthPlayerUtils.getPlayer(player).getIsMale();
		int currentRank = AranarthPlayerUtils.getPlayer(player).getRank();
		
		// Initialize Items
		ItemStack yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemStack peasant = new ItemStack(Material.LIME_CONCRETE_POWDER);
		ItemStack esquire = new ItemStack(Material.LIGHT_GRAY_CONCRETE_POWDER);
		ItemStack knight = new ItemStack(Material.WHITE_CONCRETE_POWDER);
		ItemStack baron = new ItemStack(Material.CYAN_CONCRETE_POWDER);
		ItemStack count = new ItemStack(Material.GRAY_CONCRETE_POWDER);
		ItemStack duke = new ItemStack(Material.YELLOW_CONCRETE_POWDER);
		ItemStack prince = new ItemStack(Material.LIGHT_BLUE_CONCRETE_POWDER);
		ItemStack king = new ItemStack(Material.BLUE_CONCRETE_POWDER);
		ItemStack emperor = new ItemStack(Material.RED_CONCRETE_POWDER);
		ItemStack saint1 = new ItemStack(Material.PINK_CONCRETE_POWDER);
		ItemStack saint2 = new ItemStack(Material.MAGENTA_CONCRETE_POWDER);
		ItemStack saint3 = new ItemStack(Material.PURPLE_CONCRETE_POWDER);

		// Removing name of panes
		ItemMeta yellowPaneMeta = yellowPane.getItemMeta();
		yellowPaneMeta.setDisplayName(" ");
		yellowPane.setItemMeta(yellowPaneMeta);
		ItemMeta blackPaneMeta = blackPane.getItemMeta();
		blackPaneMeta.setDisplayName(" ");
		blackPane.setItemMeta(blackPaneMeta);

		// Peasant
		ItemMeta peasantMeta = peasant.getItemMeta();
		ArrayList<String> peasantLore = new ArrayList<>();
		if (currentRank == 0) {
			peasantMeta.setDisplayName(ChatUtils.translateToColor("&a&l&nPeasant - Current Rank"));
		} else {
			peasantMeta.setDisplayName(ChatUtils.translateToColor("&a&l&nPeasant"));
		}
		peasantLore.add(ChatUtils.translateToColor("&f&lBending"));
		peasantLore.add(ChatUtils.translateToColor("&f&o- Basic abilities"));
		peasantLore.add(ChatUtils.translateToColor("&f&o- &3&oIcebending"));
		peasantMeta.setLore(peasantLore);
		peasant.setItemMeta(peasantMeta);

		// Esquire
		ItemMeta esquireMeta = esquire.getItemMeta();
		ArrayList<String> esquireLore = new ArrayList<>();
		if (currentRank == 1) {
			esquireMeta.setDisplayName(ChatUtils.translateToColor("&d&l&nEsquire&r&f&l ($250) - Current Rank"));
		} else {
			esquireMeta.setDisplayName(ChatUtils.translateToColor("&d&l&nEsquire&r&f&l ($250)"));
		}
		esquireLore.add(ChatUtils.translateToColor("&f&lBending"));
		esquireLore.add(ChatUtils.translateToColor("&f&o- &3&oHealing"));
		esquireLore.add(ChatUtils.translateToColor("&f&o- &3&oPlantbending"));
		esquireLore.add(ChatUtils.translateToColor("&f&o- &2&oSandbending"));
		esquireLore.add(ChatUtils.translateToColor("&f&lPerks"));
		esquireLore.add(ChatUtils.translateToColor("&f&o- Ability to sit on stairs"));
		esquireLore.add(ChatUtils.translateToColor("&f&o- /seen"));
		esquireMeta.setLore(esquireLore);
		esquire.setItemMeta(esquireMeta);

		// Knight
		ItemMeta knightMeta = knight.getItemMeta();
		ArrayList<String> knightLore = new ArrayList<>();
		if (currentRank == 2) {
			knightMeta.setDisplayName(ChatUtils.translateToColor("&7&l&nKnight&r&f&l ($750) - Current Rank"));
		} else {
			knightMeta.setDisplayName(ChatUtils.translateToColor("&7&l&nKnight&r&f&l ($750)"));
		}
		knightLore.add(ChatUtils.translateToColor("&f&lBending"));
		knightLore.add(ChatUtils.translateToColor("&f&o- &2&oMetalbending"));
		knightLore.add(ChatUtils.translateToColor("&f&lPerks"));
		knightLore.add(ChatUtils.translateToColor("&f&o- /recipe"));
		knightMeta.setLore(knightLore);
		knight.setItemMeta(knightMeta);

		// Baron
		ItemMeta baronMeta = baron.getItemMeta();
		ArrayList<String> baronLore = new ArrayList<>();
		if (isMalePlayer) {
			if (currentRank == 3) {
				baronMeta.setDisplayName(ChatUtils.translateToColor("&5&l&nBaron&r&f&l ($1,500) - Current Rank"));
			} else {
				baronMeta.setDisplayName(ChatUtils.translateToColor("&5&l&nBaron&r&f&l ($1,500)"));
			}
		} else {
			if (currentRank == 3) {
				baronMeta.setDisplayName(ChatUtils.translateToColor("&5&l&nBaroness&r&f&l ($1,500) - Current Rank"));
			} else {
				baronMeta.setDisplayName(ChatUtils.translateToColor("&5&l&nBaroness&r&f&l ($1,500)"));
			}
		}
		baronLore.add(ChatUtils.translateToColor("&f&lBending"));
		baronLore.add(ChatUtils.translateToColor("&f&o- &b&oWaterArms"));
		baronLore.add(ChatUtils.translateToColor("&f&lPerks"));
		baronLore.add(ChatUtils.translateToColor("&f&o- Ability to create chest shops"));
		baronLore.add(ChatUtils.translateToColor("&f&o- Ability to set 2 homes"));
		baronMeta.setLore(baronLore);
		baron.setItemMeta(baronMeta);

		// Count
		ItemMeta countMeta = count.getItemMeta();
		ArrayList<String> countLore = new ArrayList<>();
		if (isMalePlayer) {
			if (currentRank == 4) {
				countMeta.setDisplayName(ChatUtils.translateToColor("&8&l&nCount&r&f&l ($3,000) - Current Rank"));
			} else {
				countMeta.setDisplayName(ChatUtils.translateToColor("&8&l&nCount&r&f&l ($3,000)"));
			}
		} else {
			if (currentRank == 4) {
				countMeta.setDisplayName(ChatUtils.translateToColor("&8&l&nCountess&r&f&l ($3,000) - Current Rank"));
			} else {
				countMeta.setDisplayName(ChatUtils.translateToColor("&8&l&nCountess&r&f&l ($3,000)"));
			}
		}
		countLore.add(ChatUtils.translateToColor("&f&lBending"));
		countLore.add(ChatUtils.translateToColor("&f&o- &7&oSonicBlast"));
		countLore.add(ChatUtils.translateToColor("&f&lPerks"));
		countLore.add(ChatUtils.translateToColor("&f&o- /back"));
		countLore.add(ChatUtils.translateToColor("&f&o- Ability to set 3 homes"));
		countMeta.setLore(countLore);
		count.setItemMeta(countMeta);

		// Duke
		ItemMeta dukeMeta = duke.getItemMeta();
		ArrayList<String> dukeLore = new ArrayList<>();
		if (isMalePlayer) {
			if (currentRank == 5) {
				dukeMeta.setDisplayName(ChatUtils.translateToColor("&6&l&nDuke&r&f&l ($7,500) - Current Rank"));
			} else {
				dukeMeta.setDisplayName(ChatUtils.translateToColor("&6&l&nDuke&r&f&l ($7,500)"));
			}
		} else {
			if (currentRank == 5) {
				dukeMeta.setDisplayName(ChatUtils.translateToColor("&6&l&nDuchess&r&f&l ($7,500) - Current Rank"));
			} else {
				dukeMeta.setDisplayName(ChatUtils.translateToColor("&6&l&nDuchess&r&f&l ($7,500)"));
			}
		}
		dukeLore.add(ChatUtils.translateToColor("&f&lPerks"));
		dukeLore.add(ChatUtils.translateToColor("&f&o- /near"));
		dukeMeta.setLore(dukeLore);
		duke.setItemMeta(dukeMeta);

		// Prince
		ItemMeta princeMeta = prince.getItemMeta();
		ArrayList<String> princeLore = new ArrayList<>();
		if (isMalePlayer) {
			if (currentRank == 6) {
				princeMeta.setDisplayName(ChatUtils.translateToColor("&b&l&nPrince&r&f&l ($12,500) - Current Rank"));
			} else {
				princeMeta.setDisplayName(ChatUtils.translateToColor("&b&l&nPrince&r&f&l ($12,500)"));
			}
		} else {
			if (currentRank == 6) {
				princeMeta.setDisplayName(ChatUtils.translateToColor("&b&l&nPrincess&r&f&l ($12,500) - Current Rank"));
			} else {
				princeMeta.setDisplayName(ChatUtils.translateToColor("&b&l&nPrincess&r&f&l ($12,500)"));
			}
		}
		princeLore.add(ChatUtils.translateToColor("&f&lBending"));
		princeLore.add(ChatUtils.translateToColor("&f&o- &4&oLightningbending"));
		princeLore.add(ChatUtils.translateToColor("&f&lPerks"));
		princeLore.add(ChatUtils.translateToColor("&f&o- /msgtoggle"));
		princeLore.add(ChatUtils.translateToColor("&f&o- Ability to set 4 homes"));
		princeMeta.setLore(princeLore);
		prince.setItemMeta(princeMeta);

		// King
		ItemMeta kingMeta = king.getItemMeta();
		ArrayList<String> kingLore = new ArrayList<>();
		if (isMalePlayer) {
			if (currentRank == 7) {
				kingMeta.setDisplayName(ChatUtils.translateToColor("&9&l&nKing&r&f&l ($30,000) - Current Rank"));
			} else {
				kingMeta.setDisplayName(ChatUtils.translateToColor("&9&l&nKing&r&f&l ($30,000)"));
			}
		} else {
			if (currentRank == 7) {
				kingMeta.setDisplayName(ChatUtils.translateToColor("&9&l&nQueen&r&f&l ($30,000) - Current Rank"));
			} else {
				kingMeta.setDisplayName(ChatUtils.translateToColor("&9&l&nQueen&r&f&l ($30,000)"));
			}
		}
		kingLore.add(ChatUtils.translateToColor("&f&lBending"));
		kingLore.add(ChatUtils.translateToColor("&f&o- &2&oLavabending"));
		kingLore.add(ChatUtils.translateToColor("&f&o- &c&lFireComet"));
		kingLore.add(ChatUtils.translateToColor("&f&lPerks"));
		kingLore.add(ChatUtils.translateToColor("&f&o- /tptoggle"));
		kingMeta.setLore(kingLore);
		king.setItemMeta(kingMeta);

		// Emperor
		ItemMeta emperorMeta = emperor.getItemMeta();
		ArrayList<String> emperorLore = new ArrayList<>();
		if (isMalePlayer) {
			if (currentRank == 8) {
				emperorMeta.setDisplayName(ChatUtils.translateToColor("&4&l&nEmperor&r&f&l ($50,000) - Current Rank"));
			} else {
				emperorMeta.setDisplayName(ChatUtils.translateToColor("&4&l&nEmperor&r&f&l ($50,000)"));
			}
		} else {
			if (currentRank == 8) {
				emperorMeta.setDisplayName(ChatUtils.translateToColor("&4&l&nEmpress&r&f&l ($50,000) - Current Rank"));
			} else {
				emperorMeta.setDisplayName(ChatUtils.translateToColor("&4&l&nEmpress&r&f&l ($50,000)"));
			}
		}
		
		emperorLore.add(ChatUtils.translateToColor("&f&lBending"));
		emperorLore.add(ChatUtils.translateToColor("&f&o- &4&oCombustionbending"));
		emperorLore.add(ChatUtils.translateToColor("&f&lPerks"));
		emperorLore.add(ChatUtils.translateToColor("&f&o- Ability to set 5 homes"));
		emperorMeta.setLore(emperorLore);
		emperor.setItemMeta(emperorMeta);

		// Saint 1
		ItemMeta saint1Meta = saint1.getItemMeta();
		ArrayList<String> saint1Lore = new ArrayList<>();
		saint1Meta.setDisplayName(ChatUtils.translateToColor("&d&l&nSaint (I)"));
		saint1Lore.add(ChatUtils.translateToColor("&f&lPerks"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- Colored chat and signs"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- /nick, /hat, /craft"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- 10 arrow trails including\nTotem and Sneeze"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- 6 gadgets including the Paintball Gun"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- 4 mounts including Glacial Steed"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- 5 particles including Frozenwalk"));
		saint1Lore.add(ChatUtils.translateToColor("&f&o- Access to the Cow and Pig pets"));
		saint1Meta.setLore(saint1Lore);
		saint1.setItemMeta(saint1Meta);

		// Saint 2
		ItemMeta saint2Meta = saint2.getItemMeta();
		ArrayList<String> saint2Lore = new ArrayList<>();
		saint2Meta.setDisplayName(ChatUtils.translateToColor("&d&l&nSaint (II)"));
		saint2Lore.add(ChatUtils.translateToColor("&f&lPerks"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- Colored nickname"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- /enderchest"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- 14 more arrow trails including\nHeart and End Rod"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- 9 gadgets including Thor's Hammer"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- 4 mounts including Mount of Fire"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- 7 particles including Magical Rods"));
		saint2Lore.add(ChatUtils.translateToColor("&f&o- 9 pets including the Polar Bear"));
		saint2Meta.setLore(saint2Lore);
		saint2.setItemMeta(saint2Meta);

		// Saint 3
		ItemMeta saint3Meta = saint3.getItemMeta();
		ArrayList<String> saint3Lore = new ArrayList<>();
		saint3Meta.setDisplayName(ChatUtils.translateToColor("&d&l&nSaint (III)"));
		saint3Lore.add(ChatUtils.translateToColor("&f&lPerks"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- Bold chat and nickname"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- Ability to join the server when it's full"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- 16 more arrow trails including\nMagic Crit and Squid Ink"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- 11 gadgets including Trampoline"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- 6 mounts including Nyan Sheep"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- 7 particles including Fire Waves"));
		saint3Lore.add(ChatUtils.translateToColor("&f&o- 5 pets including the Wither"));
		saint3Meta.setLore(saint3Lore);
		saint3.setItemMeta(saint3Meta);

		// Initialize GUI
		// Line 1
		gui.setItem(0, yellowPane);
		gui.setItem(1, yellowPane);
		gui.setItem(2, blackPane);
		gui.setItem(3, blackPane);
		gui.setItem(4, peasant);
		gui.setItem(5, blackPane);
		gui.setItem(6, blackPane);
		gui.setItem(7, yellowPane);
		gui.setItem(8, yellowPane);
		// Line 2
		gui.setItem(9, blackPane);
		gui.setItem(10, yellowPane);
		gui.setItem(11, blackPane);
		gui.setItem(12, esquire);
		gui.setItem(13, blackPane);
		gui.setItem(14, knight);
		gui.setItem(15, blackPane);
		gui.setItem(16, yellowPane);
		gui.setItem(17, blackPane);
		// Line 3
		gui.setItem(18, blackPane);
		gui.setItem(19, yellowPane);
		gui.setItem(20, baron);
		gui.setItem(21, blackPane);
		gui.setItem(22, count);
		gui.setItem(23, blackPane);
		gui.setItem(24, duke);
		gui.setItem(25, yellowPane);
		gui.setItem(26, blackPane);
		// Line 4
		gui.setItem(27, blackPane);
		gui.setItem(28, yellowPane);
		gui.setItem(29, blackPane);
		gui.setItem(30, prince);
		gui.setItem(31, blackPane);
		gui.setItem(32, king);
		gui.setItem(33, blackPane);
		gui.setItem(34, yellowPane);
		gui.setItem(35, blackPane);
		// Line 5
		gui.setItem(36, blackPane);
		gui.setItem(37, yellowPane);
		gui.setItem(38, blackPane);
		gui.setItem(39, blackPane);
		gui.setItem(40, emperor);
		gui.setItem(41, blackPane);
		gui.setItem(42, blackPane);
		gui.setItem(43, yellowPane);
		gui.setItem(44, blackPane);
		// Line 6
		gui.setItem(45, yellowPane);
		gui.setItem(46, yellowPane);
		gui.setItem(47, saint1);
		gui.setItem(48, blackPane);
		gui.setItem(49, saint2);
		gui.setItem(50, blackPane);
		gui.setItem(51, saint3);
		gui.setItem(52, yellowPane);
		gui.setItem(53, yellowPane);

		return gui;
	}

}
