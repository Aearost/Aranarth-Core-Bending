package com.aearost.aranarthcore.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.aearost.aranarthcore.utils.ChatUtils;

/**
 * A chest GUI that appears once a user enters the /trails command. This GUI
 * allows the user to easily select and equip an arrowtrail that is available to
 * them with their given rank. Only those they have access to will be listed.
 * 
 * @author Aearost
 *
 */
public class TrailsGui {

	private Player player;
	private Inventory initializedGui;

	public TrailsGui(Player player) {
		this.player = player;
		this.initializedGui = initializeGui(player);
	}

	public void openGui() {
		player.openInventory(initializedGui);
	}

	private Inventory initializeGui(Player player) {
		Inventory gui = Bukkit.getServer().createInventory(player, 4,
				ChatUtils.translateToColor("&8&lArrow Trails"));

		// Initialize Base Arrow Items
		ItemStack gray = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta grayMeta = (PotionMeta) gray.getItemMeta();
		grayMeta.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER));
		gray.setItemMeta(grayMeta);
		
		ItemStack purple = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta purpleMeta = (PotionMeta) purple.getItemMeta();
		purpleMeta.setBasePotionData(new PotionData(PotionType.REGEN));
		purple.setItemMeta(purpleMeta);
		
		ItemStack lime = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta limeMeta = (PotionMeta) lime.getItemMeta();
		limeMeta.setBasePotionData(new PotionData(PotionType.JUMP));
		lime.setItemMeta(limeMeta);
		
		ItemStack white = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta whiteMeta = (PotionMeta) white.getItemMeta();
		whiteMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
		white.setItemMeta(whiteMeta);
		
		ItemStack black = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta blackMeta = (PotionMeta) black.getItemMeta();
		blackMeta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
		black.setItemMeta(blackMeta);
		
		ItemStack green = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta greenMeta = (PotionMeta) green.getItemMeta();
		greenMeta.setBasePotionData(new PotionData(PotionType.LUCK));
		green.setItemMeta(greenMeta);
		
		ItemStack blue = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta blueMeta = (PotionMeta) blue.getItemMeta();
		blueMeta.setBasePotionData(new PotionData(PotionType.WATER_BREATHING));
		blue.setItemMeta(blueMeta);
		
		ItemStack orange = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta orangeMeta = (PotionMeta) orange.getItemMeta();
		orangeMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE));
		orange.setItemMeta(orangeMeta);
		
		ItemStack red = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta redMeta = (PotionMeta) red.getItemMeta();
		redMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		red.setItemMeta(redMeta);
		
		ItemStack lightBlue = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta lightBlueMeta = (PotionMeta) lightBlue.getItemMeta();
		lightBlueMeta.setBasePotionData(new PotionData(PotionType.SPEED));
		lightBlue.setItemMeta(lightBlueMeta);

		ItemStack darkGray = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta darkGrayMeta = (PotionMeta) darkGray.getItemMeta();
		darkGrayMeta.setBasePotionData(new PotionData(PotionType.SLOWNESS));
		darkGray.setItemMeta(darkGrayMeta);
		
		ItemStack lightGray = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta lightGrayMeta = (PotionMeta) lightGray.getItemMeta();
		lightGrayMeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
		lightGray.setItemMeta(lightGrayMeta);
		
		ItemStack darkBlue = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta darkBlueMeta = (PotionMeta) darkBlue.getItemMeta();
		darkBlueMeta.setBasePotionData(new PotionData(PotionType.NIGHT_VISION));
		darkBlue.setItemMeta(darkBlueMeta);
		
		
		

		return gui;
	}

}