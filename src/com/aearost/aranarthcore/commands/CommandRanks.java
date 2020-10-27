package com.aearost.aranarthcore.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandRanks implements CommandExecutor {

	/**
	 * All logic behind the /ranks command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			Inventory gui = Bukkit.getServer().createInventory(player, 54, ChatUtils.translateToColor("&8&lAranarth Ranks"));
			
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
			peasantMeta.setDisplayName(ChatUtils.translateToColor("&a&l&nPeasant"));
			peasantLore.add(ChatUtils.translateToColor("&f&lBending"));
			peasantLore.add(ChatUtils.translateToColor("&f&o- Basic abilities"));
			peasantLore.add(ChatUtils.translateToColor("&f&o- &3&oIcebending"));
			peasantMeta.setLore(peasantLore);
			peasant.setItemMeta(peasantMeta);
			
			// Esquire
			ItemMeta esquireMeta = esquire.getItemMeta();
			ArrayList<String> esquireLore = new ArrayList<>();
			esquireMeta.setDisplayName(ChatUtils.translateToColor("&d&l&nEsquire"));
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
			knightMeta.setDisplayName(ChatUtils.translateToColor("&7&l&nKnight"));
			knightLore.add(ChatUtils.translateToColor("&f&lBending"));
			knightLore.add(ChatUtils.translateToColor("&f&o- &2&oMetalbending"));
			knightLore.add(ChatUtils.translateToColor("&f&lPerks"));
			knightLore.add(ChatUtils.translateToColor("&f&o- /recipe"));
			knightLore.add(ChatUtils.translateToColor("&f&o- Ability to work 2 jobs"));
			knightMeta.setLore(knightLore);
			knight.setItemMeta(knightMeta);			
			
			// Baron
			ItemMeta baronMeta = baron.getItemMeta();
			ArrayList<String> baronLore = new ArrayList<>();
			baronMeta.setDisplayName(ChatUtils.translateToColor("&5&l&nBaron / Baroness"));
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
			countMeta.setDisplayName(ChatUtils.translateToColor("&8&l&nCount / Countess"));
			countLore.add(ChatUtils.translateToColor("&f&lBending"));
			countLore.add(ChatUtils.translateToColor("&f&o- &7&oSonicBlast"));
			countLore.add(ChatUtils.translateToColor("&f&lPerks"));
			countLore.add(ChatUtils.translateToColor("&f&o- /back"));
			countLore.add(ChatUtils.translateToColor("&f&o- Ability to set 3 homes"));
			countLore.add(ChatUtils.translateToColor("&f&o- Ability to work 3 jobs"));
			countMeta.setLore(countLore);
			count.setItemMeta(countMeta);
			
			// Duke
			ItemMeta dukeMeta = duke.getItemMeta();
			ArrayList<String> dukeLore = new ArrayList<>();
			dukeMeta.setDisplayName(ChatUtils.translateToColor("&6&l&nDuke / Duchess"));
			dukeLore.add(ChatUtils.translateToColor("&f&lPerks"));
			dukeLore.add(ChatUtils.translateToColor("&f&o- /near"));
			dukeLore.add(ChatUtils.translateToColor("&f&o- Ability to work 4 jobs"));
			dukeLore.add(ChatUtils.translateToColor("&f&o- Ability to get paid for pet's kills"));
			dukeMeta.setLore(dukeLore);
			duke.setItemMeta(dukeMeta);
			
			// Prince
			ItemMeta princeMeta = prince.getItemMeta();
			ArrayList<String> princeLore = new ArrayList<>();
			princeMeta.setDisplayName(ChatUtils.translateToColor("&b&l&nPrince / Princess"));
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
			kingMeta.setDisplayName(ChatUtils.translateToColor("&9&l&nKing / Queen"));
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
			emperorMeta.setDisplayName(ChatUtils.translateToColor("&4&l&nEmperor / Empress"));
			emperorLore.add(ChatUtils.translateToColor("&f&lBending"));
			emperorLore.add(ChatUtils.translateToColor("&f&o- &4&oCombustionbending"));
			emperorLore.add(ChatUtils.translateToColor("&f&lPerks"));
			emperorLore.add(ChatUtils.translateToColor("&f&o- Ability to set 5 homes"));
			emperorLore.add(ChatUtils.translateToColor("&f&o- Ability to work 5 jobs"));
			emperorMeta.setLore(emperorLore);
			emperor.setItemMeta(emperorMeta);
			
			
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
			
			player.openInventory(gui);
			return true;
		}
		return false;
		
	}
	
}