package com.aearost.aranarthcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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